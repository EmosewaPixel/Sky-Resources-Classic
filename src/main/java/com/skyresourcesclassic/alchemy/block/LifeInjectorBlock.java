package com.skyresourcesclassic.alchemy.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.alchemy.tile.LifeInjectorTile;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import com.skyresourcesclassic.registry.ModGuiHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LifeInjectorBlock extends BlockContainer {
    public LifeInjectorBlock(String name, float hardness, float resistance) {
        super(Material.WOOD);
        setTranslationKey(References.ModID + "." + name);
        setCreativeTab(ModCreativeTabs.tabAlchemy);
        setHardness(hardness);
        setResistance(resistance);
        setRegistryName(name);
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks
     * for render
     */
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new LifeInjectorTile();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        LifeInjectorTile te = (LifeInjectorTile) world.getTileEntity(pos);
        te.dropInventory();

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(SkyResourcesClassic.instance, ModGuiHandler.LifeInjectorGUI, world, pos.getX(), pos.getY(),
                    pos.getZ());
        }
        return true;
    }
}
