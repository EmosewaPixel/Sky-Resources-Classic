package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.base.tile.TileBase;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.registry.ModFluids;
import com.skyresourcesclassic.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Random;

public class CrystallizerTile extends TileBase implements ITickable {
    public CrystallizerTile(int tier) {
        super("crystallizer", ModEntities.CRYSTALLIZER);
        this.tier=tier;
    }

    private int timeCondense;
    private int randInterval;
    private int tier;

    @Override
    public void tick() {
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

                if (tier != 1 || type == FluidRegisterInfo.CrystalFluidType.NORMAL) {
                    if (crystalBlock.isSourceBlock(world, pos.up())
                            && crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up())))
                        timeCondense++;
                    else
                        timeCondense = 0;
                    if (timeCondense >= randInterval) {
                        if (rand.nextInt(50 + ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                                .indexOf(crystalBlock)].rarity * 5) >= 40 + 15 * getCrystallizeEfficiencyFromTier())
                            world.removeBlock(pos.up());

                        ItemStack stack = new ItemStack(ModItems.metalCrystal[ModFluids
                                .crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].crystalIndex]);
                        ejectResultSlot(stack);
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
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("time", timeCondense);
        compound.setInt("rand", randInterval);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        timeCondense = compound.getInt("time");
        randInterval = compound.getInt("rand");
    }

    private Block getBlockAbove() {
        return this.world.getBlockState(pos.add(0, 1, 0)).getBlock();
    }

    private void ejectResultSlot(ItemStack output) {
        if (!world.isRemote) {

            BlockPos facingPos = getPos().down();

            TileEntity tile = world.getTileEntity(facingPos);

            if (tile != null) {
                if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)) {
                    output = RandomHelper.fillInventory(
                            tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP), output);
                } else if (tile instanceof IInventory) {
                    output = RandomHelper.fillInventory((IInventory) tile, output);
                }
            }

            if (output != ItemStack.EMPTY && output.getCount() > 0) {
                EntityItem item = new EntityItem(world, pos.down().getX() + 0.5f, pos.down().getY() + 0.5f,
                        pos.down().getZ() + 0.5f, output.copy());
                item.motionY = 0;
                item.motionX = 0;
                item.motionZ = 0;
                world.spawnEntity(item);
            }
        }
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
