package eu.pb4.polymania.dialog;

import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.parsers.NodeParser;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.dialog.AfterAction;
import net.minecraft.dialog.DialogActionButtonData;
import net.minecraft.dialog.DialogButtonData;
import net.minecraft.dialog.DialogCommonData;
import net.minecraft.dialog.action.SimpleDialogAction;
import net.minecraft.dialog.body.PlainMessageDialogBody;
import net.minecraft.dialog.type.Dialog;
import net.minecraft.dialog.type.MultiActionDialog;
import net.minecraft.dialog.type.NoticeDialog;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolymaniaDialogs {
    private static final NodeParser PARSER = NodeParser.builder().quickText().add(new LinkParser()).add(new VersionParser()).markdown().build();

    public static Dialog getModList(Optional<RegistryEntry<Dialog>> returnDialog, Optional<Identifier> rawId) {
        var buttons = new ArrayList<DialogActionButtonData>();
		var mods = new ArrayList<>(FabricLoader.getInstance().getAllMods());

        for (var mod : mods) {
            if (mod.getMetadata().getId().startsWith("fabric") && !mod.getMetadata().getId().equals("fabric-api") && mod.getMetadata().containsCustomValue("fabric-api:module-lifecycle")) {
                continue;
            }

            var data = new NbtCompound();
            data.putString("prev", rawId.map(Identifier::toString).orElse(""));
            data.putString("mod", mod.getMetadata().getId());

            buttons.add(new DialogActionButtonData(new DialogButtonData(Text.literal(mod.getMetadata().getName()), 150), Optional.of(new SimpleDialogAction(
                    new ClickEvent.Custom(Identifier.of("polymania", "open/mod_page"), Optional.of(data))))));
        }

        buttons.sort(Comparator.comparing(x -> x.data().label().getString().toUpperCase(Locale.ROOT)));

        return new MultiActionDialog(new DialogCommonData(Text.literal("Mods"), Optional.empty(),
                true, true, AfterAction.CLOSE, List.of(), List.of()),
                buttons,
                Optional.of(new DialogActionButtonData(new DialogButtonData(Text.translatable("gui.back"), 200),
                        returnDialog.map(x -> new SimpleDialogAction(new ClickEvent.ShowDialog(x))))),
                2);
    }

    public static Dialog getModPage(String modId, Optional<String> previous) {
        var mod = FabricLoader.getInstance().getModContainer(modId).orElseThrow();
        var meta = mod.getMetadata();

        var text = Text.empty()
                .append(Text.literal("-------------------------\n").formatted(Formatting.GRAY))
                .append(Text.literal("Version: ").formatted(Formatting.GOLD))
                .append(meta.getVersion().getFriendlyString() + "\n")
                .append(meta.getLicense().isEmpty() ? Text.empty() : Text.empty()
                        .append(Text.literal("License: ").formatted(Formatting.YELLOW))
                        .append(String.join(", ", meta.getLicense()))
                        .append("\n")
                );


        if (!meta.getDescription().isEmpty()) {
            text.append(Text.literal("-------------------------\n\n").formatted(Formatting.GRAY))
                    .append(Text.literal(meta.getDescription() + "\n\n"));
        }

        if (!meta.getContact().asMap().isEmpty()) {
            var map = new ArrayList<>(meta.getContact().asMap().entrySet());
            map.sort(Map.Entry.comparingByKey());

            text
                    .append(Text.literal("-------------------------\n").formatted(Formatting.GRAY))
                    .append(Text.literal("Links\n").formatted(Formatting.YELLOW))
                    .append(Text.literal("-------------------------\n").formatted(Formatting.GRAY))
                    .append(map.stream().map(x -> {
                        var key = x.getKey();
                        key = x.getKey().substring(0, 1).toUpperCase(Locale.ROOT) + x.getKey().substring(1);


                        var base = Text.empty().append(Text.literal(key + ": ").formatted(Formatting.GRAY));
                        try {
                            var url = Util.validateUri(x.getValue());
                            base.append(Text.literal(x.getValue()).setStyle(Style.EMPTY.withColor(Formatting.BLUE).withUnderline(true).withClickEvent(new ClickEvent.OpenUrl(url))));
                        } catch (Throwable e) {
                            base.append(Text.literal(x.getValue()).setStyle(Style.EMPTY.withUnderline(true).withClickEvent(new ClickEvent.CopyToClipboard(x.getValue()))));
                        }

                        return base.append("\n");
                    }).collect(Collector.of(Text::empty, MutableText::append, MutableText::append))).append("\n");
        }

        if (!meta.getAuthors().isEmpty() || !meta.getContributors().isEmpty()) {
            text
                    .append(Text.literal("-------------------------\n").formatted(Formatting.GRAY))
                    .append(Text.literal("Credits\n").formatted(Formatting.YELLOW))
                    .append(Text.literal("-------------------------\n").formatted(Formatting.GRAY));


            if (!meta.getAuthors().isEmpty()) {
                text
                        .append(Text.literal("=== Authors ===\n"))
                        .append(Text.literal(meta.getAuthors().stream().map(Person::getName).collect(Collectors.joining("\n"))).formatted(Formatting.GRAY))
                        .append("\n");
            }
            if (!meta.getContributors().isEmpty()) {
                text.append(Text.literal("=== Contributors ===\n"))
                        .append(Text.literal(meta.getContributors().stream().map(Person::getName).collect(Collectors.joining("\n"))).formatted(Formatting.GRAY))
                ;
            }
        }


        return new NoticeDialog(new DialogCommonData(Text.literal(mod.getMetadata().getName()), Optional.empty(),
                true, true, AfterAction.CLOSE, List.of(new PlainMessageDialogBody(text, 350)), List.of()),
                new DialogActionButtonData(new DialogButtonData(Text.translatable("gui.back"), 200),
                        previous.map(x -> new SimpleDialogAction(new ClickEvent.Custom(Identifier.of("polymania", "open/mods"), Optional.of(NbtString.of(x)))))));
    }

    public static Dialog getChangelog(Optional<RegistryEntry<Dialog>> returnDialog) {
        String text;
        try {
            text = Files.readString(FabricLoader.getInstance().getGameDir().resolve("changelog.md"));
        } catch (IOException e) {
            text = "File 'changelog.md' is missing!";
        }

		var isLatest = new boolean[] { true };

        var parsed = text.lines().flatMap(x -> {
            x = x.replace("\t", "    ");
            if (x.startsWith("# ") && x.endsWith(":")) {
				var color = isLatest[0] ? "<gold>" : "<yellow>";
				isLatest[0] = false;
                return Stream.of(
                        "<gray>-------------------------</>\n",
                        "<b>" + color + x.substring(2, x.length() - 1) + "</></>\n",
                        "<gray>-------------------------</>\n"
                );
            }
            if (x.startsWith("- ")) {
                return Stream.of("<gray>» </>" + x.substring(2) + "\n");
            } else if (x.startsWith("  - ")) {
                return Stream.of("<gray>-» </>" + x.substring(4) + "\n");
            } else if (x.startsWith("    - ")) {
                return Stream.of("<gray>--» </>" + x.substring(6) + "\n");
            } else if (x.startsWith("     - ")) {
                return Stream.of("<gray>---» </>" + x.substring(8) + "\n");
            } else if (x.startsWith("      - ")) {
                return Stream.of("<gray>----» </>" + x.substring(10) + "\n");
            }

            return Stream.of(x + "\n");
        }).map(x -> PARSER.parseText(x, ParserContext.of())).collect(Collector.of(Text::empty, MutableText::append, MutableText::append));

        return new NoticeDialog(new DialogCommonData(Text.literal("Changelog"), Optional.empty(),
                true, true, AfterAction.CLOSE, List.of(new PlainMessageDialogBody(parsed, 350)), List.of()),
                new DialogActionButtonData(new DialogButtonData(Text.translatable("gui.back"), 200), returnDialog.map(x -> new SimpleDialogAction(new ClickEvent.ShowDialog(x)))));
    }
}
