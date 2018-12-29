package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.block.*;
import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import com.skyresourcesclassic.base.block.BaseBlock;
import com.skyresourcesclassic.base.block.BlazePowderBlock;
import com.skyresourcesclassic.base.block.BlockDryCactus;
import com.skyresourcesclassic.base.block.TransparentBlock;
import com.skyresourcesclassic.base.tile.MachineTierInfo;
import com.skyresourcesclassic.technology.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
    public static Block cactusFruitNeedle;

    public static Block combustionHeater[] = new Block[4];
    public static Block crucible;
    public static Block fluidDropper;
    public static Block alchemicalCondenser[] = new Block[4];
    public static Block crystallizer[] = new Block[4];
    public static Block miniFreezer;
    public static Block ironFreezer;
    public static Block heatProvider[] = new Block[4];
    public static Block darkMatterWarper;
    public static Block endPortalCore;
    public static Block lifeInfuser;
    public static Block lifeInjector;
    public static Block crucibleInserter;
    public static Block rockCrusher;
    public static Block rockCleaner;
    public static Block combustionCollector;
    public static Block quickDropper;
    public static Block aqueousConcentrator;
    public static Block aqueousDeconcentrator;
    public static Block wildlifeAttractor;

    public static Block compressedCoalBlock;
    public static Block coalInfusedBlock;
    public static Block sandyNetherrack;
    public static Block darkMatterBlock;
    public static Block heavySnow;
    public static Block heavySnow2;

    public static Block blazePowderBlock;

    public static Block dryCactus;
    public static Block dirtFurnace;

    public static List<Block> crystalFluidBlocks;

    private static ArrayList<MachineTierInfo> tiers = new ArrayList<>();

    public static void init() {
        tiers.add(new MachineTierInfo("stone", 1.5F, 10F, 1));
        tiers.add(new MachineTierInfo("iron", 5F, 10F, 2));
        tiers.add(new MachineTierInfo("steel", 6F, 11F, 3));
        tiers.add(new MachineTierInfo("dm", 10F, 12F, 4));

        crystalFluidBlocks = new ArrayList<>();
        cactusFruitNeedle = registerBlock(new TransparentBlock(Material.PLANTS, "cactus_fruit_needle",
                0.5F, 0.5F, Block.makeCuboidShape(0.3D, 0D, 0.3D, 0.7D, 0.8D, 0.7D), SoundType.PLANT));

        compressedCoalBlock = registerBlock(
                new BaseBlock(Material.ROCK, "compressed_coal_block", 6F, 6F, SoundType.STONE));

        sandyNetherrack = registerBlock(
                new BaseBlock(Material.ROCK, "sandy_netherrack", 2F, 2F, SoundType.STONE));

        coalInfusedBlock = registerBlock(
                new BaseBlock(Material.ROCK, "coal_infused_block", 6F, 6F, SoundType.STONE));
        darkMatterBlock = registerBlock(
                new BaseBlock(Material.ROCK, "dark_matter_block", 10F, 12F, SoundType.STONE));

        blazePowderBlock = registerBlock(new BlazePowderBlock(Material.CLAY, "blaze_powder_block",
                0.5F, 0.5F, SoundType.GROUND));
        heavySnow = registerBlock(new BaseBlock(Material.CLAY, "heavy_snow", 0.5F, 0.5F, SoundType.SNOW));
        heavySnow2 = registerBlock(new BaseBlock(Material.CLAY, "heavy_snow2", 1F, 1F, SoundType.SNOW));

        registerBlock(combustionHeater[0] = new CombustionHeaterBlock("wooden", 2.0F, 5.0F, 1));
        registerBlock(heatProvider[0] = new BlockHeater("wooden", 2.0F, 5.0F, 1));
        for (MachineTierInfo tier : tiers) {
            registerBlock(
                    alchemicalCondenser[tier.id - 1] = new CondenserBlock(tier.name, tier.hardness, tier.resistance, tier.id));
            registerBlock(
                    crystallizer[tier.id - 1] = new CrystallizerBlock(tier.name, tier.hardness, tier.resistance, tier.id));
            if (tier.id != 1) {
                registerBlock(
                        combustionHeater[tier.id - 1] = new CombustionHeaterBlock(tier.name, tier.hardness, tier.resistance, tier.id));
                registerBlock(
                        heatProvider[tier.id - 1] = new BlockHeater(tier.name, tier.hardness, tier.resistance, tier.id));
            }
        }
        darkMatterWarper = registerBlock(new BlockDarkMatterWarper("dark_matter_warper", 8F, 12F));
        endPortalCore = registerBlock(new BlockEndPortalCore("end_portal_core", 6F, 12F));
        rockCrusher = registerBlock(new BlockRockCrusher("rock_crusher", 6F, 12F));
        rockCleaner = registerBlock(new BlockRockCleaner("rock_cleaner", 6F, 12F));
        combustionCollector = registerBlock(
                new BlockCombustionCollector("combustion_collector", 6F, 12F));
        quickDropper = registerBlock(new BlockQuickDropper("quick_dropper", 6F, 12F));
        lifeInfuser = registerBlock(new LifeInfuserBlock("life_infuser", 6F, 12F));
        lifeInjector = registerBlock(new LifeInjectorBlock("life_injector", 6F, 12F));

        miniFreezer = registerBlock(new BlockMiniFreezer("mini_freezer", 0.5F, 0.5F));
        ironFreezer = registerBlock(new BlockFreezer("iron_freezer", 2F, 2F));

        crucible = registerBlock(new CrucibleBlock("crucible", 2F, 12F));
        wildlifeAttractor = registerBlock(
                new BlockWildlifeAttractor("wildlife_attractor", 2F, 12F));

        fluidDropper = registerBlock(new FluidDropperBlock("fluid_dropper", 2F, 12F));

        crucibleInserter = registerBlock(new BlockCrucibleInserter("crucible_inserter", 2F, 12F));
        aqueousConcentrator = registerBlock(
                new BlockAqueousConcentrator("aqueous_concentrator", 2F, 12F));
        aqueousDeconcentrator = registerBlock(
                new BlockAqueousDeconcentrator("aqueous_deconcentrator", 2F, 12F));
        dryCactus = registerBlock(new BlockDryCactus());

        dirtFurnace = registerBlock(new BlockDirtFurnace("dirt_furnace", 0.5F, 0.5F));

        for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++) {
            CrystalFluidType type = ModFluids.crystalFluidInfos()[i].type;
            crystalFluidBlocks.add(registerBlockOnly(new FluidCrystalBlock(ModFluids.crystalFluids.get(i),
                    type == CrystalFluidType.NORMAL ? Material.WATER : Material.LAVA,
                    ModFluids.crystalFluidInfos()[i].name + (type == CrystalFluidType.NORMAL ? "" : "molten")
                            + "crystal_fluid_block",
                    ModFluids.crystalFluidInfos()[i].name + (type == CrystalFluidType.NORMAL ? "" : "molten")
                            + "crystal_fluid_block")));
        }
    }

    public static Block registerBlockOnly(Block block, String name) {
        GameData.register_impl(block.setRegistryName(new ResourceLocation(References.ModID, name)));

        return block;
    }

    public static Block registerBlock(Block block, String name) {
        GameData.register_impl(block.setRegistryName(new ResourceLocation(References.ModID, name)));
        GameData.register_impl(new ItemBlock(block).setRegistryName(new ResourceLocation(References.ModID, name)));

        return block;
    }

    private static Block registerBlockOnly(Block block) {
        GameData.register_impl(block);

        return block;
    }

    public static Block registerBlock(Block block) {
        GameData.register_impl(block);
        GameData.register_impl(new ItemBlock(block).setRegistryName(block.getRegistryName()));

        return block;
    }
}
