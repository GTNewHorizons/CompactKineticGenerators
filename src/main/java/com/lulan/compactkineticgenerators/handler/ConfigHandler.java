package com.lulan.compactkineticgenerators.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigHandler {

    public static Configuration config;

    public static int customKGTier = 7;

    public static double[] scaleOutput = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleDamage = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleOutputWater = new double[] { 4D, 16D, 64D, 256D };
    public static double[] scaleDamageWater = new double[] { 4D, 16D, 64D, 256D };

    private static void loadConfiguration() {

        // Keep the legacy key in generated configs.
        config.getBoolean("Debug Mode", "general", false, "Enable debug message (SPAM WARNING)");

        customKGTier = config.getInt(
            "ExtremeKG Tier",
            "general",
            7,
            7,
            13,
            "Extreme Kinetic Generator energy tier, default 7 = max 131,072 EU/t");

        Property propOutput = config
            .get("general", "Wind Output Scale", scaleOutput, "output scale vs classic wind generator");
        Property propDamage = config
            .get("general", "Wind Rotor Damage Scale", scaleDamage, "rotor damage scale vs classic wind generator");
        Property propOutputWater = config
            .get("general", "Water Output Scale", scaleOutputWater, "output scale vs classic water generator");
        Property propDamageWater = config.get(
            "general",
            "Water Rotor Damage Scale",
            scaleDamageWater,
            "rotor damage scale vs classic water generator");

        scaleOutput = getDoubleArrayFromConfig(scaleOutput, propOutput);
        scaleDamage = getDoubleArrayFromConfig(scaleDamage, propDamage);
        scaleOutputWater = getDoubleArrayFromConfig(scaleOutputWater, propOutputWater);
        scaleDamageWater = getDoubleArrayFromConfig(scaleDamageWater, propDamageWater);

        if (config.hasChanged()) {
            config.save();
        }
    }

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

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

}
