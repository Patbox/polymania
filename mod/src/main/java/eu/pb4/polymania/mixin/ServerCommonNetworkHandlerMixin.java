package eu.pb4.polymania.mixin;

import eu.pb4.polymania.dialog.PolymaniaDialogs;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomClickActionC2SPacket;
import net.minecraft.network.packet.s2c.common.ShowDialogS2CPacket;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonNetworkHandler.class)
public abstract class ServerCommonNetworkHandlerMixin {
    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Shadow @Final protected MinecraftServer server;

    @Inject(method = "onCustomClickAction", at = @At("TAIL"))
    private void handleCustomClick(CustomClickActionC2SPacket packet, CallbackInfo ci) {
        if (!packet.id().getNamespace().equals("polymania")) {
            return;
        }

        switch (packet.id().getPath()) {
            case "open/changelog" -> this.sendPacket(new ShowDialogS2CPacket(RegistryEntry.of(PolymaniaDialogs.getChangelog(
                    packet.payload().flatMap(x -> x.asString().map(Identifier::tryParse)).flatMap(this.server.getRegistryManager().getOrThrow(RegistryKeys.DIALOG)::getEntry)
            ))));
            case "open/mods" -> this.sendPacket(new ShowDialogS2CPacket(RegistryEntry.of(PolymaniaDialogs.getModList(
                    packet.payload().flatMap(x -> x.asString().map(Identifier::tryParse)).flatMap(this.server.getRegistryManager().getOrThrow(RegistryKeys.DIALOG)::getEntry),
                    packet.payload().flatMap(x -> x.asString().map(Identifier::tryParse))
            ))));
            case "open/mod_page" -> {
                try {
                    var nbt = (NbtCompound) packet.payload().orElseThrow();
                    var mod = nbt.getString("mod", "polymania-extras");
                    var previous = nbt.getString("prev");
                    this.sendPacket(new ShowDialogS2CPacket(RegistryEntry.of(PolymaniaDialogs.getModPage(mod, previous))));
                } catch (Throwable e) {
                    // ignore
                }
            }

        }
    }
}
