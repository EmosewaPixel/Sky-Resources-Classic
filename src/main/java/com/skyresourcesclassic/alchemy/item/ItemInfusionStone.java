package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.ItemHelper;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemInfusionStone extends Item {
    public ItemInfusionStone(int durability, String name) {
        super(new Item.Builder().defaultMaxDamage(durability).maxStackSize(1).group(ModItemGroups.tabAlchemy));
        setRegistryName(name);

        ItemHelper.addInfusionStone(this);
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        super.onItemUse(context);
        ItemStack stack = context.getPlayer().getHeldItem(EnumHand.MAIN_HAND);
        Block block = context.getWorld().getBlockState(context.getPos()).getBlock();

        ItemStack offHand = context.getPlayer().getHeldItemOffhand();

        ProcessRecipe recipe = ProcessRecipeManager.infusionRecipes.getRecipe(
                new ArrayList<>(Arrays.asList(offHand,
                        new ItemStack(block))),
                context.getPlayer().getHealth(), false, false);

        if (recipe != null && recipe.getOutputs().get(0) != ItemStack.EMPTY) {
            if (context.getPlayer().getMaxHealth() < recipe.getIntParameter()) {
                if (context.getWorld().isRemote)
                    context.getPlayer().sendMessage(new TextComponentString(
                            "You are not strong enough to infuse. Your max health is too low."));
            }
            if (context.getPlayer().getHealth() >= recipe.getIntParameter()) {
                if (!context.getWorld().isRemote) {
                    context.getPlayer().attackEntityFrom(DamageSource.MAGIC, recipe.getIntParameter());
                    context.getWorld().removeBlock(context.getPos());
                    context.getPlayer().dropItem(recipe.getOutputs().get(0).copy(), false);
                    if (offHand != ItemStack.EMPTY)
                        offHand.shrink(recipe.getInputs().get(0) instanceof ItemStack
                                ? ((ItemStack) recipe.getInputs().get(0)).getCount() : 1);

                    stack.damageItem(1, context.getPlayer());
                }
            } else {
                if (context.getWorld().isRemote)
                    context.getPlayer().sendMessage(new TextComponentString("Not enough health to infuse."));
            }
        }

        if (recipe == null) {
            if (applyBonemeal(stack, context.getWorld(), context.getPos(), context.getPlayer())) {
                if (!context.getWorld().isRemote) {
                    context.getWorld().playEvent(2005, context.getPos(), 0);
                }

                return EnumActionResult.SUCCESS;
            }
        }

        if (context.getWorld().isRemote)
            context.getPlayer().swingArm(EnumHand.MAIN_HAND);

        return EnumActionResult.PASS;
    }

    private static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack);
        if (hook != 0) return hook > 0;

        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();

            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }

                    stack.damageItem(1, player);
                    player.attackEntityFrom(DamageSource.MAGIC, 4);
                }

                return true;
            }
        }

        return false;
    }
}
