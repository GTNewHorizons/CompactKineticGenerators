package com.lulan.compactkineticgenerators.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.lulan.compactkineticgenerators.init.ModBlocks;
import com.lulan.compactkineticgenerators.reference.Reference;

public class ModCreativeTab {

    public static final CreativeTabs CKG_TAB = new CreativeTabs(Reference.MOD_ID) {

        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.BlockCkg);
        }
    };

}
