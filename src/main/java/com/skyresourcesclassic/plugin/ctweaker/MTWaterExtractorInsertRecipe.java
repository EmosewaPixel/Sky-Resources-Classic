package com.skyresourcesclassic.plugin.ctweaker;

import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.init.Fluids;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.Collections;

@ZenClass("mods.skyresourcesclassic.waterextractor.insert")
@ZenRegister
public class MTWaterExtractorInsertRecipe extends MTRecipeBase {
    @ZenMethod
    public static void addRecipe(IItemStack output, IItemStack input, int waterIn) {
        addRecipe(
                new ProcessRecipe(
                        Arrays.asList(CraftTweakerPlugin.toStack(output)),
                        Arrays.asList(CraftTweakerPlugin.toStack(input),
                                new FluidStack(Fluids.WATER, waterIn)), 0, "waterextractor-insert"),
                ProcessRecipeManager.waterExtractorInsertRecipes);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        removeRecipe(new ProcessRecipe(Arrays.asList(CraftTweakerPlugin.toStack(output)), Collections.emptyList(), 0,
                "waterextractor-insert"), ProcessRecipeManager.waterExtractorInsertRecipes);
    }

}
