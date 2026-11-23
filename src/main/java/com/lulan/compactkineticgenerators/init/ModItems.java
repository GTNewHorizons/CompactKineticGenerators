package com.lulan.compactkineticgenerators.init;

import com.lulan.compactkineticgenerators.item.BasicCkgItem;
import com.lulan.compactkineticgenerators.item.IridiumBlade;
import com.lulan.compactkineticgenerators.item.IridiumRotor;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID) // Expose the registered items for direct access by other mods.
public class ModItems {

    // items
    public static final BasicCkgItem IridiumBlade = new IridiumBlade();
    public static final BasicCkgItem IridiumRotor = new IridiumRotor();

    // Register items during pre-initialization.
    public static void init() {
        // register items
        GameRegistry.registerItem(IridiumBlade, "IridiumBlade");
        GameRegistry.registerItem(IridiumRotor, "IridiumRotor");

    }

}
