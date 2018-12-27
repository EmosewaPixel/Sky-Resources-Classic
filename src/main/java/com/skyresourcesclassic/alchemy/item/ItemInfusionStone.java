package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.ItemHelper;
import com.skyresourcesclassic.References;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemInfusionStone extends Item {
    public ItemInfusionStone(int durability, String name) {
        this.setMaxDamage(durability);
        this.setUnlocalizedName(References.ModID + "." + name);
        setRegistryName(name);
        this.setMaxStackSize(1);
        this.setNoRepair();
        this.setCreativeTab(ModItemGroups.tabAlchemy);

        ItemHelper.addInfusionStone(this);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
                                      float hitX, float hitY, float hitZ) {
        super.onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        Block block = world.getBlockState(pos).getBlock();

        ItemStack offHand = player.getHeldItemOffhand();

        ProcessRecipe recipe = ProcessRecipeManager.infusionRecipes.getRecipe(
                new ArrayList<>(Arrays.asList(offHand,
                        new ItemStack(block))),
                player.getHealth(), false, false);

        if (recipe != null && recipe.getOutputs().get(0) != ItemStack.EMPTY) {
            if (player.getMaxHealth() < recipe.getIntParameter()) {
                if (world.isRemote)
                    player.sendMessage(new TextComponentString(
                            "You are not strong enough to infuse. Your max health is too low."));
            }
            if (player.getHealth() >= recipe.getIntParameter()) {
                if (!world.isRemote) {
                    player.attackEntityFrom(DamageSource.MAGIC, recipe.getIntParameter());
                    world.removeBlock(pos);
                    player.dropItem(recipe.getOutputs().get(0).copy(), false);
                    if (offHand != ItemStack.EMPTY)
                        offHand.shrink(recipe.getInputs().get(0) instanceof ItemStack
                                ? ((ItemStack) recipe.getInputs().get(0)).getCount() : 1);

                    stack.damageItem(1, player);
                }
            } else {
                if (world.isRemote)
                    player.sendMessage(new TextComponentString("Not enough health to infuse."));
            }
        }

        if (recipe == null) {
            if (applyBonemeal(stack, world, pos, player)) {
                if (!world.isRemote) {
                    world.playEvent(2005, pos, 0);
                }

                return EnumActionResult.SUCCESS;
            }
        }

        if (world.isRemote)
            player.swingArm(EnumHand.MAIN_HAND);

        return EnumActionResult.PASS;
    }

    private static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(target);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack, EnumHand.MAIN_HAND);
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
