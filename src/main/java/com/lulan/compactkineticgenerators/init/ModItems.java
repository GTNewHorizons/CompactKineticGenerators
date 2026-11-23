package com.lulan.compactkineticgenerators.init;

import com.lulan.compactkineticgenerators.item.BasicCkgItem;
import com.lulan.compactkineticgenerators.item.IridiumBlade;
import com.lulan.compactkineticgenerators.item.IridiumRotor;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) // Expose the registered items for direct access by other mods.
public class ModItems {

    public static final BasicCkgItem IridiumBlade = new IridiumBlade();
    public static final BasicCkgItem IridiumRotor = new IridiumRotor();

    public static void init() {
        GameRegistry.registerItem(IridiumBlade, "IridiumBlade");
        GameRegistry.registerItem(IridiumRotor, "IridiumRotor");
    }

}
