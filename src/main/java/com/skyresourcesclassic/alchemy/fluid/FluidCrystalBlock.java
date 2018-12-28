package com.skyresourcesclassic.alchemy.fluid;

import com.skyresourcesclassic.References;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class FluidCrystalBlock extends BlockFluidClassic {

    public FluidCrystalBlock(Fluid fluid, Material material, String unlocalizedName,
                             String registryName) {
        super(fluid, material);
        this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
        this.setRegistryName(registryName);
        this.setTickRandomly(true);
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
                if (!isSourceBlock(world, pos2))
                    return false;
            }
        }
        return !(world.getBlockState(pos.add(0, 1, 0)).getBlock() == this
                || world.getBlockState(pos.add(0, -1, 0)).getBlock() == this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn,
                                                 BlockPos pos) {
        return NULL_AABB;
    }
}
