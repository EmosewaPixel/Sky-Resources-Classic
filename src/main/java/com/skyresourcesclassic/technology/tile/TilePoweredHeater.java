package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.IHeatSource;
import com.skyresourcesclassic.base.tile.TileGenericPower;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.technology.block.BlockHeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class TilePoweredHeater extends TileGenericPower implements ITickable, IEnergyStorage, IHeatSource {
    public TilePoweredHeater(int tier) {
        super("poweredHeater", ModEntities.POWERED_HEATER, 100000, 2000, 0);
        this.tier = tier;
    }

    private int powerUsage = 120 * getHeat() / 30;
    private int tier;

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (getEnergyStored() >= powerUsage && this.getRedstoneSignal() > 0) {
                internalExtractEnergy(powerUsage, false);
                world.setBlockState(getPos(),
                        world.getBlockState(getPos()).with(BlockHeater.RUNNING, true), 3);
            } else
                world.setBlockState(getPos(),
                        world.getBlockState(getPos()).with(BlockHeater.RUNNING, false), 3);

            this.markDirty();
        }
    }

    @Override
    public int getHeatValue() {
        if (getEnergyStored() >= powerUsage && this.getRedstoneSignal() > 0)
            return getHeat();
        return 0;
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }


    private int getHeat() {
        if (tier == 3)
            return 30;
        else
            return 120;
    }
}
