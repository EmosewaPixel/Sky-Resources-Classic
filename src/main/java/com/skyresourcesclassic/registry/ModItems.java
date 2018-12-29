package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.alchemy.item.DirtyGemItem;
import com.skyresourcesclassic.alchemy.item.ItemHealthGem;
import com.skyresourcesclassic.alchemy.item.ItemInfusionStone;
import com.skyresourcesclassic.alchemy.item.MetalCrystalItem;
import com.skyresourcesclassic.base.item.*;
import com.skyresourcesclassic.technology.item.GemRegisterInfo;
import com.skyresourcesclassic.technology.item.ItemKnife;
import com.skyresourcesclassic.technology.item.ItemRockGrinder;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraftforge.registries.GameData;

import java.util.ArrayList;

public class ModItems {
    public static Item metalCrystal[] = new Item[ModFluids.crystalFluidInfos().length];
    public static Item healthGem;

    public static Item cactusFruit, fleshySnowNugget;

    public static Item waterExtractor;

    public static Item heavySnowball, heavyExpSnowball;

    public static Item dirtyGem[] = new Item[44];

    public static Item cactusKnife, stoneKnife, ironKnife, diamondKnife;

    public static Item stoneGrinder, ironGrinder, diamondGrinder;

    public static Item sandstoneInfusionStone, redSandstoneInfusionStone, alchemicalInfusionStone;

    public static Item survivalistFishingRod;

    private static String[] ItemComponentName = {
            "cactus_needle",
            "coal_alchemical",
            "dust_alchemical",
            "diamond_alchemical",
            "gold_ingot_alchemical",
            "gold_needle_alchemical",
            "wood_heat_component",
            "iron_heat_component",
            "plant_matter",
            "steel_power_component",
            "frozen_iron_cooling_component",
            "dark_matter",
            "enriched_bonemeal",
            "sawdust",
            "stone_crushed",
            "radioactive_mix",
            "frozen_iron_ingot",
            "netherrack_crushed"
    };

    public static Item[] itemComponent = new Item[ItemComponentName.length];

    public static ArrayList<GemRegisterInfo> gemList = new ArrayList<>();

