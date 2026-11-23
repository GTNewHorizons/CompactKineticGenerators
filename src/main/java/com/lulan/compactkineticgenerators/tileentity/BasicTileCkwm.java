package com.lulan.compactkineticgenerators.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.lulan.compactkineticgenerators.CompactKineticGenerators;
import com.lulan.compactkineticgenerators.handler.ConfigHandler;

import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;

abstract public class BasicTileCkwm extends TileEntityWindKineticGenerator {

    public int ckwmType, tick;
    protected float scaleOutput;
    protected float scaleDamage;

    public BasicTileCkwm() {
        this(0);
    }

    public BasicTileCkwm(int type) {
        super();
        this.ckwmType = type;
        this.tick = 0;

        // setup scale
        try {
            this.scaleOutput = (float) ConfigHandler.scaleOutput[this.ckwmType];
            this.scaleDamage = (float) ConfigHandler.scaleDamage[this.ckwmType];
        } catch (Exception e) {
            CompactKineticGenerators.LOGGER.error(e);
            this.scaleOutput = 1F;
            this.scaleDamage = 1F;
        }

    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();

        if (this.tick++ % getTickRate() != 0 || getKuOutput() <= 0 || this.rotorSlot.isEmpty()) {
            return;
        }

        double wind = calcWindStrength();
        if (wind >= getMinWindStrength()) {
            int multiplier = wind <= getMaxWindStrength() ? 1 : 4;
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

        ckwmType = nbt.getInteger("type");
        scaleOutput = nbt.getFloat("scale");
        scaleDamage = nbt.getFloat("damage");
    }

    // Write data to NBT.
    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("type", ckwmType);
        nbt.setFloat("scale", scaleOutput);
        nbt.setFloat("damage", scaleDamage);
    }

}
