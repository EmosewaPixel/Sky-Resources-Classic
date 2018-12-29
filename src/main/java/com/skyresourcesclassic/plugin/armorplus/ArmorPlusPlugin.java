package com.skyresourcesclassic.plugin.armorplus;

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

public class ArmorPlusPlugin implements IModPlugin {

    public void preInit() {

    }

    public void init() {
        Item crystal = Item.REGISTRY.get(new ResourceLocation("armorplus", "lava_crystal"));

        ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(crystal), 1200,
                new ArrayList<Object>(Arrays.asList(new ItemStack(ModItems.metalCrystal[0], 16),
                        new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.LAVA_BUCKET))));

        SkyResourcesGuide.addPage("armorplus", "guide.skyresourcesclassic.misc", new ItemStack(crystal));
    }

    public void postInit() {

    }

    public void initRenderers() {

    }

}
