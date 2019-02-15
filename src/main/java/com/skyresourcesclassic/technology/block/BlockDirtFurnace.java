package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.ContainerDirtFurnace;
import com.skyresourcesclassic.technology.tile.DirtFurnaceTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockDirtFurnace extends BlockContainer {
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    public static final BooleanProperty BURNING = BooleanProperty.create("burning");

    public BlockDirtFurnace(String name, float hardness, float resistance) {
        super(Block.Builder.create(Material.GROUND).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
        this.setDefaultState(
                this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH).with(BURNING, false));
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Nullable
    public Item getItemDropped(IBlockState state, World worldIn, BlockPos pos, int fortune) {
        return Item.getItemFromBlock(ModBlocks.dirtFurnace);
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
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new DirtFurnaceInterface(pos), null);

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new DirtFurnaceTile();
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to
     * allow for adjustments to the IBlockstate
     */
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite())
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

    public class DirtFurnaceInterface implements IInteractionObject {
        private BlockPos pos;

        private DirtFurnaceInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerDirtFurnace(playerInventory, (DirtFurnaceTile) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:dirt_furnace_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ModBlocks.dirtFurnace.getTranslationKey() + ".name", new Object[0]);
        }

        @Override
        public boolean hasCustomName() {
            return false;
        }

        @Nullable
        @Override
        public ITextComponent getCustomName() {
            return null;
        }
    }
}
