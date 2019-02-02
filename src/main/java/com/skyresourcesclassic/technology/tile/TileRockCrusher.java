package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.gui.ItemHandlerSpecial;
import com.skyresourcesclassic.base.tile.TileGenericPower;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.technology.item.ItemRockGrinder;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import java.util.Collections;

public class TileRockCrusher extends TileGenericPower implements ITickable {
    public TileRockCrusher() {
        super("rockCrusher", 100000, 2000, 0, 5, new Integer[]{2, 3, 4}, new Integer[]{0, 1});
        this.setInventory(new ItemHandlerSpecial(5, new Integer[]{2, 3, 4}, new Integer[]{0, 1}) {
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                TileRockCrusher.this.markDirty();
            }

            public boolean isItemValid(int slot, ItemStack stack) {
                if (slot == 0)
                    return stack.getItem() instanceof ItemRockGrinder;
                else
                    return !(stack.getItem() instanceof ItemRockGrinder);
            }
        });
    }

    private int powerUsage = 80;
    private int curProgress;

    private NonNullList<ItemStack> bufferStacks = NonNullList.create();

    private int base;

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (bufferStacks.size() > 0 && !fullOutput()) {
                this.addToOutput(2);
                this.addToOutput(3);
                this.addToOutput(4);
            } else {
                boolean hasRecipes = hasRecipes();
                if (curProgress < 100 && getEnergyStored() >= powerUsage && hasRecipes && hasRockGrinder()
                        && bufferStacks.size() == 0) {
                    internalExtractEnergy(powerUsage, false);
                    int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY,
                            this.getInventory().getStackInSlot(0));
                    String materiaName = ((ItemRockGrinder) this.getInventory().getStackInSlot(0).getItem())
                            .getTier().toString();
                    switch (materiaName) {
                        case "stone":
                            base = 2;
                            break;
                        case "iron":
                            base = 3;
                            break;
                        case "diamond":
                            base = 4;
                            break;
                        case "gold":
                            base = 6;
                            break;
                        default:
                            base = 1;
                    }
                    curProgress += Math.floor(Math.sqrt(base * 2 * (level + 1)));
                } else if (!hasRecipes)
                    curProgress = 0;
                if (curProgress >= 100 && hasRecipes && hasRockGrinder()) {
                    ProcessRecipe recMachine = new ProcessRecipe(Collections.singletonList(this.getInventory().getStackInSlot(1)),
                            Integer.MAX_VALUE, "rockgrinder");
                    for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes()) {
                        if (r != null && recMachine.isInputRecipeEqualTo(r, false)) {
                            int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE,
                                    this.getInventory().getStackInSlot(0));
                            float chance = r.getIntParameter() * (((float) level + 3F) / 3F);
                            while (chance >= 1) {
                                bufferStacks.add(r.getOutputs().get(0).copy());
                                chance -= 1;
                            }
                            if (this.world.rand.nextFloat() <= chance)
                                bufferStacks.add(r.getOutputs().get(0).copy());
                        }
                    }
                    this.getInventory().getStackInSlot(1).shrink(1);
                    if (this.getInventory().getStackInSlot(0).attemptDamageItem(1, this.world.rand, null))
                        this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
                    curProgress = 0;
                }
            }
            this.markDirty();
        }
    }

    private void addToOutput(int slot) {
        if (bufferStacks.size() > 0) {
            ItemStack stack = this.getInventory().insertInternalItem(slot, bufferStacks.get(bufferStacks.size() - 1),
                    false);
            bufferStacks.set(bufferStacks.size() - 1, stack);
            if (bufferStacks.get(bufferStacks.size() - 1).isEmpty())
                bufferStacks.remove(bufferStacks.size() - 1);
        }
    }

    private boolean fullOutput() {
        return !this.getInventory().getStackInSlot(2).isEmpty() && !this.getInventory().getStackInSlot(3).isEmpty()
                && !this.getInventory().getStackInSlot(4).isEmpty();
    }

    private boolean hasRecipes() {
        if (this.getInventory().getStackInSlot(1).isEmpty())
            return false;
        ProcessRecipe recMachine = new ProcessRecipe(Collections.singletonList(this.getInventory().getStackInSlot(1)),
                Integer.MAX_VALUE, "rockgrinder");
        for (ProcessRecipe r : ProcessRecipeManager.rockGrinderRecipes.getRecipes()) {
            if (r != null && recMachine.isInputRecipeEqualTo(r, false))
                return true;
        }
        return false;
    }

    private boolean hasRockGrinder() {
        if (this.getInventory().getStackInSlot(0).getItem() instanceof ItemRockGrinder) {
            return this.getInventory().getStackInSlot(0).getDamage() <= this.getInventory().getStackInSlot(0)
                    .getMaxDamage();
        }
        return false;
    }

    public int getProgress() {
        return curProgress;
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        compound = super.write(compound);

        compound.setTag("buffer", bufferListWrite());
        compound.setInt("progress", curProgress);

        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        bufferListRead(compound.getTag("buffer"));
        curProgress = compound.getInt("progress");
    }

    private NBTTagCompound bufferListWrite() {
        NBTTagList nbtTagList = new NBTTagList();
        for (ItemStack item : bufferStacks) {
            if (!item.isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                item.write(itemTag);
                nbtTagList.add(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        return nbt;
    }

    private void bufferListRead(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            NBTTagCompound itemTags = (NBTTagCompound) tagList.getTag(i);
            bufferStacks.add(new ItemStack(itemTags));
        }
    }
}
