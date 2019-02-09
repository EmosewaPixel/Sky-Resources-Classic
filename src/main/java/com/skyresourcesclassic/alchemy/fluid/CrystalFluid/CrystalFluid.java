package com.skyresourcesclassic.alchemy.fluid.CrystalFluid;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public abstract class CrystalFluid extends FlowingFluid {
    private int type;

    public CrystalFluid(int type) {
        this.type = type;
    }

    public Fluid getFlowingFluid() {
        return ModFluids.flowingCrystalFluids.get(type);
    }

    public Fluid getStillFluid() {
        return ModFluids.crystalFluids.get(type);
    }

    protected boolean canSourcesMultiply() {
        return false;
    }

    @Override
    protected void beforeReplacingBlock(IWorld iWorld, BlockPos blockPos, IBlockState iBlockState) {

    }

    @Override
    protected int getSlopeFindDistance(IWorldReaderBase iWorldReaderBase) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReaderBase iWorldReaderBase) {
        return 1;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public Item getFilledBucket() {
        return null;
    }

    @Override
    protected boolean canOtherFlowInto(IFluidState state, Fluid fluidIn, EnumFacing direction) {
        return false;
    }

    @Override
    public int getTickRate(IWorldReaderBase p_205569_1_) {
        return 8;
    }

    @Override
    protected float getExplosionResistance() {
        return 100;
    }

    @Override
    protected IBlockState getBlockState(IFluidState state) {
        return ModBlocks.crystalFluidBlocks.get(type).getDefaultState().with(BlockFlowingFluid.LEVEL, getLevelFromState(state));
    }
}
