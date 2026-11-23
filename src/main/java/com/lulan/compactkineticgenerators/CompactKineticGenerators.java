package com.lulan.compactkineticgenerators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lulan.compactkineticgenerators.handler.ConfigHandler;
import com.lulan.compactkineticgenerators.init.ModBlocks;
import com.lulan.compactkineticgenerators.init.ModItems;
import com.lulan.compactkineticgenerators.init.ModRecipes;
import com.lulan.compactkineticgenerators.init.ModTileentity;
import com.lulan.compactkineticgenerators.proxy.CommonProxy;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.MOD_VERSION,
    dependencies = "required-after:Forge@[10.13,);required-after:IC2@[2.2,)")
public class CompactKineticGenerators {

    @Mod.Instance(Reference.MOD_ID)
    public static CompactKineticGenerators instance;

    @SidedProxy(
        clientSide = "com.lulan.compactkineticgenerators.proxy.ClientProxy",
        serverSide = "com.lulan.compactkineticgenerators.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        ModItems.init();
        ModBlocks.init();
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        ModRecipes.init();
        ModTileentity.init();
    }

}
