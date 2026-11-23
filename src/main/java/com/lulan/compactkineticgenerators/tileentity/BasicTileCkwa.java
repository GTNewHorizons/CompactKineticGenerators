package com.lulan.compactkineticgenerators.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.lulan.compactkineticgenerators.CompactKineticGenerators;
import com.lulan.compactkineticgenerators.handler.ConfigHandler;

import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;

public class BasicTileCkwa extends TileEntityWaterKineticGenerator {

    public int ckwaType, tick;
    protected float scaleOutput;
    protected float scaleDamage;

    public BasicTileCkwa() {
        this(0);
    }

    public BasicTileCkwa(int type) {
        super();
        this.ckwaType = type;
        this.tick = 0;

        try {
            this.scaleOutput = (float) ConfigHandler.scaleOutputWater[this.ckwaType];
            this.scaleDamage = (float) ConfigHandler.scaleDamageWater[this.ckwaType];
        } catch (Exception e) {
            CompactKineticGenerators.LOGGER.error("Failed to load water mill scales for tier " + type, e);
            this.scaleOutput = 1F;
            this.scaleDamage = 1F;
        }

    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();

        if (this.tick++ <= 20) {
            return;
        }
        this.tick = 0;

        if (getKuOutput() > 0 && !this.rotorSlot.isEmpty()) {
            int multiplier = this.type == 1 ? 2 : 1; // Ocean biomes double wear.
            this.rotorSlot.damage((int) (multiplier * this.scaleDamage), false);
        }
    }

    @Override
    public int getKuOutput() {
        return (int) (scaleOutput * super.getKuOutput());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        ckwaType = nbt.getInteger("ckwaType");
        scaleOutput = nbt.getFloat("scale");
        scaleDamage = nbt.getFloat("damage");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("ckwaType", ckwaType);
        nbt.setFloat("scale", scaleOutput);
        nbt.setFloat("damage", scaleDamage);
    }

}
