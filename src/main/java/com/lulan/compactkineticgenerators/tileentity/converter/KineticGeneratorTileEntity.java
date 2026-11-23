package com.lulan.compactkineticgenerators.tileentity.converter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.lulan.compactkineticgenerators.handler.ConfigHandler;

import ic2.api.energy.tile.IKineticSource;
import ic2.core.IC2;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;

public class KineticGeneratorTileEntity extends TileEntityKineticGenerator {

    public int type;
    protected double guiproduction = 0.0D;
    protected double production = 0.0D;
    protected final int MaxStorage;
    protected final double productionpeerkineticunit;

    public KineticGeneratorTileEntity() {
        this(0);
    }

    public KineticGeneratorTileEntity(int type) {
        super();
        this.type = type;

        this.productionpeerkineticunit = (0.25D
            * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic"));
        this.updateTicker = IC2.random.nextInt(getTickRate());

        this.MaxStorage = switch (this.type) {
            case 1 -> 4_000_000;
            case 2 -> 10_000_000;
            case 3 -> 40_000_000;
            default -> 1_000_000;
        };
    }

    @Override
    protected void updateEntityServer() {
        boolean newActive = gainEnergy();

        if (this.updateTicker++ >= getTickRate()) {
            this.guiproduction = this.production;
            this.updateTicker = 0;
        }

        if (this.EUstorage > this.MaxStorage) {
            this.EUstorage = this.MaxStorage;
        }

        if (getActive() != newActive) {
            setActive(newActive);
        }
    }

    @Override
    protected boolean gainEnergy() {
        ForgeDirection dir = ForgeDirection.getOrientation(getFacing());

        TileEntity te = this.worldObj
            .getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);

        if (te instanceof IKineticSource source) {
            ForgeDirection from = dir.getOpposite();
            int bandwidth = source.maxrequestkineticenergyTick(from);
            int received = source.requestkineticenergy(from, bandwidth);

            if (received >= 0) {
                this.production = received * this.productionpeerkineticunit;
                this.EUstorage += this.production;
                return true;
            }
        }

        this.production = 0D;
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.type = nbt.getInteger("type");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("type", this.type);
    }

    // 4 = 2048, 5 = 8192, 6 = 32768, 7 = 131072
    @Override
    public int getSourceTier() {
        return this.type >= 3 ? ConfigHandler.customKGTier : 4 + this.type;
    }

    @Override
    public int gaugeEUStorageScaled(int i) {
        return (int) (this.EUstorage * i / this.MaxStorage);
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocalFormatted("tile.ckg:BlockCkg" + (8 + this.type) + ".name");
    }

    @Override
    public double getproduction() {
        return this.guiproduction;
    }

}