    public static void init() {
        addGem("emerald", 0xFF12DB3A, 0.015F, Blocks.STONE);
        addGem("diamond", 0xFF6BFFFD, 0.033F, Blocks.STONE);
        addGem("ruby", 0xFFFA1E1E, 0.015F, Blocks.STONE);
        addGem("sapphire", 0xFF1E46FA, 0.015F, Blocks.STONE);
        addGem("peridot", 0xFF1CB800, 0.015F, Blocks.STONE);
        addGem("redGarnet", 0xFFC90014, 0.015F, Blocks.STONE);
        addGem("yellowGarnet", 0xFFF7FF0F, 0.015F, Blocks.STONE);
        addGem("apatite", 0xFF2B95FF, 0.600F, Blocks.STONE);
        addGem("amber", 0xFFF5CC53, 0.021F, Blocks.STONE);
        addGem("lepidolite", 0xFF57008A, 0.021F, Blocks.NETHERRACK);
        addGem("malachite", 0xFF23AD00, 0.021F, Blocks.NETHERRACK);
        addGem("onyx", 0xFF3D3D3D, 0.021F, Blocks.STONE);
        addGem("moldavite", 0xFFADFF99, 0.021F, Blocks.NETHERRACK);
        addGem("agate", 0xFFFF63FF, 0.021F, Blocks.STONE);
        addGem("opal", 0xFFDEDEDE, 0.021F, Blocks.STONE);
        addGem("amethyst", 0xFF780078, 0.018F, Blocks.STONE);
        addGem("jasper", 0xFF874800, 0.018F, Blocks.NETHERRACK);
        addGem("aquamarine", 0xFF36E7FF, 0.018F, Blocks.STONE);
        addGem("heliodor", 0xFFFFFF7D, 0.018F, Blocks.STONE);
        addGem("turquoise", 0xFF2EF2C8, 0.018F, Blocks.NETHERRACK);
        addGem("moonstone", 0xFF016A8A, 0.018F, Blocks.NETHERRACK);
        addGem("morganite", 0xFFFA61FF, 0.018F, Blocks.STONE);
        addGem("carnelian", 0xFF630606, 0.018F, Blocks.NETHERRACK);
        addGem("beryl", 0xFF46E334, 0.015F, Blocks.STONE);
        addGem("goldenBeryl", 0xFFD6AE2B, 0.015F, Blocks.NETHERRACK);
        addGem("citrine", 0xFF871616, 0.015F, Blocks.NETHERRACK);
        addGem("indicolite", 0xFF39E6BD, 0.015F, Blocks.STONE);
        addGem("garnet", 0xFFFF9999, 0.015F, Blocks.STONE);
        addGem("topaz", 0xFFFFD399, 0.015F, Blocks.STONE);
        addGem("ametrine", 0xFFA300BF, 0.015F, Blocks.NETHERRACK);
        addGem("tanzanite", 0xFF00076E, 0.015F, Blocks.NETHERRACK);
        addGem("violetSapphire", 0xFF451287, 0.012F, Blocks.NETHERRACK);
        addGem("alexandrite", 0xFFE3E3E3, 0.012F, Blocks.NETHERRACK);
        addGem("blueTopaz", 0xFF1000C4, 0.012F, Blocks.NETHERRACK);
        addGem("spinel", 0xFF750000, 0.012F, Blocks.NETHERRACK);
        addGem("iolite", 0xFF9502CF, 0.012F, Blocks.STONE);
        addGem("blackDiamond", 0xFF262626, 0.009F, Blocks.NETHERRACK);
        addGem("chaos", 0xFFFFE6FB, 0.009F, Blocks.STONE);
        addGem("enderEssence", 0xFF356E19, 0.009F, Blocks.END_STONE);
        addGem("dark", 0xFF242424, 0.27F, Blocks.STONE);
        addGem("quartz", 0xFFFFFFFF, 0.42F, Blocks.NETHERRACK);
        addGem("lapis", 0xFF075BBA, 0.54F, Blocks.STONE);
        addGem("quartzBlack", 0xFF171717, 0.36F, Blocks.STONE);
        addGem("certus", 0xFFB0F4F7, 0.48F, Blocks.STONE);

        for (int i = 0; i < itemComponent.length; i++)
            if (i < 6)
                itemComponent[i] = registerItem(new ItemComponent(ItemComponentName[i], ModItemGroups.tabAlchemy));
            else if (i < 14)
                itemComponent[i] = registerItem(new ItemComponent(ItemComponentName[i], ModItemGroups.tabMain));
            else
                itemComponent[i] = registerItem(new ItemComponent(ItemComponentName[i], ModItemGroups.tabTech));

        for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++) {
            metalCrystal[i] = registerItem(new MetalCrystalItem(ModFluids.getFluidInfo(i).name));
        }
        for (int i = 0; i < gemList.size(); i++)
            dirtyGem[i] = registerItem(new DirtyGemItem(gemList.get(i).name));
        healthGem = registerItem(new ItemHealthGem());
        waterExtractor = registerItem(new ItemWaterExtractor());
        heavySnowball = registerItem(new ItemHeavySnowball("heavy_snowball"));
        heavyExpSnowball = registerItem(new ItemHeavyExplosiveSnowball("heavy_explosive_snowball"));
        cactusFruit = registerItem(new ModItemFood(3, 2F, false, "cactus_fruit"));
        fleshySnowNugget = registerItem(
                new ModItemFood(4, 1.5F, false, "fleshy_snow_nugget"));
        cactusKnife = registerItem(new ItemKnife(SkyResourcesClassic.materialCactusNeedle,
                "cactus_cutting_knife"));
        stoneKnife = registerItem(
                new ItemKnife(ItemTier.STONE, "stone_cutting_knife"));
        ironKnife = registerItem(
                new ItemKnife(ItemTier.IRON, "iron_cutting_knife"));
        diamondKnife = registerItem(
                new ItemKnife(ItemTier.DIAMOND, "diamond_cutting_knife"));

        stoneGrinder = registerItem(
                new ItemRockGrinder(ItemTier.STONE, "stone_grinder"));
        ironGrinder = registerItem(
                new ItemRockGrinder(ItemTier.IRON, "iron_grinder"));
        diamondGrinder = registerItem(
                new ItemRockGrinder(ItemTier.DIAMOND, "diamond_grinder"));

        sandstoneInfusionStone = registerItem(
                new ItemInfusionStone(100, "sandstone_infusion_stone"));
        redSandstoneInfusionStone = registerItem(new ItemInfusionStone(80,
                "red_sandstone_infusion_stone"));
        alchemicalInfusionStone = registerItem(
                new ItemInfusionStone(1500, "alchemical_infusion_stone"));
        survivalistFishingRod = registerItem(
                new ItemSurvivalFishingRod("survivalist_fishing_rod"));
    }

    private static Item registerItem(Item item) {
        if (item.getRegistryName() == null) {
            SkyResourcesClassic.logger.error(
                    "Item {} doesn't have a registry name. Item will not be registered.",
                    item.getClass().getCanonicalName());
            return item;
        }
        GameData.register_impl(item);

        return item;
    }

    private static void addGem(String name, int color, float rarity, Block block) {
        gemList.add(new GemRegisterInfo(name, color, rarity, new ItemStack(block)));
    }
}
