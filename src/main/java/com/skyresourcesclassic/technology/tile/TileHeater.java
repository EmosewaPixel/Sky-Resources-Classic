package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.IHeatSource;
import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.technology.block.BlockHeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class TileHeater extends TileItemInventory implements ITickable, IHeatSource {
    public TileHeater(int tier) {
        super("heater", ModEntities.heaterType(tier), 1, null, new Integer[]{0});
        this.tier = tier;
    }

    public int fuelBurnTime;
    public int currentItemBurnTime;
    private int tier;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (fuelBurnTime > 0 && this.getRedstoneSignal() > 0) {
                fuelBurnTime--;
                world.setBlockState(getPos(),
                        world.getBlockState(getPos()).with(BlockHeater.RUNNING, true), 3);
            } else
                world.setBlockState(getPos(),
                        world.getBlockState(getPos()).with(BlockHeater.RUNNING, false), 3);

            if (this.getRedstoneSignal() > 0) {
                if (fuelBurnTime > 0 || this.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
                    if (fuelBurnTime == 0
                            && isValidFuel(getInventory().getStackInSlot(0))) {
                        this.currentItemBurnTime = this.fuelBurnTime = getFuelBurnTime(getInventory().getStackInSlot(0));

                        if (fuelBurnTime > 0) {
                            if (this.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
                                this.getInventory().getStackInSlot(0).shrink(1);

                                if (this.getInventory().getStackInSlot(0).getCount() == 0) {
                                    this.getInventory().setStackInSlot(0, getInventory().getStackInSlot(0).getItem()
                                            .getContainerItem(getInventory().getStackInSlot(0)));
                                }
                            }
                        }
                    }
                }
            }
            this.markDirty();
        }
    }

    private int getBurnTime(ItemStack stack) {
        int burnTime = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, burnTime == -1 ? TileEntityFurnace.getBurnTimes().getOrDefault(stack, 0) : burnTime);
    }

    private int getFuelBurnTime(ItemStack stack) {
        return getBurnTime(stack) * 5 / getHeat();
    }

    private boolean isValidFuel(ItemStack stack) {
        return !(getBurnTime(stack) <= 0 || getFuelBurnTime(stack) <= 0);
    }

    @Override
    public int getHeatValue() {
        if (fuelBurnTime > 0 && this.getRedstoneSignal() > 0)
            return getHeat();
        return 0;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("fuel", fuelBurnTime);
        compound.setInt("item", currentItemBurnTime);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        fuelBurnTime = compound.getInt("fuel");
        currentItemBurnTime = compound.getInt("item");
    }

    private int getHeat() {
        if (tier == 1)
            return 5;
        else
            return 10;
    }
}
