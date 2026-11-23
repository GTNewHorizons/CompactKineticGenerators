package com.lulan.compactkineticgenerators;

import com.lulan.compactkineticgenerators.handler.ConfigHandler;
import com.lulan.compactkineticgenerators.init.ModBlocks;
import com.lulan.compactkineticgenerators.init.ModItems;
import com.lulan.compactkineticgenerators.init.ModRecipes;
import com.lulan.compactkineticgenerators.init.ModTileentity;
import com.lulan.compactkineticgenerators.proxy.IProxy;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.MOD_VERSION,
    dependencies = "required-after:Forge@[10.13,);required-after:IC2@[2.2,)")
public class CompactKineticGenerators {

    // mod instance
    @Mod.Instance(Reference.MOD_ID)
    public static CompactKineticGenerators instance;

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_NAME);

    // proxy for client/server event
    @SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
    public static IProxy proxy;

    /** pre initial: load config, block/item/entity/render init, event handler init */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // config inti
        ConfigHandler.init(event.getSuggestedConfigurationFile()); // load config file

        ModItems.init();

        ModBlocks.init();

        // Packet channel register (simple network)
        proxy.registerChannel();

        LOGGER.info("DEBUG : preInit complete."); // debug
    }

    /**
     * initial: recipe/tileentity/gui/worldgen init, event handler regist, create data handler,
     * request mod interact AND oreDictionary registr
     */
    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        ModRecipes.init();

        ModTileentity.init();

        LOGGER.info("DEBUG : Init complete.");
    }

    /** post initial: mod interact */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("DEBUG : postInit complete."); // debug
    }

}
