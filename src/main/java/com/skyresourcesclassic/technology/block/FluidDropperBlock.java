package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import com.skyresourcesclassic.technology.tile.FluidDropperTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class FluidDropperBlock extends BlockContainer {

    public FluidDropperBlock(String name, float hardness, float resistance) {
        super(Material.ROCK);
        this.setTranslationKey(References.ModID + "." + name);
        this.setCreativeTab(ModCreativeTabs.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(name);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new FluidDropperTile();

    }
}
