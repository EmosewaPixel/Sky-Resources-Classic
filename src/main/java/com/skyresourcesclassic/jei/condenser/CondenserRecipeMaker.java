package com.skyresourcesclassic.jei.condenser;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class CondenserRecipeMaker {
    public static List<CondenserRecipeJEI> getRecipes() {
        ArrayList<CondenserRecipeJEI> recipes = new ArrayList<CondenserRecipeJEI>();

        for (int i = 0; i < ModFluids.crystalFluids.size(); i++) {
            if (OreDictionary.getOres("ingot" + RandomHelper.capitalizeString(ModFluids.crystalFluidInfos()[i].name))
                    .size() > 0) {
                ItemStack ingot = OreDictionary
                        .getOres("ingot" + RandomHelper.capitalizeString(ModFluids.crystalFluidInfos()[i].name)).get(0)
                        .copy();
                ingot.setCount(1);
                CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(ingot,
                        new FluidStack(ModFluids.crystalFluids.get(i), 1000),
                        ModFluids.crystalFluidInfos()[i].rarity * ConfigOptions.condenser.condenserProcessTimeBase.get()
                                * (ModFluids.crystalFluidInfos()[i].type == FluidRegisterInfo.CrystalFluidType.MOLTEN ? 20 : 1));
                recipes.add(addRecipe);
            }
        }

        return recipes;
    }
}
