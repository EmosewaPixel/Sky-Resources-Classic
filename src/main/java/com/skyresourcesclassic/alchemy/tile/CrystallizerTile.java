package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.base.tile.TileBase;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModFluids;
import com.skyresourcesclassic.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Random;

public class CrystallizerTile extends TileBase implements ITickable {
    public CrystallizerTile(int tier) {
        super("crystallizer");
        this.tier = tier;
    }

    private int timeCondense;
    private int randInterval;
    private int tier;

    @Override
    public void update() {
        updateRedstone();
        fluidUpdate();
    }

    private void fluidUpdate() {
        Random rand = world.rand;
        Block block = getBlockAbove();
        boolean success = false;
        if (!world.isRemote && getRedstoneSignal() == 0) {
            if (block instanceof FluidCrystalBlock) {
                FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
                FluidRegisterInfo.CrystalFluidType type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].type;

                ItemStack stack = new ItemStack(ModItems.metalCrystal[ModFluids
                        .crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].crystalIndex]);

                if (output(stack, true))
                    if (tier != 1 || type == FluidRegisterInfo.CrystalFluidType.NORMAL) {
                        if (crystalBlock.isSourceBlock(world, pos.up())
                                && crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up())))
                            timeCondense++;
                        else
                            timeCondense = 0;
                        if (timeCondense >= randInterval) {
                            if (rand.nextInt(50 + ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                                    .indexOf(crystalBlock)].rarity * 5) >= 40 + 15 * getCrystallizeEfficiencyFromTier())
                                world.setBlockToAir(pos.up());

                            output(stack, false);
                            success = true;
                            timeCondense = 0;
                            randInterval = (int) ((float) (20 * ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                                    .indexOf(crystalBlock)].rarity + 20) / getCrystallizeSpeedFromTier());
                        }
                    }
            }
        }
        if (success)
            world.playSound(null, pos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 1.0F,
                    2.2F / (rand.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("time", timeCondense);
        compound.setInteger("rand", randInterval);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        timeCondense = compound.getInteger("time");
        randInterval = compound.getInteger("rand");
    }

    private Block getBlockAbove() {
        return this.world.getBlockState(pos.add(0, 1, 0)).getBlock();
    }

    private boolean output(ItemStack output, boolean simulate) {
        TileEntity te = getWorld().getTileEntity(pos.down());
        if (te != null) {
            if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
                for (int i = 0; i < inventory.getSlots(); i++)
                    if (inventory.insertItem(i, output, simulate).isEmpty())
                        return true;
                return false;
            }
        }

        if (getWorld().getBlockState(pos.down()).getBlockFaceShape(world, pos.down(), EnumFacing.UP) != BlockFaceShape.SOLID)
            if (simulate)
                return true;
            else {
                Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() - 0.5F, pos.getZ() + 0.5F, output);
                world.spawnEntity(entity);
                timeCondense = 0;
            }

        return false;
    }

    private float getCrystallizeSpeedFromTier() {
        switch (tier) {
            case 1:
                return 0.5f;
            case 2:
                return 1;
            case 3:
                return 2.5f;
            case 4:
                return 4;
        }
        return 1;
    }

    private float getCrystallizeEfficiencyFromTier() {
        switch (tier) {
            case 1:
                return 1;
            case 2:
                return 1.5f;
            case 3:
                return 2;
            case 4:
                return 3;
        }
        return 1;
    }
}
