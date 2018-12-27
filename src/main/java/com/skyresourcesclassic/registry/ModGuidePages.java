package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.base.guide.SkyResourcesGuide;
import com.skyresourcesclassic.technology.block.BlockFreezer;
import net.minecraft.block.BlockButton;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class ModGuidePages {
    public static Map<String, Map<BlockPos, IBlockState>> imageDesigns = new HashMap<>();

    public static void init() {
        SkyResourcesGuide.addPage("basics", "guide.skyresourcesclassic.stage1", new ItemStack(Blocks.BEDROCK));

        SkyResourcesGuide.addPage("sandIsland", "guide.skyresourcesclassic.stage1", new ItemStack(Blocks.RED_SAND));

        SkyResourcesGuide.addPage("lifeInfusion", "guide.skyresourcesclassic.stage1",
                new ItemStack(ModItems.alchemicalInfusionStone));

        SkyResourcesGuide.addPage("snowIsland", "guide.skyresourcesclassic.stage1", new ItemStack(Blocks.SNOW));

        SkyResourcesGuide.addPage("miniFreezer", "guide.skyresourcesclassic.stage1", new ItemStack(ModBlocks.miniFreezer));

        SkyResourcesGuide.addPage("woodIsland", "guide.skyresourcesclassic.stage1", new ItemStack(Blocks.DARK_OAK_PLANKS));

        SkyResourcesGuide.addPage("grassIsland", "guide.skyresourcesclassic.stage1", new ItemStack(Blocks.GRASS));

        SkyResourcesGuide.addPage("combustionHeater", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModBlocks.combustionHeater[0]));
        addImagePos("combustion", new BlockPos(0, -1, 0), ModBlocks.combustionHeater[0].getDefaultState());
        addImagePos("combustion", new BlockPos(1, 0, 0), Blocks.OAK_PLANKS.getDefaultState());
        addImagePos("combustion", new BlockPos(-1, 0, 0), Blocks.OAK_PLANKS.getDefaultState());
        addImagePos("combustion", new BlockPos(0, 0, 1), Blocks.OAK_PLANKS.getDefaultState());
        addImagePos("combustion", new BlockPos(0, 0, -1), Blocks.OAK_PLANKS.getDefaultState());
        addImagePos("combustion", new BlockPos(0, 1, 0), Blocks.OAK_PLANKS.getDefaultState());
        addImagePos("combustion", new BlockPos(0, -1, -1), Blocks.OAK_BUTTON.getDefaultState());

        SkyResourcesGuide.addPage("waterExtractor", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModItems.waterExtractor));

        SkyResourcesGuide.addPage("dirtFurnace", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.dirtFurnace));

        if (ConfigOptions.general.meltableBlazeBlocks)
            SkyResourcesGuide.addPage("lavaBlaze", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.blazePowderBlock));
        else
            SkyResourcesGuide.addPage("lavaBlaze1", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.blazePowderBlock));
        addImagePos("lava", new BlockPos(0, -1, 0), Blocks.TORCH.getDefaultState());
        addImagePos("lava", new BlockPos(0, 0, 0), ModBlocks.blazePowderBlock.getDefaultState());

        SkyResourcesGuide.addPage("heatSources", "guide.skyresourcesclassic.stage2", new ItemStack(Blocks.TORCH));

        SkyResourcesGuide.addPage("heatProvider", "guide.skyresourcesclassic.stage1", new ItemStack(ModBlocks.heatProvider[0]));

        SkyResourcesGuide.addPage("rockGrinder", "guide.skyresourcesclassic.stage2", new ItemStack(ModItems.stoneGrinder));

        SkyResourcesGuide.addPage("metalCreation", "guide.skyresourcesclassic.stage2", new ItemStack(ModItems.metalCrystal[0]));
        addImagePos("ingotSetup", new BlockPos(0, -2, 0), Blocks.TORCH.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(0, -1, 0), ModBlocks.alchemicalCondenser[0].getDefaultState());
        addImagePos("ingotSetup", new BlockPos(-1, 0, 0), Blocks.GLASS.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(0, 0, 1), Blocks.GLASS.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(0, 0, -1), Blocks.GLASS.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(1, 0, 0), Blocks.GLASS.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(0, 1, 0), ModBlocks.fluidDropper.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(0, 2, 0), ModBlocks.fluidDropper.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(1, 2, 0), ModBlocks.crucible.getDefaultState());
        addImagePos("ingotSetup", new BlockPos(1, 1, 0), Blocks.TORCH.getDefaultState());

        addImagePos("crystalSetup", new BlockPos(0, -1, 0), ModBlocks.crystallizer[0].getDefaultState());
        addImagePos("crystalSetup", new BlockPos(-1, 0, 0), Blocks.GLASS.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(0, 0, 1), Blocks.GLASS.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(0, 0, -1), Blocks.GLASS.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(1, 0, 0), Blocks.GLASS.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(0, 1, 0), ModBlocks.fluidDropper.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(0, 2, 0), ModBlocks.fluidDropper.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(1, 2, 0), ModBlocks.crucible.getDefaultState());
        addImagePos("crystalSetup", new BlockPos(1, 1, 0), Blocks.TORCH.getDefaultState());

        SkyResourcesGuide.addPage("crucible", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.crucible));

        SkyResourcesGuide.addPage("fluidDropper", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.fluidDropper));

        SkyResourcesGuide.addPage("alchemicalCondenser", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModBlocks.alchemicalCondenser[0]));

        SkyResourcesGuide.addPage("crystallizer", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModBlocks.crystallizer[0]));

        SkyResourcesGuide.addPage("crucibleInserter", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModBlocks.crucibleInserter));

        SkyResourcesGuide.addPage("combustionCollector", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModBlocks.combustionCollector));

        SkyResourcesGuide.addPage("quickDropper", "guide.skyresourcesclassic.stage2", new ItemStack(ModBlocks.quickDropper));

        SkyResourcesGuide.addPage("crushedStone", "guide.skyresourcesclassic.stage2",
                new ItemStack(ModItems.itemComponent[14]));

        SkyResourcesGuide.addPage("gemProduction", "guide.skyresourcesclassic.stage2", new ItemStack(Items.EMERALD));

        SkyResourcesGuide.addPage("wildlifeAttractor", "guide.skyresources.stage2",
                new ItemStack(ModBlocks.wildlifeAttractor));

        SkyResourcesGuide.addPage("alchemicalItems", "guide.skyresourcesclassic.stage3",
                new ItemStack(ModItems.itemComponent[0]));

        SkyResourcesGuide.addPage("ironFreezer", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.ironFreezer));
        addImagePos("ironFreezer", new BlockPos(0, -1, 0), ModBlocks.ironFreezer.getDefaultState().with(BlockFreezer.PART, BlockFreezer.EnumPartType.BOTTOM).withProperty(BlockFreezer.FACING, EnumFacing.WEST));
        addImagePos("ironFreezer", new BlockPos(0, 0, 0), ModBlocks.ironFreezer.getDefaultState().with(BlockFreezer.PART, BlockFreezer.EnumPartType.TOP).withProperty(BlockFreezer.FACING, EnumFacing.WEST));

        SkyResourcesGuide.addPage("rockCrusher", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.rockCrusher));

        SkyResourcesGuide.addPage("rockCleaner", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.rockCleaner));

        SkyResourcesGuide.addPage("aqueous", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.aqueousConcentrator));

        if (Loader.isModLoaded("voidislandcontrol"))
            SkyResourcesGuide.addPage("nether", "guide.skyresourcesclassic.misc", new ItemStack(Blocks.NETHERRACK));

        SkyResourcesGuide.addPage("dmWarper", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.darkMatterWarper));

        SkyResourcesGuide.addPage("healthGem", "guide.skyresourcesclassic.stage3", new ItemStack(ModItems.healthGem));

        SkyResourcesGuide.addPage("lifeInfuser", "guide.skyresourcesclassic.stage3", new ItemStack(ModBlocks.lifeInfuser));
        addImagePos("infuser", new BlockPos(-1, -1, -1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, 0, -1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(1, -1, -1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(1, 0, -1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, -1, 1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, 0, 1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(1, -1, 1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(1, 0, 1), Blocks.OAK_LOG.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, 1, -1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(0, 1, -1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(1, 1, -1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(1, 1, 0), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(1, 1, 1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(0, 1, 1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, 1, 1), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(-1, 1, 0), Blocks.OAK_LEAVES.getDefaultState());
        addImagePos("infuser", new BlockPos(0, 0, 0), ModBlocks.lifeInfuser.getDefaultState());
        addImagePos("infuser", new BlockPos(0, 1, 0), ModBlocks.darkMatterBlock.getDefaultState());
        addImagePos("infuser", new BlockPos(0, 2, 0), Blocks.STONE_BUTTON.getDefaultState().with(BlockButton.FACING, EnumFacing.UP));

        SkyResourcesGuide.addPage("end", "guide.skyresourcesclassic.stage3", new ItemStack(Items.ENDER_EYE));
        addImagePos("end", new BlockPos(0, -1, 0), ModBlocks.endPortalCore.getDefaultState());
        addImagePos("end", new BlockPos(-1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(1, -1, 0), Blocks.GOLD_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(0, -1, -1), Blocks.GOLD_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(0, -1, 1), Blocks.GOLD_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(-1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(1, -1, -1), Blocks.DIAMOND_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(-1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
        addImagePos("end", new BlockPos(1, -1, 1), Blocks.DIAMOND_BLOCK.getDefaultState());
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                if (Math.abs(x) == 2 || Math.abs(z) == 2)
                    addImagePos("end", new BlockPos(x, -1, z), ModBlocks.darkMatterBlock.getDefaultState());
            }
        }
        addImagePos("end", new BlockPos(-2, 0, -2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(-2, 1, -2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(-2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
        addImagePos("end", new BlockPos(2, 0, -2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(2, 1, -2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(2, 2, -2), Blocks.GLOWSTONE.getDefaultState());
        addImagePos("end", new BlockPos(-2, 0, 2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(-2, 1, 2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(-2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
        addImagePos("end", new BlockPos(2, 0, 2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(2, 1, 2), Blocks.END_STONE_BRICKS.getDefaultState());
        addImagePos("end", new BlockPos(2, 2, 2), Blocks.GLOWSTONE.getDefaultState());
        addImagePos("end", new BlockPos(0, 0, 0), Blocks.STONE_BUTTON.getDefaultState().with(BlockButton.FACING, EnumFacing.UP));
    }

    private static void addImagePos(String id, BlockPos pos, IBlockState block) {
        if (imageDesigns.get(id) == null)
            imageDesigns.put(id, new HashMap());
        Map map = imageDesigns.get(id);
        map.put(pos, block);
        imageDesigns.put(id, map);
    }
}
