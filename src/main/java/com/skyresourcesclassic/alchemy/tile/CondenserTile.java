package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.base.tile.TileBase;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class CondenserTile extends TileBase implements ITickable {
    public CondenserTile(int tier) {
        super("condenser");
        this.tier = tier;
    }

    private int timeCondense;
    private int tier;

    @Override
    public void tick() {
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

            if ((tier != 1 || fluidType == FluidRegisterInfo.CrystalFluidType.NORMAL) && crystalBlock.isSourceBlock(world, pos.up())
                    && crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up()))
                    && OreDictionary.getOres(oreDictCheck).size() > 0
                    && HeatSources.isValidHeatSource(pos.down(), world)) {
                this.world.spawnParticle(Particles.SMOKE, pos.getX() + rand.nextFloat(),
                        pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (!world.isRemote)
                    timeCondense += HeatSources.getHeatSourceValue(pos.down(), world);
            } else if (!world.isRemote)
                timeCondense = 0;

            if (timeCondense >= getTimeToCondense(crystalBlock)) {
                world.removeBlock(pos.up());
                ItemStack stack = OreDictionary.getOres(oreDictCheck).get(0).copy();
                stack.setCount(1);
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
                * (1F / tier));
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("time", timeCondense);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        timeCondense = compound.getInt("time");
    }

    private Block getBlockAbove() {
        return getWorld().getBlockState(pos.add(0, 1, 0)).getBlock();
    }
}
