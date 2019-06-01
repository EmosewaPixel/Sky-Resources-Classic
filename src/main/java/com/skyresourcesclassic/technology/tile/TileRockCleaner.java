package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.tile.TileGenericPower;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.List;

public class TileRockCleaner extends TileGenericPower implements ITickable, IFluidHandler {
    public TileRockCleaner() {
        super("rock_cleaner", 100000, 2000, 0, 4, new Integer[]{1, 2, 3}, new Integer[]{0});
        tank = new FluidTank(4000);
    }

    private int powerUsage = 80;
    private int curProgress = 0;

    private NonNullList<ItemStack> bufferStacks = NonNullList.create();

    @Override
    public void update() {
        if (!this.world.isRemote) {
            if (bufferStacks.size() > 0 && fullOutput()) {
                this.addToOutput(1);
                this.addToOutput(2);
                this.addToOutput(3);
            } else {
                boolean hasRecipes = hasRecipes();
                if (curProgress < 100 && getEnergyStored() >= powerUsage && hasRecipes && tank.getFluidAmount() >= 250
                        && bufferStacks.size() == 0) {
                    internalExtractEnergy(powerUsage, false);
                    curProgress += 20;
                } else if (!hasRecipes)
                    curProgress = 0;
                if (curProgress >= 100 && hasRecipes) {
                    List<ProcessRecipe> recipes = ProcessRecipeManager.cauldronCleanRecipes.getRecipes();
                    for (ProcessRecipe r : recipes)

                    {
                        if (((ItemStack) r.getInputs().get(0)).isItemEqual(this.getInventory().getStackInSlot(0))) {
                            if (!world.isRemote) {
                                // int level =
                                // EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE,
                                // this.getInventory().getStackInSlot(0));
                                float chance = r.getIntParameter() * (((float) 0 + 3F) / 3F);
                                while (chance >= 1) {
                                    bufferStacks.add(r.getOutputs().get(0).copy());
                                    chance -= 1;
                                }
                                if (this.world.rand.nextFloat() <= chance)
                                    bufferStacks.add(r.getOutputs().get(0).copy());
                            }
                        }
                    }
                    tank.drain(250, true);
                    this.getInventory().getStackInSlot(0).shrink(1);
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
        return !this.getInventory().getStackInSlot(1).isEmpty() && !this.getInventory().getStackInSlot(2).isEmpty()
                && !this.getInventory().getStackInSlot(3).isEmpty();
    }

    private boolean hasRecipes() {
        List<ProcessRecipe> recipes = ProcessRecipeManager.cauldronCleanRecipes.getRecipes();
        for (ProcessRecipe r : recipes)

        {
            if (r != null && this.getInventory().getStackInSlot(0).isItemEqual((ItemStack) r.getInputs().get(0))
                    && !((ItemStack) r.getInputs().get(0)).isEmpty() && !this.getInventory().getStackInSlot(0).isEmpty()
                    && !r.getOutputs().get(0).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int getProgress() {
        return curProgress;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);

        compound.setTag("buffer", bufferListWrite());
        compound.setInteger("progress", curProgress);

        tank.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        bufferListRead(compound.getCompoundTag("buffer"));
        curProgress = compound.getInteger("progress");

        tank.readFromNBT(compound);
    }

    private NBTTagCompound bufferListWrite() {
        NBTTagList nbtTagList = new NBTTagList();
        for (ItemStack stack : bufferStacks) {
            if (!stack.isEmpty()) {
                NBTTagCompound itemTag = new NBTTagCompound();
                stack.writeToNBT(itemTag);
                nbtTagList.appendTag(itemTag);
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setTag("Items", nbtTagList);
        return nbt;
    }

    private void bufferListRead(NBTTagCompound nbt) {
        NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
            bufferStacks.add(new ItemStack(itemTags));
        }
    }

    private FluidTank tank;

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluid() == FluidRegistry.WATER) {
            int filled = tank.fill(resource, doFill);

            return filled;
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return null;
    }

    public FluidTank getTank() {
        return tank;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}
