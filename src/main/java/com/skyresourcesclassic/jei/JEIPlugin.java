package com.skyresourcesclassic.jei;

import com.skyresourcesclassic.ItemHelper;
import com.skyresourcesclassic.References;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.jei.cauldronclean.CauldronCleanRecipeCategory;
import com.skyresourcesclassic.jei.combustion.CombustionRecipeCategory;
import com.skyresourcesclassic.jei.condenser.CondenserRecipeCategory;
import com.skyresourcesclassic.jei.condenser.CondenserRecipeMaker;
import com.skyresourcesclassic.jei.crucible.CrucibleRecipeCategory;
import com.skyresourcesclassic.jei.freezer.FreezerRecipeCategory;
import com.skyresourcesclassic.jei.heatsources.HeatSourceJEI;
import com.skyresourcesclassic.jei.heatsources.HeatSourcesRecipeCategory;
import com.skyresourcesclassic.jei.infusion.InfusionRecipeCategory;
import com.skyresourcesclassic.jei.knife.KnifeRecipeCategory;
import com.skyresourcesclassic.jei.rockgrinder.RockGrinderRecipeCategory;
import com.skyresourcesclassic.jei.waterextractor.WaterExtractorRecipeCategory;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.gui.Focus;
import mezz.jei.plugins.jei.JEIInternalPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void onRuntimeAvailable(IJeiRuntime arg0) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration reg) {
        IGuiHelper guiHelper = reg.getJeiHelpers().getGuiHelper();

        reg.addRecipeCategories(new InfusionRecipeCategory(guiHelper),
                new CombustionRecipeCategory(guiHelper),
                new RockGrinderRecipeCategory(guiHelper),
                new KnifeRecipeCategory(guiHelper),
                new CrucibleRecipeCategory(guiHelper),
                new FreezerRecipeCategory(guiHelper),
                new HeatSourcesRecipeCategory(guiHelper),
                new WaterExtractorRecipeCategory(guiHelper),
                new CauldronCleanRecipeCategory(guiHelper),
                new CondenserRecipeCategory(guiHelper));
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), CombustionRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), RockGrinderRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), KnifeRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), CrucibleRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), FreezerRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), WaterExtractorRecipeCategory.UUID);
        registry.handleRecipes(ProcessRecipe.class, recipe -> new ProcessRecipeJEI(recipe), CauldronCleanRecipeCategory.UUID);

        registry.addRecipes(ProcessRecipeManager.infusionRecipes.getRecipes(), InfusionRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.combustionRecipes.getRecipes(), CombustionRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.rockGrinderRecipes.getRecipes(), RockGrinderRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.crucibleRecipes.getRecipes(), CrucibleRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.freezerRecipes.getRecipes(), FreezerRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.knifeRecipes.getRecipes(), KnifeRecipeCategory.UUID);
        registry.addRecipes(getSources(), HeatSourcesRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.waterExtractorExtractRecipes.getRecipes(), WaterExtractorRecipeCategory.UUID);
        registry.addRecipes(ProcessRecipeManager.waterExtractorInsertRecipes.getRecipes(), WaterExtractorRecipeCategory.UUID);
        if (ProcessRecipeManager.cauldronCleanRecipes.getRecipes().size() > 0)
            registry.addRecipes(ProcessRecipeManager.cauldronCleanRecipes.getRecipes(), CauldronCleanRecipeCategory.UUID);
        registry.addRecipes(CondenserRecipeMaker.getRecipes(), CondenserRecipeCategory.UUID);
        for (Block block : ModBlocks.combustionHeater)
            registry.addRecipeCatalyst(new ItemStack(block),
                    References.ModID + ":combustion");
        registry.addRecipeCatalyst(new ItemStack(Items.CAULDRON, 1, 0),
                References.ModID + ":cauldronclean");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.rockCleaner),
                References.ModID + ":cauldronclean");
        for (Block block : ModBlocks.alchemicalCondenser)
            registry.addRecipeCatalyst(new ItemStack(block),
                    References.ModID + ":condenser");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.crucible, 1, 0), References.ModID + ":crucible");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.miniFreezer, 1, 0),
                References.ModID + ":freezer");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.ironFreezer, 1, 0),
                References.ModID + ":freezer");
        registry.addRecipeCatalyst(new ItemStack(ModItems.waterExtractor),
                References.ModID + ":waterextractor");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.aqueousConcentrator),
                References.ModID + ":waterextractor");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.aqueousDeconcentrator),
                References.ModID + ":waterextractor");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.dirtFurnace), "minecraft.smelting");
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.dirtFurnace), "minecraft.fuel");
        for (ItemStack i : ItemHelper.getRockGrinders()) {
            registry.addRecipeCatalyst(i, References.ModID + ":rockgrinder");
        }
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.rockCrusher), References.ModID + ":rockgrinder");
        for (ItemStack i : ItemHelper.getInfusionStones()) {
            registry.addRecipeCatalyst(i, References.ModID + ":infusion");
        }
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.lifeInfuser), References.ModID + ":infusion");

        for (ItemStack i : ItemHelper.getKnives()) {
            registry.addRecipeCatalyst(i, References.ModID + ":knife");
        }
    }

    public static void openRecipesGui(ItemStack stack) {
        IRecipesGui gui = JEIInternalPlugin.jeiRuntime.getRecipesGui();
        gui.show(new Focus(mezz.jei.api.recipe.IFocus.Mode.OUTPUT, stack));
    }


    private static List<HeatSourceJEI> getSources() {
        List<HeatSourceJEI> sources = new ArrayList<>();
        for (IBlockState state : HeatSources.getHeatSources().keySet()) {
            Item item = Item.getItemFromBlock(state.getBlock());
            if (item == Items.AIR)
                sources.add(new HeatSourceJEI(state.getBlock().getTranslationKey(),
                        HeatSources.getHeatSourceValue(state)));
            else
                sources.add(new HeatSourceJEI(new ItemStack(item),
                        HeatSources.getHeatSourceValue(state)));
        }
        return sources;
    }
}
