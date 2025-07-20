package eu.pb4.polymania.resourcepack;

import com.google.common.hash.Hashing;
import eu.pb4.polymania.mixin.DefaultRPBuilderAccessor;
import eu.pb4.polymer.autohost.api.AutoHostUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.api.ResourcePackCreator;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MusicResourcePack {
    public static final Identifier ID = Identifier.of("polymania", "music_rp");
    public static final Path PATH = FabricLoader.getInstance().getGameDir().resolve(".tmp/music_rp.zip");

    public static final List<Pair<String, List<String>>> COPY_PATHS = List.of(
            new Pair<>("cinderscapes", List.of("assets/cinderscapes/sounds/music/")),
            new Pair<>("enderscape", List.of("assets/enderscape/sounds/"))
    );


    public static void setup() {
        CompletableFuture.runAsync(MusicResourcePack::generatePack);
        AutoHostUtils.registerHostedFile(ID, PATH);
        AutoHostUtils.SEND_RESOURCE_PACK_COLLECTOR.register((provider, context, consumer) -> {
            if (generated) {
                consumer.accept(provider.createProperties(context, ID, hash));
            }
        });
    }

    public static boolean generated = false;
    public static String hash = "";

    public static void generatePack() {
        try {
            Files.createDirectories(PATH.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        var builder = ResourcePackCreator.create();
        builder.setPackDescription(Text.literal("Polymania Music Files"));
        builder.creationEvent.register(b -> {
            for (var path : COPY_PATHS) {
                var mod = FabricLoader.getInstance().getModContainer(path.getLeft());
                if (mod.isEmpty()) continue;
                ((DefaultRPBuilderAccessor) b).getModsList().add(mod.orElseThrow());

                for (var s : path.getRight()) {
                    var p = mod.get().findPath(s);
                    p.ifPresent(value -> b.copyFromPath(value, s));
                }
            }
        });

        try {
            builder.build(PATH);
            hash = com.google.common.io.Files.asByteSource(PATH.toFile()).hash(Hashing.sha1()).toString();
            generated = true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
