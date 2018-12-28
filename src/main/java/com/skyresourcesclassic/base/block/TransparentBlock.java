package com.skyresourcesclassic.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TransparentBlock extends BaseBlock {
    private static VoxelShape BoundingBox = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public TransparentBlock(Material material, String name, float hardness,
                            float resistance, VoxelShape shape, SoundType stepSound) {
        super(material, name, hardness, resistance, stepSound);
        BoundingBox = shape;
    }

    @Override
    public VoxelShape getCollisionShape(IBlockState p_196268_1_, IBlockReader p_196268_2_, BlockPos p_196268_3_) {
        return BoundingBox;
    }

    public boolean isSolid(IBlockState p_200124_1_) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
