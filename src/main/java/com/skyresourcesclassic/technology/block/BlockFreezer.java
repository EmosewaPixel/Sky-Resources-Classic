package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModItemGroups;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.FreezerTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockFreezer extends BlockContainer {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumPartType> PART = PropertyEnum.<EnumPartType>create("part",
            EnumPartType.class);

    public BlockFreezer(String name, float hardness, float resistance) {
        super(Material.IRON);
        this.setUnlocalizedName(References.ModID + "." + name);
        this.setCreativeTab(ModItemGroups.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(name);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new FreezerTile();
    }

    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public int damageDropped(IBlockState blockstate) {
        return 0;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        FreezerTile te = (FreezerTile) world.getTileEntity(pos);
        te.dropInventory();
        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            BlockPos bottomPos = state.getProperties().get(PART) == EnumPartType.BOTTOM ? pos : pos.down();
            player.openGui(SkyResourcesClassic.instance, ModGuiHandler.FreezerGUI, world, bottomPos.getX(), bottomPos.getY(),
                    bottomPos.getZ());
        }
        return true;
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
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

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, EntityLivingBase placer) {
        super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return this.getDefaultState().with(FACING, placer.getHorizontalFacing().getOpposite())
                .with(PART, EnumPartType.BOTTOM);
    }

    public int getMetaFromState(IBlockState state) {

        int i = 0;
        i = i | ((EnumFacing) state.get(FACING)).getHorizontalIndex();

        if (state.get(PART) == EnumPartType.TOP) {
            i |= 8;
        }

        return i;
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

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, PART});
    }

    public enum EnumPartType implements IStringSerializable {
        TOP("top"), BOTTOM("bottom");

        private final String name;

        EnumPartType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}
