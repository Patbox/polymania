package eu.pb4.polymania.patch;

import eu.pb4.factorytools.api.block.model.generic.BlockStateModelManager;
import eu.pb4.polymania.util.BaseFactoryBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.other.PolymerComponent;
import eu.pb4.polymer.core.api.other.PolymerSoundEvent;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import hibi.blahaj.BlahajDataComponentTypes;
import hibi.blahaj.block.BlahajBlocks;
import hibi.blahaj.sound.BlahajSoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

public class BlahajPatch {
    public static void init() {
        PolymerResourcePackUtils.addModAssets("blahaj");
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.fromNamespaceAndPath("blahaj", "block"));

        BlahajBlocks.ITEMS.forEach(item -> PolymerItem.registerOverlay(item, (_, _) -> Items.TRIAL_KEY));
        BlahajBlocks.BLOCKS.forEach(block -> {
            PolymerBlock.registerOverlay(block, BaseFactoryBlock.BARRIER);
            BlockWithElementHolder.registerOverlay(block, BaseFactoryBlock.BARRIER);
            BlockStateModelManager.addBlock(block.builtInRegistryHolder().key().identifier(), block);
        });
        PolymerComponent.registerDataComponent(BlahajDataComponentTypes.OWNER);
        PolymerSoundEvent.registerOverlay(BlahajSoundEvents.BLOCK_CUDDLY_ITEM_HIT);
        BlahajSoundEvents.BLOCK_CUDDLY_ITEM.forEach(PolymerSoundEvent::registerOverlay);
    }
}
