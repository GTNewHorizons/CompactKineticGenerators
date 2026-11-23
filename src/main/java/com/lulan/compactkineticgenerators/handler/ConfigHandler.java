package com.lulan.compactkineticgenerators.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {

    public static Configuration config; // Configuration file handle.

    // GENERAL
    public static boolean debugMode = false;

    public static int customKGTier = 7;

    public static Property propOutput, propDamage, propOutputWater, propDamageWater;
    public static double[] scaleOutput = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleDamage = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleOutputWater = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleDamageWater = new double[] { 4D, 16D, 64D, 256D };

    // Read settings from the configuration file.
    private static void loadConfiguration() {

        // Check whether debug mode is enabled.
        debugMode = config.getBoolean("Debug Mode", "general", false, "Enable debug message (SPAM WARNING)");

        customKGTier = config.getInt(
            "ExtremeKG Tier",
            "general",
            7,
            7,
            13,
            "Extreme Kinetic Generator energy tier, default 7 = max 131,072 EU/t");

        propOutput = config.get("general", "Wind Output Scale", scaleOutput, "output scale vs classic wind generator");
        propDamage = config
            .get("general", "Wind Rotor Damage Scale", scaleDamage, "rotor damage scale vs classic wind generator");
        propOutputWater = config
            .get("general", "Water Output Scale", scaleOutputWater, "output scale vs classic water generator");
        propDamageWater = config.get(
            "general",
            "Water Rotor Damage Scale",
            scaleDamageWater,
            "rotor damage scale vs classic water generator");

        scaleOutput = getDoubleArrayFromConfig(scaleOutput, propOutput);
        scaleDamage = getDoubleArrayFromConfig(scaleDamage, propDamage);
        scaleOutputWater = getDoubleArrayFromConfig(scaleOutputWater, propOutputWater);
        scaleDamageWater = getDoubleArrayFromConfig(scaleDamageWater, propDamageWater);

        // Save the configuration if it changed.
        if (config.hasChanged()) {
            config.save();
        }
    }

    // check get value
    public static double[] getDoubleArrayFromConfig(double[] defaultValue, Property target) {
        int size = defaultValue.length;
        double[] getd = target.getDoubleList();

        if (getd != null && getd.length == size) {
            return getd;
        } else {
            target.set(defaultValue);
            return defaultValue;
        }
    }

    // Initialize the configuration.
    public static void init(File configFile) {
        // Create the configuration if it has not been initialized.
        if (config == null) {
            config = new Configuration(configFile); // Create the configuration instance.
            loadConfiguration();
        }
    }

    // Add configuration migrations here if a new version requires them.
    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        // Reload only if the event is for this mod.
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }

}
