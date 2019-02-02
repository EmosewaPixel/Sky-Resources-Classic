package com.skyresourcesclassic;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigOptions {
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

    public static final general general = new general(SERVER_BUILDER);
    public static final health health = new health(SERVER_BUILDER);
    public static final condenser condenser = new condenser(SERVER_BUILDER);
    public static final knife knife = new knife(SERVER_BUILDER);
    public static final combustion combustion = new combustion(SERVER_BUILDER);
    public static final crucible crucible = new crucible(SERVER_BUILDER);
    public static fluidDropper fluidDropper = new fluidDropper(SERVER_BUILDER);
    public static guide guide = new guide(CLIENT_BUILDER);
    public static rockGrinder rockGrinder = new rockGrinder(SERVER_BUILDER);

    public static class general {
        public final ForgeConfigSpec.BooleanValue endWussMode;
        public final ForgeConfigSpec.IntValue draconiumType;
        public final ForgeConfigSpec.BooleanValue allowAllGemTypes;
        public final ForgeConfigSpec.BooleanValue meltableBlazeBlocks;

        general(ForgeConfigSpec.Builder builder) {
            builder.comment("The general config options for Sky Resources Classic").push("General");

            endWussMode = builder
                    .comment("Makes the end portal less dangerous")
                    .translation("End Portal Wuss Mode")
                    .define("endWussMode", false);

            draconiumType = builder
                    .comment("0=overworld, 1=nether")
                    .translation("Draconium Crystal Type")
                    .defineInRange("draconiumType", 1, 1, Integer.MAX_VALUE);

            allowAllGemTypes = builder
                    .comment("Allows all dirty gem types to be obtainable")
                    .translation("All Dirty Gem Types")
                    .define("allowAllGemTypes", false);

            meltableBlazeBlocks = builder
                    .translation("Should Blaze Blocks be meltable in-world?")
                    .define("meltableBlazeBlocks", true);
        }
    }

    public static class health {
        public final ForgeConfigSpec.IntValue healthGemMaxHealth;
        public final ForgeConfigSpec.DoubleValue healthGemPercentage;

        health(ForgeConfigSpec.Builder builder) {
            builder.comment("The general config options for the Health Gem").push("Health");

            healthGemMaxHealth = builder
                    .comment("Max health the Health Gem can store")
                    .translation("Health Gem Max Health Infusion")
                    .defineInRange("healthGemMaxHealth", 100, 0, Integer.MAX_VALUE);

            healthGemPercentage = builder
                    .comment("Percentage (min 0) of health stored to boost player health")
                    .translation("Health Gem Boost Percentage")
                    .defineInRange("healthGemPercentage", 0.02, 0, (double) Float.MAX_VALUE);
        }
    }

    public static class condenser {
        public final ForgeConfigSpec.IntValue condenserProcessTimeBase;

        condenser(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Condenser").push("condenser");

            condenserProcessTimeBase = builder
                    .comment("Base time for condensers to process")
                    .translation("Condenser Base Process Time")
                    .defineInRange("condenserProcessTimeBase", 250, 0, Integer.MAX_VALUE);
        }
    }

    public static class knife {
        public final ForgeConfigSpec.DoubleValue knifeBaseDamage;
        public final ForgeConfigSpec.DoubleValue knifeBaseDurability;

        knife(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Knives").push("knife");

            knifeBaseDamage = builder
                    .translation("Knife Base Damage")
                    .defineInRange("knifeBaseDamage", 1.5, 0, Float.MAX_VALUE);

            knifeBaseDurability = builder
                    .translation("Knife Base Durability")
                    .defineInRange("knifeBaseDurability", 0.8, 0.1, Float.MAX_VALUE);
        }
    }


    public static class crucible {
        public final ForgeConfigSpec.IntValue crucibleCapacity;

        crucible(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Crucible").push("crucible");

            crucibleCapacity = builder
                    .translation("Crucible Capacity")
                    .defineInRange("crucibleCapacity", 4000, 1, Integer.MAX_VALUE);
        }
    }


    public static class combustion {
        public final ForgeConfigSpec.DoubleValue combustionHeatMultiplier;

        combustion(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Combustion Chamber").push("combustion");

            combustionHeatMultiplier = builder
                    .comment("Amount of heat from fuel gained")
                    .translation("Combustion Fuel Heat Multiplier")
                    .defineInRange("combustionHeatMultiplier", 0.5, 0, Float.MAX_VALUE);
        }
    }


    public static class fluidDropper {
        public final ForgeConfigSpec.IntValue fluidDropperCapacity;

        fluidDropper(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Fluid Dropper").push("fluidDropper");

            fluidDropperCapacity = builder
                    .translation("Fluid Dropper Capacity")
                    .defineInRange("fluidDropperCapacity", 1000, 1, Integer.MAX_VALUE);
        }
    }

    public static class guide {
        public final ForgeConfigSpec.BooleanValue rememberGuide;
        public final ForgeConfigSpec.BooleanValue allowGuide;

        guide(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the guide").push("guide");

            rememberGuide = builder
                    .translation("Remember Current Guide Page")
                    .define("rememberGuide", true);

            allowGuide = builder
                    .translation("Allow guide to be opened")
                    .define("allowGuide", true);
        }
    }

    public static class rockGrinder {
        public final ForgeConfigSpec.DoubleValue rockGrinderBaseDamage;
        public final ForgeConfigSpec.DoubleValue rockGrinderBaseDurability;

        rockGrinder(ForgeConfigSpec.Builder builder) {
            builder.comment("Config options for the Rock Grinders").push("rockGrinder");

            rockGrinderBaseDamage = builder
                    .translation("Rock Grinder Base Damage")
                    .defineInRange("rockGrinderBaseDamage", 2.5, 0, Float.MAX_VALUE);

            rockGrinderBaseDurability = builder
                    .translation("Rock Grinder Base Durability")
                    .defineInRange("rockGrinderBaseDurability", 0.8, 0, Float.MAX_VALUE);
        }
    }

    public static final ForgeConfigSpec client_spec = CLIENT_BUILDER.build();
    public static final ForgeConfigSpec server_spec = SERVER_BUILDER.build();

}