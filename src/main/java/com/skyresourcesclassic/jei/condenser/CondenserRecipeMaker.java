package com.skyresourcesclassic.jei.condenser;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CondenserRecipeMaker {
    public static List<CondenserRecipeJEI> getRecipes() {
        ArrayList<CondenserRecipeJEI> recipes = new ArrayList<CondenserRecipeJEI>();

        for (int i = 0; i < ModFluids.crystalFluids.size(); i++) {
            if (!new ItemTags.Wrapper(new ResourceLocation("forge:ingots/" + ModFluids.crystalFluidInfos()[i].name)).getAllElements().isEmpty()) {
                Item ingot = new ItemTags.Wrapper(new ResourceLocation("forge:ingots/" + ModFluids.crystalFluidInfos()[i].name)).getAllElements().iterator().next();
                CondenserRecipeJEI addRecipe = new CondenserRecipeJEI(new ItemStack(ingot),
                        new FluidStack(ModFluids.crystalFluids.get(i), 1000),
                        ModFluids.crystalFluidInfos()[i].rarity * ConfigOptions.condenser.condenserProcessTimeBase.get()
                                * (ModFluids.crystalFluidInfos()[i].type == FluidRegisterInfo.CrystalFluidType.MOLTEN ? 20 : 1));
                recipes.add(addRecipe);
            }
        }

        return recipes;
    }
}
