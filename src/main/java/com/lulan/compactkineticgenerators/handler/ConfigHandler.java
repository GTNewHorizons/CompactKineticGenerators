package com.lulan.compactkineticgenerators.handler;

import java.util.stream.Stream;

import net.minecraftforge.common.config.Configuration;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import com.lulan.compactkineticgenerators.reference.Reference;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.GuiConfigEntries;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Config(modid = Reference.MOD_ID)
@Config.LangKey("compactkineticgenerators.config.general")
@Config.RequiresWorldRestart
public class ConfigHandler {

    @Config.Ignore
    private static final int MIN_EXTREME_TIER = 7, MAX_EXTREME_TIER = 13;

    @Config.Ignore
    private static final double LOW_SCALE = 4D, MEDIUM_SCALE = 16D, HIGH_SCALE = 64D, EXTREME_SCALE = 256D;

    @Config.Ignore
    private static final double[] DEFAULT_SCALE = { LOW_SCALE, MEDIUM_SCALE, HIGH_SCALE, EXTREME_SCALE };

    @Config.Name("ExtremeKG Tier")
    @Config.LangKey("compactkineticgenerators.config.extremeTier")
    @Config.Comment("Extreme Kinetic Generator energy tier, default 7 = max 131,072 EU/t")
    @Config.DefaultInt(MIN_EXTREME_TIER)
    @Config.RangeInt(min = MIN_EXTREME_TIER, max = MAX_EXTREME_TIER)
    @Config.Sync
    private static int customKGTier = MIN_EXTREME_TIER;

    @Config.Name("Wind Output Scale")
    @Config.LangKey("compactkineticgenerators.config.windOutput")
    @Config.Comment("output scale vs classic wind generator")
    @Config.DefaultDoubleList({ LOW_SCALE, MEDIUM_SCALE, HIGH_SCALE, EXTREME_SCALE })
    @Config.Entry(FixedScaleEntry.class)
    @Config.Sync
    private static double[] scaleOutput = DEFAULT_SCALE.clone();

    @Config.Name("Wind Rotor Damage Scale")
    @Config.LangKey("compactkineticgenerators.config.windRotorDamage")
    @Config.Comment("rotor damage scale vs classic wind generator")
    @Config.DefaultDoubleList({ LOW_SCALE, MEDIUM_SCALE, HIGH_SCALE, EXTREME_SCALE })
    @Config.Entry(FixedScaleEntry.class)
    public static double[] scaleDamage = DEFAULT_SCALE.clone();

    @Config.Name("Water Output Scale")
    @Config.LangKey("compactkineticgenerators.config.waterOutput")
    @Config.Comment("output scale vs classic water generator")
    @Config.DefaultDoubleList({ LOW_SCALE, MEDIUM_SCALE, HIGH_SCALE, EXTREME_SCALE })
    @Config.Entry(FixedScaleEntry.class)
    @Config.Sync
    private static double[] scaleOutputWater = DEFAULT_SCALE.clone();

    @Config.Name("Water Rotor Damage Scale")
    @Config.LangKey("compactkineticgenerators.config.waterRotorDamage")
    @Config.Comment("rotor damage scale vs classic water generator")
    @Config.DefaultDoubleList({ LOW_SCALE, MEDIUM_SCALE, HIGH_SCALE, EXTREME_SCALE })
    @Config.Entry(FixedScaleEntry.class)
    public static double[] scaleDamageWater = DEFAULT_SCALE.clone();

    public static void init() {
        ConfigurationManager.registerConfig(ConfigHandler.class);
        if (Stream.of(scaleOutput, scaleDamage, scaleOutputWater, scaleDamageWater)
            .anyMatch(scale -> scale.length != DEFAULT_SCALE.length)) {
            scaleOutput = validateScale(scaleOutput);
            scaleDamage = validateScale(scaleDamage);
            scaleOutputWater = validateScale(scaleOutputWater);
            scaleDamageWater = validateScale(scaleDamageWater);
            ConfigurationManager.save(ConfigHandler.class);
        }
    }

    public static int getExtremeTier() {
        return Math.max(MIN_EXTREME_TIER, Math.min(MAX_EXTREME_TIER, customKGTier));
    }

    public static double getOutputScale(boolean water, int tier) {
        // GTNHLib does not enforce list length on values received from the server.
        double[] scale = water ? scaleOutputWater : scaleOutput;
        return (scale.length == DEFAULT_SCALE.length ? scale : DEFAULT_SCALE)[tier];
    }

    private static double[] validateScale(double[] scale) {
        return scale.length == DEFAULT_SCALE.length ? scale : DEFAULT_SCALE.clone();
    }

    @SideOnly(Side.CLIENT)
    public static class FixedScaleEntry extends GuiConfigEntries.ArrayEntry {

        public FixedScaleEntry(GuiConfig screen, GuiConfigEntries entries, IConfigElement<?> element) {
            super(screen, entries, element);
        }

        @Override
        public void valueButtonPressed(int slotIndex) {
            // GTNHLib clears the fixed-length flag when it processes a GUI change.
            ConfigurationManager.getConfig(ConfigHandler.class)
                .getCategory(Configuration.CATEGORY_GENERAL)
                .get(configElement.getName())
                .setIsListLengthFixed(true);
            super.valueButtonPressed(slotIndex);
        }
    }

}
