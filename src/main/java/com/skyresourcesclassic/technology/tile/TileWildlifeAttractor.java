package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.tile.TileGenericPower;
import com.skyresourcesclassic.registry.ModItems;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileWildlifeAttractor extends TileGenericPower implements ITickable, IFluidHandler {
    private FluidTank tank;
    private int powerUsage = 40;
    private int fluidUsage = 20;
    private int matterLeft = 0;

    public TileWildlifeAttractor() {
        super("wildlifeAttractor", 100000, 2000, 0, 1, null, new Integer[]{0});
        tank = new FluidTank(4000);
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (this.getRedstoneSignal() == 0) {
                if (matterLeft <= 0) {
                    if (!getInventory().getStackInSlot(0).isEmpty() && getInventory().getStackInSlot(0)
                            .isItemEqual(new ItemStack(ModItems.itemComponent[8]))) {
                        getInventory().getStackInSlot(0).shrink(1);
                        matterLeft = 320;
                    }
                }
                if (tank.getFluidAmount() >= fluidUsage && tank.getFluid().getFluid() == FluidRegistry.WATER
                        && energy >= powerUsage && matterLeft > 0) {
                    spawnRandomAnimal();
                    tank.drain(fluidUsage, true);
                    energy -= powerUsage;
                    matterLeft--;
                }
            }
        }
        markDirty();
    }

    public int getMatterLeft() {
        return matterLeft;
    }

    private EntityLiving[] wildlifeAttractorAnimalIDs = {"minecraft:sheep", "minecraft:cow",
            "minecraft:chicken", "minecraft:pig", "minecraft:rabbit", "minecraft:squid", "minecraft:horse",
            "minecraft:parrot"};

    private void spawnRandomAnimal() {
        if (world.rand.nextInt(600) == 0) {
            String randomID = wildlifeAttractorAnimalIDs[world.rand
                    .nextInt(wildlifeAttractorAnimalIDs.length)];
            EntityLiving e = (EntityLiving) EntityList.createEntityByIDFromName(new ResourceLocation(randomID), world);
            e.setLocationAndAngles(pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f,
                    world.rand.nextFloat() * 360.0F, 0.0F);

            Event.Result canSpawn = net.minecraftforge.event.ForgeEventFactory
                    .canEntitySpawn(e, world, pos.getX() + 0.5f, pos.getY() + 1f, pos.getZ() + 0.5f, false);
            boolean isNotColliding = e.isNotColliding();
            if (canSpawn == Event.Result.ALLOW
                    || (canSpawn == Event.Result.DEFAULT && isNotColliding)) {
                if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(e, world, pos.getX() + 0.5f,
                        pos.getY() + 1f, pos.getZ() + 0.5f, null))
                    e.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(e)), null, null);

                if (e.isNotColliding()) {
                    world.spawnEntity(e);
                } else {
                    e.remove();
                }
            }
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluid() == FluidRegistry.WATER) {
            return tank.fill(resource, doFill);
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

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        compound = super.write(compound);

        compound.setInt("matter", matterLeft);
        tank.writeToNBT(compound);

        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        matterLeft = compound.getInt("matter");

        tank.readFromNBT(compound);
    }

    public FluidTank getTank() {
        return tank;
    }
}