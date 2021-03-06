package com.skyresourcesclassic.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HeatSources {
    private static HashMap<IBlockState, Integer> validHeatSources;

    public HeatSources() {
        validHeatSources = new HashMap<>();
        ctAdded = new HashMap<>();
        ctRemoved = new ArrayList<>();
    }

    public static void addHeatSource(IBlockState blockState, int value) {
        validHeatSources.put(blockState, value);
    }

    public static boolean isValidHeatSource(BlockPos pos, World world) {
        if (!validHeatSources.containsKey(world.getBlockState(pos))) {
            for (IBlockState key : validHeatSources.keySet())
                if (key.getBlock() == world.getBlockState(pos).getBlock())
                    return true;

            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof IHeatSource)
                return getHeatSourceValue(pos, world) > 0;
        } else
            return true;

        return false;
    }

    private static boolean isValidHeatSource(IBlockState state) {
        if (!validHeatSources.containsKey(state)) {
            for (IBlockState key : validHeatSources.keySet())
                if (key.getBlock() == state.getBlock())
                    return true;

        } else
            return true;

        return false;
    }

    public static int getHeatSourceValue(BlockPos pos, World world) {
        IBlockState state = world.getBlockState(pos);

        if (validHeatSources.containsKey(state))
            return validHeatSources.get(state);
        else {
            for (IBlockState key : validHeatSources.keySet()) {
                int val = validHeatSources.get(key);
                if (key.getBlock() == state.getBlock())
                    return val;
            }

            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof IHeatSource) {
                IHeatSource source = (IHeatSource) tile;
                return source.getHeatValue();
            }
        }
        return 0;
    }

    public static int getHeatSourceValue(IBlockState state) {
        if (!isValidHeatSource(state))
            return 0;

        if (validHeatSources.containsKey(state))
            return validHeatSources.get(state);
        else {
            for (IBlockState key : validHeatSources.keySet()) {
                int val = validHeatSources.get(key);
                if (key.getBlock() == state.getBlock())
                    return val;
            }
        }
        return 0;
    }

    private static List<IBlockState> ctRemoved;
    private static HashMap<IBlockState, Integer> ctAdded;

    public static void removeCTHeatSource(IBlockState blockState) {
        ctRemoved.add(blockState);
    }

    public static void addCTHeatSource(IBlockState blockState, int value) {
        ctAdded.put(blockState, value);
    }

    public static void ctRecipes() {
        for (IBlockState s : ctRemoved)
            removeHeatSource(s);
        for (IBlockState s : ctAdded.keySet())
            addHeatSource(s, ctAdded.get(s));
    }

    private static void removeHeatSource(IBlockState blockState) {
        validHeatSources.remove(blockState);
    }

    public static HashMap<IBlockState, Integer> getHeatSources() {
        return validHeatSources;
    }
}
