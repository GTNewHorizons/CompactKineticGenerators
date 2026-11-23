package com.lulan.compactkineticgenerators.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import ic2.api.item.IKineticRotor.GearboxType;
import ic2.core.block.kineticgenerator.gui.GuiWaterKineticGenerator;
import ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator;

public final class ClientProxy extends CommonProxy {

    @Override
    public GearboxType getRotorTooltipGearboxType() {
        GuiScreen screen = Minecraft.getMinecraft().currentScreen;
        if (screen instanceof GuiWaterKineticGenerator) {
            return GearboxType.WATER;
        }
        if (screen instanceof GuiWindKineticGenerator) {
            return GearboxType.WIND;
        }
        return null;
    }
}
