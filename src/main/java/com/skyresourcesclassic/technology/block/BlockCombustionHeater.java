package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.TileCombustionHeater;
import com.skyresourcesclassic.technology.tile.TilePoweredCombustionHeater;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockCombustionHeater extends BlockContainer {
    public BlockCombustionHeater(String material, float hardness, float resistance, int tier) {
        super(Material.WOOD);
        this.setTranslationKey(References.ModID + "." + material + "_combustion_heater");
        this.setCreativeTab(ModCreativeTabs.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
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

    public int getTier() {
        return tier;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (tier > 2)
            return new TilePoweredCombustionHeater();
        return new TileCombustionHeater();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (tier < 3) {
            TileCombustionHeater te = (TileCombustionHeater) world.getTileEntity(pos);
            te.dropInventory();
        }

        super.breakBlock(world, pos, state);
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

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
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
        tooltip.add(TextFormatting.RED + "Max Heat: " + Integer.toString(heat));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (tier < 3) {
                player.openGui(SkyResourcesClassic.instance, ModGuiHandler.CombustionHeaterGUI, world, pos.getX(), pos.getY(),
                        pos.getZ());
            } else {
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
}
