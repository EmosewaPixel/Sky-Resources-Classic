package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.References;
import net.minecraft.block.IGrowable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.ItemGroup;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemComponent extends Item {
    public ItemComponent(String name, ItemGroug tab) {
        super();

        this.setUnlocalizedName(References.ModID + "." + name);
        setRegistryName(name);
        this.setCreativeTab(tab);
    }

    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, playerIn.getHeldItem(hand))) {
            return EnumActionResult.FAIL;
        } else {
            if (playerIn.getHeldItem(hand).getTranslationKey().equals("item.skyresourcesclassic.plant_matter")
                    || playerIn.getHeldItem(hand).getTranslationKey().equals("item.skyresourcesclassic.enriched_bonemeal")) {
                if (applyBonemeal(playerIn.getHeldItem(hand), worldIn, pos)) {
                    if (!worldIn.isRemote) {
                        worldIn.playEvent(2005, pos, 0);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }

            return EnumActionResult.PASS;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getTranslationKey().equals("item.skyresourcesclassic.plant_matter")) {
            tooltip.add(TextFormatting.DARK_GRAY + "Acts as bonemeal");
            tooltip.add(TextFormatting.DARK_GRAY + "Grows instantly");
        } else if (stack.getTranslationKey().equals("item.skyresourcesclassic.enriched_bonemeal")) {
            tooltip.add(TextFormatting.DARK_GRAY + "Grows instantly");
        }
    }

    private static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target) {
        if (worldIn.getBlockState(target).getBlock() instanceof IGrowable && !worldIn.isRemote) {
            int tries = 100;
            while (worldIn.getBlockState(target).getBlock() instanceof IGrowable && tries > 0) {
                tries--;
                IGrowable igrowable = (IGrowable) worldIn.getBlockState(target).getBlock();
                if (igrowable.canGrow(worldIn, target, worldIn.getBlockState(target), false)) {
                    igrowable.grow(worldIn, worldIn.rand, target, worldIn.getBlockState(target));
                }
            }

            stack.shrink(1);

            return true;
        }

        return false;
    }
}
