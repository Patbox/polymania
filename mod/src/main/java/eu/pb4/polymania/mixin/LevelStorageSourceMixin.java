package eu.pb4.polymania.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.DataResult;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @Shadow
    @Final
    private static Logger LOGGER;

    @WrapOperation(method = "getLevelDataAndDimensions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/LevelStorageSource;readExistingSavedData(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/core/HolderLookup$Provider;Lnet/minecraft/world/level/saveddata/SavedDataType;)Lcom/mojang/serialization/DataResult;"))
    private static DataResult<WorldGenSettings> trySomewhatFixingBrokenWorlds(LevelStorageSource.LevelStorageAccess access, HolderLookup.Provider registryAccess,
                                                                              SavedDataType<WorldGenSettings> savedDataType, Operation<DataResult<WorldGenSettings>> original,
                                                                              @Local(argsOnly = true) Registry<LevelStem> datapackDimensions) {
        try {
            return original.call(access, registryAccess, savedDataType);
        } catch (Throwable e) {
            LOGGER.error("Polymania> Horribly failed to load old world data! Trying to reconstruct it at best of out ability! See error below", e);
        }

        Path dataLocation = savedDataType.id().withSuffix(".dat").resolveAgainst(access.getLevelPath(LevelResource.DATA));

        CompoundTag fileContents;
        try {
            fileContents = NbtIo.readCompressed(dataLocation, NbtAccounter.unlimitedHeap());
        } catch (IOException e) {
            return DataResult.error(e::getMessage);
        }

        try {
            // Read the seed and some other stuff, as we kinda want to preserve that
            var worldOptions = WorldOptions.CODEC.codec().parse(RegistryOps.create(NbtOps.INSTANCE, registryAccess), fileContents.getCompoundOrEmpty("data"));

            return worldOptions.map(wo -> new WorldGenSettings(wo, registryAccess.getOrThrow(WorldPresets.NORMAL).value().createWorldDimensions()));
        } catch (Throwable e) {
            LOGGER.error("Polymania> Failed to load any world data! Falling back to new world settings...", e);
            return DataResult.error(e::getMessage);
        }
    }
}
