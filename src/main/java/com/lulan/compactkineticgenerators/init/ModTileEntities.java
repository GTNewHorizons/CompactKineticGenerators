package com.lulan.compactkineticgenerators.init;

import com.lulan.compactkineticgenerators.tileentity.converter.ExtremeKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.converter.HighKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.converter.LowKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.converter.MediumKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.water.ExtremeWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.water.HighWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.water.LowWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.water.MediumWaterKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.wind.ExtremeWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.wind.HighWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.wind.LowWindKineticGeneratorTileEntity;
import com.lulan.compactkineticgenerators.tileentity.wind.MediumWindKineticGeneratorTileEntity;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void init() {
        // Keep these IDs stable: worlds store them in tile entity NBT.
        // wind mills
        GameRegistry.registerTileEntity(LowWindKineticGeneratorTileEntity.class, "TileEntityCkwmL");
        GameRegistry.registerTileEntity(MediumWindKineticGeneratorTileEntity.class, "TileEntityCkwmM");
        GameRegistry.registerTileEntity(HighWindKineticGeneratorTileEntity.class, "TileEntityCkwmH");
        GameRegistry.registerTileEntity(ExtremeWindKineticGeneratorTileEntity.class, "TileEntityCkwmE");
        // water mills
        GameRegistry.registerTileEntity(LowWaterKineticGeneratorTileEntity.class, "TileEntityCkwaL");
        GameRegistry.registerTileEntity(MediumWaterKineticGeneratorTileEntity.class, "TileEntityCkwaM");
        GameRegistry.registerTileEntity(HighWaterKineticGeneratorTileEntity.class, "TileEntityCkwaH");
        GameRegistry.registerTileEntity(ExtremeWaterKineticGeneratorTileEntity.class, "TileEntityCkwaE");
        // kinetic generators
        GameRegistry.registerTileEntity(LowKineticGeneratorTileEntity.class, "TileEntityCkgL");
        GameRegistry.registerTileEntity(MediumKineticGeneratorTileEntity.class, "TileEntityCkgM");
        GameRegistry.registerTileEntity(HighKineticGeneratorTileEntity.class, "TileEntityCkgH");
        GameRegistry.registerTileEntity(ExtremeKineticGeneratorTileEntity.class, "TileEntityCkgE");
    }

}
