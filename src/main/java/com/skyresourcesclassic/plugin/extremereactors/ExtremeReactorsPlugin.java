package com.skyresourcesclassic.plugin.extremereactors;

import java.util.ArrayList;
import java.util.Arrays;

import com.skyresourcesclassic.base.guide.SkyResourcesGuide;
import com.skyresourcesclassic.plugin.IModPlugin;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItems;

import com.skyresourcesclassic.registry.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ExtremeReactorsPlugin implements IModPlugin
{

	public void preInit()
	{

	}

	public void init()
	{
		Item dust = Item.REGISTRY.get(new ResourceLocation("bigreactors", "dustmetals"));
		Item ingot = Item.REGISTRY.get(new ResourceLocation("bigreactors", "ingotmetals"));

		ProcessRecipeManager.combustionRecipes.addRecipe(new ItemStack(dust, 5), 1200, new ArrayList<Object>(
				Arrays.asList(new ItemStack(Items.REDSTONE, 2), new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(ModItems.itemComponent[15], 2))));

		SkyResourcesGuide.addPage("extremereactors", "guide.skyresourcesclassic.misc", new ItemStack(ingot, 1));
	}

	public void initRenderers()
	{

	}

	public void postInit()
	{

	}
}
