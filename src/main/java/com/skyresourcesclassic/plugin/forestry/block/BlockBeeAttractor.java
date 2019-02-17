package com.skyresourcesclassic.plugin.forestry.block;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.plugin.forestry.ForestryPlugin;
import com.skyresourcesclassic.plugin.forestry.gui.container.ContainerBeeAttractor;
import com.skyresourcesclassic.plugin.forestry.tile.TileBeeAttractor;
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
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BlockBeeAttractor extends BlockContainer {

    public BlockBeeAttractor(String unlocalizedName, String registryName, float hardness, float resistance) {
        super(Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(registryName);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileBeeAttractor();
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        TileBeeAttractor te = (TileBeeAttractor) world.getTileEntity(pos);
        te.dropInventory();
        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!player.getHeldItem(hand).isEmpty() && FluidUtil.getFluidContained(player.getHeldItem(hand)) != null
                    && FluidUtil.getFluidContained(player.getHeldItem(hand)).getFluid().getName().equals("seed.oil")) {
                TileBeeAttractor tile = (TileBeeAttractor) world.getTileEntity(pos);
                FluidActionResult result = FluidUtil
                        .tryEmptyContainer(player.getHeldItem(hand), tile.getTank(), 1000, player, true);

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

            NetworkHooks.openGui((EntityPlayerMP) player, new BeeAttractorInterface(pos), null);
        }
        return true;
    }


    public class BeeAttractorInterface implements IInteractionObject {
        private BlockPos pos;

        private BeeAttractorInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerBeeAttractor() playerInventory, (TileBeeAttractor) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:bee_attractor_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ForestryPlugin.beeAttractor.getTranslationKey() + ".name", new Object[0]);
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
