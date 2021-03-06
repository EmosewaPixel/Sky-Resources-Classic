package com.skyresourcesclassic.jei.combustion;

import com.skyresourcesclassic.References;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

public class CombustionRecipeCategory implements IRecipeCategory {
    private static final int[] slotInputStacks = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int slotOutput = 9;
    public static final String UUID = References.ModID + ":combustion";

    private final IDrawable background;

    private IDrawable heatBar;

    private final String localizedName = I18n.translateToLocalFormatted("jei.skyresourcesclassic.recipe.combustion");

    public CombustionRecipeCategory(IGuiHelper guiHelper) {
        super();
        background = guiHelper.createDrawable(new ResourceLocation(References.ModID, "textures/gui/jei/combustion.png"),
                0, 0, 137, 71);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(
                new ResourceLocation(References.ModID, "textures/gui/gui_icons.png"), 59, 14, 8, 69);
        heatBar = guiHelper.createAnimatedDrawable(arrowDrawable, 200,
                mezz.jei.api.gui.IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        heatBar.draw(minecraft, 128, 1);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public String getUid() {
        return UUID;
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        for (int i : slotInputStacks) {
            layout.getItemStacks().init(i, true, (i % 3) * 18 + 3, (i / 3) * 18 + 9);

        }
        layout.getItemStacks().init(slotOutput, false, 97, 27);

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        for (int i = 0; i < inputs.size(); i++) {
            layout.getItemStacks().set(slotInputStacks[i], inputs.get(i));
        }
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
        layout.getItemStacks().set(slotOutput, outputs.get(0));
    }

    @Override
    public String getModName() {
        return References.ModName;
    }

}
