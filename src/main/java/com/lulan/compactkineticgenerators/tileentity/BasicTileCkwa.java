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

        // setup scale
        try {
            this.scaleOutput = (float) ConfigHandler.scaleOutputWater[this.ckwaType];
            this.scaleDamage = (float) ConfigHandler.scaleDamageWater[this.ckwaType];
        } catch (Exception e) {
            CompactKineticGenerators.LOGGER.error(e);
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

    // Read data from NBT.
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt); // Read the block's coordinates from NBT.

        ckwaType = nbt.getInteger("ckwaType");
        scaleOutput = nbt.getFloat("scale");
        scaleDamage = nbt.getFloat("damage");
    }

    // Write data to NBT.
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("ckwaType", ckwaType);
        nbt.setFloat("scale", scaleOutput);
        nbt.setFloat("damage", scaleDamage);
    }

}
