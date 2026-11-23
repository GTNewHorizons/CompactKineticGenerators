package com.lulan.compactkineticgenerators.tileentity;

import com.lulan.compactkineticgenerators.CompactKineticGenerators;
import net.minecraft.nbt.NBTTagCompound;

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

        // rotor take more damage
        if (this.tick++ > 20) {
            this.tick = 0;

            if (getKuOutput() > 0 && !this.rotorSlot.isEmpty()) {
                // ocean biome
                if (this.type == 1) {
                    this.rotorSlot.damage((int) (2 * this.scaleDamage), false);
                }
                // other
                else {
                    this.rotorSlot.damage((int) (1 * this.scaleDamage), false);
                }
            }
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
