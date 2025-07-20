package eu.pb4.polymania.mixin;

import eu.pb4.polymer.resourcepack.impl.generation.DefaultRPBuilder;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(DefaultRPBuilder.class)
public interface DefaultRPBuilderAccessor {
    @Accessor
    List<ModContainer> getModsList();
}
