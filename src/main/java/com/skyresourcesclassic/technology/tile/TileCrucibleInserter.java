package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.alchemy.tile.CrucibleTile;
import com.skyresourcesclassic.base.gui.ItemHandlerSpecial;
import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class TileCrucibleInserter extends TileItemInventory {
    public TileCrucibleInserter() {
        super("crucibleInserter", 1);
        this.setInventory(new ItemHandlerSpecial(1) {
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                TileCrucibleInserter.this.markDirty();
            }

            public boolean isItemValid(int slot, ItemStack stack) {
                if (!(getWorld().getTileEntity(pos.down()) instanceof CrucibleTile))
                    return false;
                ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

                int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

                CrucibleTile tile = (CrucibleTile) getWorld().getTileEntity(pos.down());

                if (tile == null)
                    return false;

                if (tile.getItemAmount() + amount <= ConfigOptions.crucible.crucibleCapacity.get() && recipe != null) {
                    ItemStack input = (ItemStack) recipe.getInputs().get(0);

                    if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null) {
                        tile.itemIn = input;
                    }

                    if (tile.itemIn == input) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            @Nonnull
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                ProcessRecipe recipe = ProcessRecipeManager.crucibleRecipes.getRecipe(stack, 0, false, false);

                int amount = recipe == null ? 0 : recipe.getFluidOutputs().get(0).amount;

                CrucibleTile tile = (CrucibleTile) getWorld().getTileEntity(pos.down());

                if (tile == null)
                    return stack;

                if (tile.getItemAmount() + amount <= ConfigOptions.crucible.crucibleCapacity.get() && recipe != null) {
                    ItemStack input = (ItemStack) recipe.getInputs().get(0);

                    if (tile.getTank().getFluid() == null || tile.getTank().getFluid().getFluid() == null) {
                        tile.itemIn = input;
                    }

                    if (tile.itemIn == input) {
                        tile.itemAmount += amount;
                        stack.shrink(1);
                    }
                }
                return stack;
            }
        });
    }
}
