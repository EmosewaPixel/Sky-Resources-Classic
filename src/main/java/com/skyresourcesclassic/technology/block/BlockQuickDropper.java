package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.TileQuickDropper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuickDropper extends BlockContainer {

    public BlockQuickDropper(String name, float hardness,
                             float resistance) {
        super(Material.IRON);
        setTranslationKey(References.ModID + "." + name);
        setCreativeTab(ModCreativeTabs.tabTech);
        setHardness(hardness);
        setResistance(resistance);
        setRegistryName(name);
        hasTileEntity = true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileQuickDropper();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileQuickDropper te = (TileQuickDropper) world.getTileEntity(pos);
        te.dropInventory();

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(SkyResourcesClassic.instance, ModGuiHandler.QuickDropperGUI, world, pos.getX(), pos.getY(),
                    pos.getZ());

        }
        return true;
    }
}
