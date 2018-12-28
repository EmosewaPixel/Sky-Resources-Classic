package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModItemGroups;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.TileDarkMatterWarper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockDarkMatterWarper extends BlockContainer {

    public BlockDarkMatterWarper(String name, float hardness, float resistance) {
        super(Material.IRON);
        this.setUnlocalizedName(References.ModID + "." + name);
        this.setCreativeTab(ModItemGroups.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDarkMatterWarper();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes,
                new AxisAlignedBB(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(SkyResourcesClassic.instance, ModGuiHandler.DarkMatterWarperGUI, world, pos.getX(), pos.getY(),
                    pos.getZ());
        }
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        TileDarkMatterWarper te = (TileDarkMatterWarper) world.getTileEntity(pos);
        te.dropInventory();

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }
}
