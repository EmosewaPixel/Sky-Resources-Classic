package com.skyresourcesclassic.alchemy.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.tile.CondenserTile;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class CondenserBlock extends BlockContainer {
    public CondenserBlock(String material, float hardness, float resistance, int tier) {
        super(Material.ROCK);
        this.setUnlocalizedName(References.ModID + "." + material + "_condenser");
        this.setCreativeTab(ModItemGroups.tabAlchemy);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(material + "_condenser");
        this.tier = tier;
    }

    private int tier;

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new CondenserTile(tier);
    }
}
