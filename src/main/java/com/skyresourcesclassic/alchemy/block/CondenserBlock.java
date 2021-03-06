package com.skyresourcesclassic.alchemy.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.tile.CondenserTile;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class CondenserBlock extends BlockContainer {
    public CondenserBlock(String material, float hardness, float resistance, int tier) {
        super(Material.ROCK);
        setTranslationKey(References.ModID + "." + material + "_condenser");
        setCreativeTab(ModCreativeTabs.tabAlchemy);
        setHardness(hardness);
        setResistance(resistance);
        setRegistryName(material + "_condenser");
        hasTileEntity = true;
        this.tier = tier;
    }

    private int tier;

    public int getTier() {
        return tier;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CondenserTile();
    }
}