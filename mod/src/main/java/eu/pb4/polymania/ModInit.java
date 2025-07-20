package eu.pb4.polymania;

import eu.pb4.polymania.resourcepack.MusicResourcePack;
import eu.pb4.polymer.autohost.api.AutoHostUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class ModInit implements ModInitializer {
    public static final String MOD_ID = "polymania-extras";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        MusicResourcePack.setup();
    }
}