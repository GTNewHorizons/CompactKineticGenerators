package com.lulan.compactkineticgenerators.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.gtnewhorizon.gtnhlib.util.numberformatting.NumberFormatUtil;
import com.lulan.compactkineticgenerators.handler.ConfigHandler;
import com.lulan.compactkineticgenerators.tileentity.converter.KineticGeneratorTileEntity;

import ic2.api.energy.EnergyNet;

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
        if (meta < 0 || meta > 11) {
            return;
        }
        if (meta <= 7) {
            double scale = meta <= 3 ? ConfigHandler.scaleOutput[meta] : ConfigHandler.scaleOutputWater[meta - 4];
            String multiplier = NumberFormatUtil.formatNumber(scale);
            list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.outputScale", multiplier));
            return;
        }

        int type = meta - 8;
        double eu = EnergyNet.instance.getPowerFromTier(KineticGeneratorTileEntity.getSourceTier(type));
        list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.eu", NumberFormatUtil.formatNumber(eu)));
        String capacity = NumberFormatUtil.formatEnergyCompact(KineticGeneratorTileEntity.getStorageCapacity(type));
        list.add(StatCollector.translateToLocalFormatted("tooltip.ckg.capacity", capacity));
    }

}
