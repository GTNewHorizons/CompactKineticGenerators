package com.lulan.compactkineticgenerators.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.lulan.compactkineticgenerators.init.ModBlocks;
import com.lulan.compactkineticgenerators.reference.Reference;

// Create the mod's creative tab.
public class CreativeTabCkg {

    public static final CreativeTabs CKG_TAB = new CreativeTabs(Reference.MOD_ID) {

        // Creative tab icon.
        @Override
        public Item getTabIconItem() {
            return new ItemStack(ModBlocks.BlockCkg, 1, 3).getItem();
        }

        // The tab label is localized automatically; no need to override getTranslatedTabLabel.
        // Add itemGroup.<mod_id>=<tab_label> to the language file.
    };

}
