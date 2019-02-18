package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.gui.container.ContainerCombustionHeater;
import com.skyresourcesclassic.technology.tile.TileCombustionHeater;
import com.skyresourcesclassic.technology.tile.TilePoweredCombustionHeater;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockCombustionHeater extends BlockContainer {
    public BlockCombustionHeater(String material, float hardness, float resistance, int tier) {
        super(Block.Properties.create(Material.WOOD).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(material + "_combustion_heater");
        this.tier = tier;
    }

    private int tier;

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Material getMaterial(IBlockState state) {
        switch (tier) {
            case 1:
                return Material.WOOD;
            default:
                return Material.IRON;
        }
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        if (tier > 2)
            return new TilePoweredCombustionHeater(tier);
        return new TileCombustionHeater(tier);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        if (tier < 3) {
            TileCombustionHeater te = (TileCombustionHeater) world.getTileEntity(pos);
            te.dropInventory();
        }

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    public int getMaximumHeat() {
        switch (tier) {
            case 1:
                return 100;
            case 2:
                return 1538;
            case 3:
                return 2750;
            default:
                return 6040;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        int heat;
        switch (tier) {
            case 1:
                heat = 100;
                break;
            case 2:
                heat = 1538;
                break;
            case 3:
                heat = 2750;
                break;
            default:
                heat = 6040;
        }
        tooltip.add(new TextComponentString(TextFormatting.RED + "Max Heat: " + Integer.toString(heat)));
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (tier < 3)
                NetworkHooks.openGui((EntityPlayerMP) player, new CombustionHeaterInterface(pos, this.tier));
            else {
                if (player.getHeldItemMainhand().isEmpty() && !player.isSneaking()) {
                    List<ITextComponent> toSend = new ArrayList();

                    TilePoweredCombustionHeater tile = (TilePoweredCombustionHeater) world.getTileEntity(pos);
                    toSend.add(new TextComponentString(TextFormatting.RED + "FE Stored: " + tile.getEnergyStored()
                            + " / " + tile.getMaxEnergyStored()));
                    toSend.add(new TextComponentString(TextFormatting.GOLD + "Current Heat: " + tile.currentHeatValue
                            + " / " + tile.getMaxHeat()));
                    if (tile.hasValidMultiblock())
                        toSend.add(new TextComponentString(TextFormatting.GREEN + "Valid Multiblock!"));
                    else
                        toSend.add(new TextComponentString(TextFormatting.RED + "Invalid Multiblock."));

                    for (ITextComponent text : toSend) {
                        player.sendMessage(text);
                    }

                }
            }
        }
        return true;
    }

    public class CombustionHeaterInterface implements IInteractionObject {
        private BlockPos pos;
        private int tier;

        private CombustionHeaterInterface(BlockPos pos, int tier) {
            this.pos = pos;
            this.tier = tier;
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
            return new ContainerCombustionHeater(playerInventory, (TileCombustionHeater) playerIn.world.getTileEntity(pos));
        }

        @Override
        public String getGuiID() {
            return "skyresourcesclassic:combustion_heater_gui";
        }

        @Override
        public ITextComponent getName() {
            return new TextComponentTranslation(ModBlocks.combustionHeater[tier].getTranslationKey() + ".name", new Object[0]);
        }

        @Override
        public boolean hasCustomName() {
            return false;
        }

        @javax.annotation.Nullable
        @Override
        public ITextComponent getCustomName() {
            return null;
        }
    }
}
