package com.lulan.compactkineticgenerators.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.lulan.compactkineticgenerators.handler.ConfigHandler;

public class KineticGeneratorBlockItem extends ItemBlockWithMetadata {

    public KineticGeneratorBlockItem(Block b1) {
        super(b1, b1);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        if (itemstack != null && itemstack.getItemDamage() > 0) {
            return this.field_150939_a.getUnlocalizedName() + itemstack.getItemDamage();
        }

        return this.field_150939_a.getUnlocalizedName();
    }

    @Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> list, boolean par4) {
        if (itemstack == null) {
            return;
        }

        int meta = itemstack.getItemDamage();
        if (meta <= 7) {
            double scale = meta <= 3 ? ConfigHandler.scaleOutput[meta] : ConfigHandler.scaleOutputWater[meta - 4];
            list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.ku", scale));
            return;
        }

        int tier = meta <= 10 ? meta - 8 : ConfigHandler.customKGTier - 4;
        int eu = 2048 << (tier * 2);
        list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.eu", eu));
        list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.capacity" + (meta - 8)));
    }

}
