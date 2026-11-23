package com.lulan.compactkineticgenerators.init;

import com.lulan.compactkineticgenerators.item.IridiumBlade;
import com.lulan.compactkineticgenerators.item.IridiumRotor;
import com.lulan.compactkineticgenerators.item.ModItem;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) // Expose the registered items for direct access by other mods.
public class ModItems {

    public static final ModItem IridiumBlade = new IridiumBlade();
    public static final ModItem IridiumRotor = new IridiumRotor();

    public static void init() {
        GameRegistry.registerItem(IridiumBlade, "IridiumBlade");
        GameRegistry.registerItem(IridiumRotor, "IridiumRotor");
    }

}
