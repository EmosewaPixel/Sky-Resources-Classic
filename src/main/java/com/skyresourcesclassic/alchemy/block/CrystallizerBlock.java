package com.skyresourcesclassic.alchemy.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.tile.CrystallizerTile;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class CrystallizerBlock extends BlockContainer {
    public CrystallizerBlock(String material, float hardness, float resistance, int tier) {
        super(Material.ROCK);
        this.setTranslationKey(References.ModID + "." + material + "_crystallizer");
        this.setCreativeTab(ModCreativeTabs.tabAlchemy);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(material + "_crystallizer");
        this.tier = tier;
    }

    private int tier;


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CrystallizerTile(tier);
    }
}
