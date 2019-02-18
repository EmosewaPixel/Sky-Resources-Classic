package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.ContainerWildlifeAttractor;
import com.skyresourcesclassic.technology.tile.TileWildlifeAttractor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Fluids;
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

public class BlockWildlifeAttractor extends BlockContainer {
    public BlockWildlifeAttractor(String name, float hardness, float resistance) {
        super(Block.Properties.create(Material.GRASS).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(name);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TileWildlifeAttractor();
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!player.getHeldItem(hand).isEmpty() && FluidUtil.getFluidContained(player.getHeldItem(hand)) != null
                    && FluidUtil.getFluidContained(player.getHeldItem(hand)).getFluid() == Fluids.WATER) {
                TileWildlifeAttractor tile = (TileWildlifeAttractor) world.getTileEntity(pos);
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

            NetworkHooks.openGui((EntityPlayerMP) player, new WildlifeAttractorInterface(pos));
        }
        return true;
    }

    public class WildlifeAttractorInterface implements IInteractionObject {
        private BlockPos pos;

        private WildlifeAttractorInterface(BlockPos pos) {
            this.pos = pos;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerWildlifeAttractor(playerInventory, (TileWildlifeAttractor) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:wildlife_attractor_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ModBlocks.wildlifeAttractor.getTranslationKey() + ".name", new Object[0]);
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
