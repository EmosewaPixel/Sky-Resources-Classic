package com.bartz24.skyresources.jei.freezer;

import com.bartz24.skyresources.ItemHelper;
import com.bartz24.skyresources.References;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class FreezerRecipeCategory extends BlankRecipeCategory
{
	private static final int slotInputStack = 0;
	private static final int slotOutput = 1;

	private final IDrawable background;

	private final String localizedName = I18n
			.translateToLocalFormatted("jei.skyresources.recipe.freezer");

	public FreezerRecipeCategory(IGuiHelper guiHelper)
	{
		super();
		background = guiHelper
				.createDrawable(
						new ResourceLocation(References.ModID,
								"textures/gui/jei/concentrator.png"),
						0, 40, 90, 40);
	}

	@Override
	public void drawAnimations(Minecraft minecraft)
	{
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public String getUid()
	{
		return References.ModID + ":freezer";
	}

	@Override
	public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper)
	{
		layout.getItemStacks().init(slotInputStack, true, 1, 5);
		layout.getItemStacks().init(slotOutput, false, 64, 5);

		if (wrapper instanceof FreezerRecipeJEI)
		{
			FreezerRecipeJEI infusionRecipe = (FreezerRecipeJEI) wrapper;
			for (int i = 0; i < infusionRecipe.getInputs().size(); i++)
			{
				layout.getItemStacks().set(slotInputStack,
						(ItemStack) infusionRecipe.getInputs().get(i));
			}
			layout.getItemStacks().set(slotOutput, infusionRecipe.getOutputs());
		}
	}

}
