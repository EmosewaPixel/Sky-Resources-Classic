package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.AqueousMachineInterface;
import com.skyresourcesclassic.technology.tile.TileAqueousConcentrator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockAqueousDeconcentrator extends BlockContainer {
    private static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public BlockAqueousDeconcentrator(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.GROUND).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Nullable
    public Item getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return Item.getItemFromBlock(ModBlocks.aqueousDeconcentrator);
    }

    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing) state.get(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullCube() && !iblockstate1.isFullCube()) {
                enumfacing = EnumFacing.SOUTH;
            } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullCube() && !iblockstate.isFullCube()) {
                enumfacing = EnumFacing.NORTH;
            } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullCube() && !iblockstate3.isFullCube()) {
                enumfacing = EnumFacing.EAST;
            } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullCube() && !iblockstate2.isFullCube()) {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.with(FACING, enumfacing), 2);
        }
    }

    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    /**
     * Returns the blockstate with the given rotation from the passed
     * blockstate. If inapplicable, returns the passed blockstate.
     */
    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate((EnumFacing) state.get(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If
     * inapplicable, returns the passed blockstate.
     */
    public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation((EnumFacing) state.get(FACING)));
    }

    protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(new IProperty[]{FACING});
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileAqueousConcentrator();
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {

            TileAqueousConcentrator tile = (TileAqueousConcentrator) world.getTileEntity(pos);
            if (!player.getHeldItem(hand).isEmpty() && FluidUtil.tryFillContainer(player.getHeldItem(hand),
                    tile.getTank(), tile.getTank().getCapacity(), player, false).success) {

                FluidActionResult result = FluidUtil.tryFillContainer(player.getHeldItem(hand), tile.getTank(),
                        tile.getTank().getCapacity(), player, true);

                if (result.success) {
                    if (player.getHeldItem(hand).getCount() > 1) {
                        player.getHeldItem(hand).shrink(1);
                        RandomHelper.spawnItemInWorld(world, result.getResult(), player.getPosition());
                    } else {
                        player.setHeldItem(hand, result.getResult());
                    }
                    return true;
                }

                ItemStack contents = player.getHeldItem(hand).copy();
                contents.setCount(1);
                return true;
            }

            NetworkHooks.openGui((EntityPlayerMP) player, new AqueousMachineInterface(pos));

        }
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        TileAqueousConcentrator te = (TileAqueousConcentrator) world.getTileEntity(pos);
        te.dropInventory();

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }
}
