package com.skyresourcesclassic.alchemy.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class FluidCrystalBlock extends BlockFlowingFluid {

    public FluidCrystalBlock(FlowingFluid fluid, Material material,
                             String registryName) {
        super(fluid, Block.Builder.create(material));
        this.setRegistryName(registryName);
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean isNotFlowing(World world, BlockPos pos, IBlockState state) {
        BlockPos[] checkPos = new BlockPos[]
                {pos.add(1, 0, 0), pos.add(-1, 0, 0), pos.add(0, 0, 1), pos.add(0, 0, -1)};
        for (BlockPos pos2 : checkPos) {
            if (world.getBlockState(pos2).getBlock() == this) {
                if (!getFluidState(world.getBlockState(pos2)).isSource())
                    return false;
            }
        }
        return !(world.getBlockState(pos.add(0, 1, 0)).getBlock() == this
                || world.getBlockState(pos.add(0, -1, 0)).getBlock() == this);
    }
}
