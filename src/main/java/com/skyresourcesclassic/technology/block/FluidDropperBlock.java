package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.technology.tile.FluidDropperTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.IBlockReader;

public class FluidDropperBlock extends BlockContainer {
    public FluidDropperBlock(String name, float hardness, float resistance) {
        super(Block.Builder.create(Material.ROCK).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new FluidDropperTile();

    }
}
