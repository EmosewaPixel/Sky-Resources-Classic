package com.skyresourcesclassic.plugin.tconstruct;

import com.skyresourcesclassic.plugin.IModPlugin;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class TConPlugin implements IModPlugin {
    public void preInit() {

    }

    public void init() {
        Item edible = Item.REGISTRY.get(new ResourceLocation("tconstruct", "edible"));
        Item sapling = Item.REGISTRY.get(new ResourceLocation("tconstruct", "slime_sapling"));
        Block dirt = Block.REGISTRY.get(new ResourceLocation("tconstruct", "slime_dirt"));

        ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(edible, 1, 1), 350,
                new ArrayList<>(Arrays.asList(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.DYE, 2, 4))));
        ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(edible, 1, 2), 350,
                new ArrayList<>(Arrays.asList(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.DYE, 2, 5))));
        ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(edible, 2, 4), 760, new ArrayList<Object>(
                Arrays.asList(new ItemStack(Items.MAGMA_CREAM), new ItemStack(Items.SLIME_BALL))));

        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1, 0), 12,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 16, 1), "treeSapling")));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1, 1), 12,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 16, 2), "treeSapling")));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1, 2), 16,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 16, 4), "treeSapling")));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(dirt, 1, 0), 8,
                new ArrayList<>(Arrays.asList(new ItemStack(Items.SLIME_BALL, 4), new ItemStack(Blocks.DIRT))));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(dirt, 1, 1), 8,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 4, 1), new ItemStack(Blocks.DIRT))));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(dirt, 1, 2), 8,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 4, 2), new ItemStack(Blocks.DIRT))));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(dirt, 1, 3), 8,
                new ArrayList<>(Arrays.asList(new ItemStack(edible, 4, 4), new ItemStack(Blocks.DIRT))));
    }

    public void initRenderers() {

    }

    public void postInit() {

    }
}
