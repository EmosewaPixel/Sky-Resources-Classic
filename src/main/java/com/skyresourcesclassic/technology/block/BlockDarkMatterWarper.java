package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.ContainerDarkMatterWarper;
import com.skyresourcesclassic.technology.tile.TileDarkMatterWarper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockDarkMatterWarper extends BlockContainer {
    private static final VoxelShape shape = Block.makeCuboidShape(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D);

    public BlockDarkMatterWarper(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileDarkMatterWarper();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
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
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new DarkMatterWarperInterface(pos));

        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        TileDarkMatterWarper te = (TileDarkMatterWarper) world.getTileEntity(pos);
        te.dropInventory();

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    public class DarkMatterWarperInterface implements IInteractionObject {
        private BlockPos pos;

        private DarkMatterWarperInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerDarkMatterWarper(playerInventory, (TileDarkMatterWarper) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:dark_matter_warper_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ModBlocks.darkMatterWarper.getTranslationKey() + ".name", new Object[0]);
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
