package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.ContainerCombustionCollector;
import com.skyresourcesclassic.technology.tile.TileCombustionCollector;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockCombustionCollector extends BlockContainer {
    public BlockCombustionCollector(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.IRON).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileCombustionCollector();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        TileCombustionCollector te = (TileCombustionCollector) world.getTileEntity(pos);
        te.dropInventory();

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote)
            NetworkHooks.openGui((EntityPlayerMP) player, new CombustionCollectorInterface(pos));

        return true;
    }

    public class CombustionCollectorInterface implements IInteractionObject {
        private BlockPos pos;

        public CombustionCollectorInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerCombustionCollector(playerInventory, (TileCombustionCollector) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:combustion_collector_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ModBlocks.combustionCollector.getTranslationKey() + ".name", new Object[0]);
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
