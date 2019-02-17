package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.technology.block.BlockFreezer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;

public class FreezerTile extends MiniFreezerTile implements ITickable {
    public FreezerTile() {
        super(6, ModEntities.FREEZER, new Integer[]{3, 4, 5}, new Integer[]{0, 1, 2});
    }

    public float getFreezerSpeed() {
        return 1;
    }

    void updateMulti2x1() {
        IBlockState state = this.world.getBlockState(pos);
        IBlockState stateUp = this.world.getBlockState(pos.up());
        IBlockState stateDown = this.world.getBlockState(pos.down());
        if (state.getBlock() instanceof BlockFreezer) {
            if (stateUp.getBlock() instanceof BlockFreezer
                    && state.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM
                    && stateUp.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM) {
                world.setBlockState(pos.up(), stateUp.with(BlockFreezer.PART, BlockFreezer.EnumPartType.TOP));
            } else if (!(stateDown.getBlock() instanceof BlockFreezer)
                    && state.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP) {
                world.setBlockState(pos, state.with(BlockFreezer.PART, BlockFreezer.EnumPartType.BOTTOM));
            }
        }
    }

    public boolean hasValidMulti() {
        IBlockState state = this.world.getBlockState(pos);

        if (state.getBlock() instanceof BlockFreezer)
            return validMulti2x1();
        return false;
    }

    private boolean validMulti2x1() {
        IBlockState state = this.world.getBlockState(pos);
        IBlockState stateUp = this.world.getBlockState(pos.up());

        if (!(state.getBlock() instanceof BlockFreezer))
            return false;

        if (!(stateUp.getBlock() instanceof BlockFreezer))
            return false;

        if (state.getProperties().add(BlockFreezer.FACING) != stateUp.getProperties().add(BlockFreezer.FACING))
            return false;

        return state.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.BOTTOM && stateUp.get(BlockFreezer.PART) == BlockFreezer.EnumPartType.TOP;
    }
}
