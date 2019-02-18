package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.technology.gui.container.FreezerInterface;
import com.skyresourcesclassic.technology.tile.MiniFreezerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockMiniFreezer extends BlockContainer {
    private static final VoxelShape shape = Block.makeCuboidShape(0.125D, 0D, 0.125D, 0.875D, 1.0D, 0.875D);
    private static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

    public BlockMiniFreezer(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.GROUND).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public VoxelShape getShape(IBlockState p_196244_1_, IBlockReader p_196244_2_, BlockPos p_196244_3_) {
        return shape;
    }

    @Override
    public VoxelShape getRaytraceShape(IBlockState p_196244_1_, IBlockReader p_196244_2_, BlockPos p_196244_3_) {
        return shape;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new MiniFreezerTile();
    }


    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        MiniFreezerTile te = (MiniFreezerTile) world.getTileEntity(pos);
        te.dropInventory();
        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new FreezerInterface(pos));

        return true;
    }

    public void onBlockAdded(IBlockState state, World worldIn, BlockPos pos, IBlockState oldState) {
        super.onBlockAdded(state, worldIn, pos, oldState);
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
        super.getStateForPlacement(context);
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
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
}
