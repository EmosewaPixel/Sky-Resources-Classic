package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.alchemy.block.CondenserBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.base.tile.TileBase;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class CondenserTile extends TileBase implements ITickable {
    public CondenserTile() {
        super("condenser");
    }

    private int timeCondense;

    private int getTier() {
        return ((CondenserBlock) getWorld().getBlockState(pos).getBlock()).getTier();
    }

    @Override
    public void update() {
        updateRedstone();
        crystalFluidUpdate();
    }

    private void crystalFluidUpdate() {
        Random rand = world.rand;
        Block block = getBlockAbove();
        if (block instanceof FluidCrystalBlock && getRedstoneSignal() == 0) {
            FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
            String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;
            FluidRegisterInfo.CrystalFluidType fluidType = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                    .indexOf(crystalBlock)].type;

            String oreDictCheck = "ingot" + RandomHelper.capatilizeString(type);

            ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
            stack.setCount(1);

            if ((getTier() != 1 || fluidType == FluidRegisterInfo.CrystalFluidType.NORMAL) && crystalBlock.isSourceBlock(world, pos.up())
                    && crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up()))
                    && OreDictionary.getOres(oreDictCheck).size() > 0
                    && HeatSources.isValidHeatSource(pos.down(), world)) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(),
                        pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (!world.isRemote)
                    timeCondense += HeatSources.getHeatSourceValue(pos.down(), world);
            } else if (!world.isRemote)
                timeCondense = 0;

            if (timeCondense >= getTimeToCondense(crystalBlock)) {
                world.setBlockToAir(pos.up());
                Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F, stack);
                world.spawnEntity(entity);
                timeCondense = 0;
            }
        }
    }

    private int getTimeToCondense(FluidCrystalBlock block) {
        return (int) (ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity
                * ConfigOptions.condenser.condenserProcessTimeBase
                * (ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                .indexOf(block)].type == FluidRegisterInfo.CrystalFluidType.NORMAL ? 1 : 20)
                * (1F / getTier()));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("time", timeCondense);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        timeCondense = compound.getInteger("time");
    }

    private Block getBlockAbove() {
        return getWorld().getBlockState(pos.up()).getBlock();
    }
}
