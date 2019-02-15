package com.skyresourcesclassic.plugin.integdyn;

import com.skyresourcesclassic.plugin.IModPlugin;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class IntegratedDynamicsPlugin implements IModPlugin {

    public void preInit() {

    }

    public void init() {
        Item berries = Item.REGISTRY.get(new ResourceLocation("integrateddynamics", "menril_berries"));
        Item sapling = Item.REGISTRY.get(new ResourceLocation("integrateddynamics", "menril_sapling"));

        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(berries, 1), 12,
                new ArrayList<Object>(Arrays.asList(new ItemStack(Items.LIGHT_BLUE_DYE, 4), new ItemStack(Blocks.RED_MUSHROOM))));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1), 12,
                new ArrayList<Object>(Arrays.asList(new ItemStack(berries, 4), new ItemStack(Blocks.BIRCH_SAPLING, 1))));
    }

    public void initRenderers() {

    }

    public void postInit() {

    }
}
