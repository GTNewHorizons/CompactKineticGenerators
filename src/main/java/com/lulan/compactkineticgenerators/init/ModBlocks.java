package com.lulan.compactkineticgenerators.init;

import net.minecraft.block.Block;

import com.lulan.compactkineticgenerators.block.KineticGeneratorBlock;
import com.lulan.compactkineticgenerators.block.KineticGeneratorBlockItem;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) // Expose the registered blocks for direct access by other mods.
public class ModBlocks {

    public static final Block BlockCkg = new KineticGeneratorBlock();

    public static void init() {
        GameRegistry.registerBlock(BlockCkg, KineticGeneratorBlockItem.class, "BlockCkg");
    }

}
