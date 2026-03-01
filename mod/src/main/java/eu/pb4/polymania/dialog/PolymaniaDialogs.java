package eu.pb4.polymania.dialog;

import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.parsers.NodeParser;
import eu.pb4.booklet.api.body.AlignedMessage;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.ActionButton;
import net.minecraft.server.dialog.CommonButtonData;
import net.minecraft.server.dialog.CommonDialogData;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.dialog.DialogAction;
import net.minecraft.server.dialog.MultiActionDialog;
import net.minecraft.server.dialog.NoticeDialog;
import net.minecraft.server.dialog.action.StaticAction;
import net.minecraft.server.dialog.body.DialogBody;
import net.minecraft.server.dialog.body.PlainMessage;
import net.minecraft.util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolymaniaDialogs {
    private static final NodeParser PARSER = NodeParser.builder().quickText().add(new LinkParser()).add(new VersionParser()).markdown().build();

    public static Dialog getModList(Optional<Holder<Dialog>> returnDialog, Optional<Identifier> rawId) {
        var buttons = new ArrayList<ActionButton>();
		var mods = new ArrayList<>(FabricLoader.getInstance().getAllMods());

        for (var mod : mods) {
            if (mod.getMetadata().getId().startsWith("fabric") && !mod.getMetadata().getId().equals("fabric-api") && mod.getMetadata().containsCustomValue("fabric-api:module-lifecycle")) {
                continue;
            }

            var data = new CompoundTag();
            data.putString("prev", rawId.map(Identifier::toString).orElse(""));
            data.putString("mod", mod.getMetadata().getId());

            buttons.add(new ActionButton(new CommonButtonData(Component.literal(mod.getMetadata().getName()), 150), Optional.of(new StaticAction(
                    new ClickEvent.Custom(Identifier.fromNamespaceAndPath("polymania", "open/mod_page"), Optional.of(data))))));
        }

        buttons.sort(Comparator.comparing(x -> x.button().label().getString().toUpperCase(Locale.ROOT)));

        return new MultiActionDialog(new CommonDialogData(Component.literal("Mods"), Optional.empty(),
                true, true, DialogAction.CLOSE, List.of(), List.of()),
                buttons,
                Optional.of(new ActionButton(new CommonButtonData(Component.translatable("gui.back"), 200),
                        returnDialog.map(x -> new StaticAction(new ClickEvent.ShowDialog(x))))),
                2);
    }

    public static Dialog getModPage(String modId, Optional<String> previous) {
        var mod = FabricLoader.getInstance().getModContainer(modId).orElseThrow();
        var meta = mod.getMetadata();

        var text = Component.empty()
                .append(Component.literal("-------------------------\n").withStyle(ChatFormatting.GRAY))
                .append(Component.literal("Version: ").withStyle(ChatFormatting.GOLD))
                .append(meta.getVersion().getFriendlyString() + "\n")
                .append(meta.getLicense().isEmpty() ? Component.empty() : Component.empty()
                        .append(Component.literal("License: ").withStyle(ChatFormatting.YELLOW))
                        .append(String.join(", ", meta.getLicense()))
                        .append("\n")
                );


        if (!meta.getDescription().isEmpty()) {
            text.append(Component.literal("-------------------------\n\n").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal(meta.getDescription() + "\n\n"));
        }

        if (!meta.getContact().asMap().isEmpty()) {
            var map = new ArrayList<>(meta.getContact().asMap().entrySet());
            map.sort(Map.Entry.comparingByKey());

            text
                    .append(Component.literal("-------------------------\n").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Links\n").withStyle(ChatFormatting.YELLOW))
                    .append(Component.literal("-------------------------\n").withStyle(ChatFormatting.GRAY))
                    .append(map.stream().map(x -> {
                        var key = x.getKey();
                        key = x.getKey().substring(0, 1).toUpperCase(Locale.ROOT) + x.getKey().substring(1);


                        var base = Component.empty().append(Component.literal(key + ": ").withStyle(ChatFormatting.GRAY));
                        try {
                            var url = Util.parseAndValidateUntrustedUri(x.getValue());
                            base.append(Component.literal(x.getValue()).setStyle(Style.EMPTY.withColor(ChatFormatting.BLUE).withUnderlined(true).withClickEvent(new ClickEvent.OpenUrl(url))));
                        } catch (Throwable e) {
                            base.append(Component.literal(x.getValue()).setStyle(Style.EMPTY.withUnderlined(true).withClickEvent(new ClickEvent.CopyToClipboard(x.getValue()))));
                        }

                        return base.append("\n");
                    }).collect(Collector.of(Component::empty, MutableComponent::append, MutableComponent::append))).append("\n");
        }

        if (!meta.getAuthors().isEmpty() || !meta.getContributors().isEmpty()) {
            text
                    .append(Component.literal("-------------------------\n").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Credits\n").withStyle(ChatFormatting.YELLOW))
                    .append(Component.literal("-------------------------\n").withStyle(ChatFormatting.GRAY));


            if (!meta.getAuthors().isEmpty()) {
                text
                        .append(Component.literal("=== Authors ===\n"))
                        .append(Component.literal(meta.getAuthors().stream().map(Person::getName).collect(Collectors.joining("\n"))).withStyle(ChatFormatting.GRAY))
                        .append("\n");
            }
            if (!meta.getContributors().isEmpty()) {
                text.append(Component.literal("=== Contributors ===\n"))
                        .append(Component.literal(meta.getContributors().stream().map(Person::getName).collect(Collectors.joining("\n"))).withStyle(ChatFormatting.GRAY))
                ;
            }
        }


        return new NoticeDialog(new CommonDialogData(Component.literal(mod.getMetadata().getName()), Optional.empty(),
                true, true, DialogAction.CLOSE, List.of(new PlainMessage(text, 350)), List.of()),
                new ActionButton(new CommonButtonData(Component.translatable("gui.back"), 200),
                        previous.map(x -> new StaticAction(new ClickEvent.Custom(Identifier.fromNamespaceAndPath("polymania", "open/mods"), Optional.of(StringTag.valueOf(x)))))));
    }

    public static Dialog getChangelog(Optional<Holder<Dialog>> returnDialog) {
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
                        "$$$center$$$",
                        "<gray>-------------------------</>",
                        "<b>" + color + x.substring(2, x.length() - 1) + "</></>",
                        "<gray>-------------------------</>",
                        "$$$left$$$"
                );
            }


            if (x.strip().startsWith("-")) {
				var index = x.indexOf('-');
				if (x.length() < index + 2) {
            		return Stream.of(x);
				}

                return Stream.of("<gray>" + " ".repeat(index) + "» </>" + x.substring(x.charAt(index + 1) == ' ' ? index + 2 : index + 1));
            }

            return Stream.of(x);
        }).map(x -> PARSER.parseText(x, ParserContext.of())).toList();
        var body = new ArrayList<DialogBody>();

        var list = new ArrayList<Component>();
        var align = AlignedMessage.Align.LEFT;

        for (var line : parsed) {
            if (line.getStyle() == Style.EMPTY && line.getContents() instanceof PlainTextContents contents && line.getSiblings().isEmpty()) {
                var alignNew = align;
                var skipAdding = false;
                if (contents.text().equals("$$$center$$$")) {
                    alignNew = AlignedMessage.Align.CENTER;
                    skipAdding = true;
                } else if (contents.text().equals("$$$left$$$")) {
                    alignNew = AlignedMessage.Align.LEFT;
                    skipAdding = true;
                }

                if (skipAdding) {
                    if (!list.isEmpty()) {
                        body.add(new AlignedMessage(CommonComponents.joinLines(list), 350, align));
                        list.clear();
                    }
                    align = alignNew;
                    continue;
                }
            }
            list.add(line);
        }

        if (!list.isEmpty()) {
            body.add(new AlignedMessage(CommonComponents.joinLines(list), 350, align));
        }

        return new NoticeDialog(new CommonDialogData(Component.literal("Changelog"), Optional.empty(),
                true, true, DialogAction.CLOSE, body, List.of()),
                new ActionButton(new CommonButtonData(Component.translatable("gui.back"), 200), returnDialog.map(x -> new StaticAction(new ClickEvent.ShowDialog(x)))));
    }


}
