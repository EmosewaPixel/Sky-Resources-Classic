package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModItemGroups;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.DirtFurnaceTile;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockDirtFurnace extends BlockContainer {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool BURNING = PropertyBool.create("burning");

    public BlockDirtFurnace(String name, float hardness, float resistance) {
        super(Material.GROUND);
        this.setUnlocalizedName(References.ModID + "." + name);
        this.setCreativeTab(ModItemGroups.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(name);
        this.setDefaultState(
                this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(BURNING, false));
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ModBlocks.dirtFurnace);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
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

    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        DirtFurnaceTile tile = (DirtFurnaceTile) worldIn.getTileEntity(pos);
        if (tile.isBurning()) {
            EnumFacing enumfacing = (EnumFacing) stateIn.get(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D,
                        SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing) {
                case WEST:
                    worldIn.spawnParticle(Particles.SMOKE, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(Particles.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(Particles.SMOKE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(Particles.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(Particles.SMOKE, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(Particles.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(Particles.SMOKE, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(Particles.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(SkyResourcesClassic.instance, ModGuiHandler.FurnaceGUI, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new DirtFurnaceTile();
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to
     * allow for adjustments to the IBlockstate
     */
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ,
                                            int meta, EntityLivingBase placer) {
        return this.getDefaultState().with(FACING, placer.getHorizontalFacing().getOpposite())
                .with(BURNING, false);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow
     * post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        DirtFurnaceTile te = (DirtFurnaceTile) world.getTileEntity(pos);
        InventoryHelper.dropInventoryItems(world, pos, te);

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(ModBlocks.dirtFurnace);
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for
     * TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing) state.get(FACING)).getHorizontalIndex();

        if (state.get(BURNING) == true) {
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
        return new BlockStateContainer(this, new IProperty[]{FACING, BURNING});
    }
}
