package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.technology.tile.TileCrucibleInserter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockCrucibleInserter extends BlockContainer {
    public BlockCrucibleInserter(String name, float hardness,
                                 float resistance) {
        super(Block.Builder.create(Material.ROCK).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileCrucibleInserter();
    }
}
