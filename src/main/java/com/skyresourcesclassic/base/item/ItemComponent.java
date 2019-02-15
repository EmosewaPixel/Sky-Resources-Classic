package com.skyresourcesclassic.base.item;

import net.minecraft.block.IGrowable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemComponent extends Item {
    public ItemComponent(String name, ItemGroup tab) {
        super(new Item.Properties().group(tab));
    }

    public EnumActionResult onItemUse(ItemUseContext context) {
        if (!context.getPlayer().canPlayerEdit(context.getPos().offset(context.getFace()), context.getFace(), context.getItem())) {
            return EnumActionResult.FAIL;
        } else {
            if (context.getItem().getTranslationKey().equals("item.skyresourcesclassic.plant_matter")
                    || context.getItem().getTranslationKey().equals("item.skyresourcesclassic.enriched_bonemeal")) {
                if (applyBonemeal(context.getItem(), context.getWorld(), context.getPos())) {
                    if (!context.getWorld().isRemote) {
                        context.getWorld().playEvent(2005, context.getPos(), 0);
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
            tooltip.add(new TextComponentString(TextFormatting.DARK_GRAY + "Acts as bonemeal"));
            tooltip.add(new TextComponentString(TextFormatting.DARK_GRAY + "Grows instantly"));
        } else if (stack.getTranslationKey().equals("item.skyresourcesclassic.enriched_bonemeal")) {
            tooltip.add(new TextComponentString(TextFormatting.DARK_GRAY + "Grows instantly"));
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
