package com.skyresourcesclassic.plugin.techreborn;

import com.skyresourcesclassic.base.guide.SkyResourcesGuide;
import com.skyresourcesclassic.plugin.IModPlugin;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public class TechRebornPlugin implements IModPlugin {

    public void preInit() {

    }

    public void init() {
        Item part = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "part"));
        Item sapling = Item.REGISTRY.getObject(new ResourceLocation("techreborn", "rubbersapling"));

        ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(part, 1, 31), 350, new ArrayList<Object>(
                Arrays.asList(new ItemStack(Items.SLIME_BALL, 2), new ItemStack(ModItems.itemComponent[8], 4))));
        ProcessRecipeManager.infusionRecipes.addRecipe(new ItemStack(sapling, 1), 12,
                new ArrayList<Object>(Arrays.asList(new ItemStack(part, 4, 31), new ItemStack(Blocks.SAPLING, 1, 1))));

        SkyResourcesGuide.addPage("techreborn", "guide.skyresourcesclassic.misc", new ItemStack(part, 1, 31));
    }

    public void initRenderers() {

    }

    public void postInit() {

    }
}
