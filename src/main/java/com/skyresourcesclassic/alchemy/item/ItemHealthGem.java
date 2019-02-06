package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.effects.IHealthBoostItem;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemHealthGem extends Item implements IHealthBoostItem {
    private final int maxHealth = ConfigOptions.health.healthGemMaxHealth.get();

    public ItemHealthGem() {
        super(new Item.Builder().group(ModItemGroups.tabAlchemy).maxStackSize(1));

        setRegistryName("Item_health_gem");
    }

    @Override
    public int getHealthBoost(ItemStack stack) {
        return (int) (getCompound(stack).getInt("health") * ConfigOptions.health.healthGemPercentage.get());
    }

    public int getHealthInjected(ItemStack stack) {
        return getCompound(stack).getInt("health");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.deserializeNBT(new NBTTagCompound());
        itemStack.getTag().setInt("health", 0);
        itemStack.getTag().setInt("cooldown", 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        super.onItemRightClick(world, player, hand);

        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (getCompound(stack) != null) {
                if (stack.getTag().getInt("health") + 2 <= maxHealth
                        && stack.getTag().getInt("cooldown") == 0) {
                    player.attackEntityFrom(DamageSource.GENERIC, 2);
                    stack.getTag().setInt("health",
                            stack.getTag().getInt("health") + 2);
                    stack.getTag().setInt("cooldown", 20);
                }
            } else {
                onCreated(stack, world, player);
            }
        }

        return new ActionResult(EnumActionResult.PASS, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack,
                                               ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(TextFormatting.GREEN + "Shift-right click to inject health.");
            if (stack.getTag() != null) {
                tooltip.add(TextFormatting.RED + "Health Injected: "
                        + stack.getTag().getInt("health"));
            } else
                tooltip.add("Health Injected: " + 0);

            tooltip.add(TextFormatting.DARK_RED + "Health Gained: "
                    + getHealthBoost(stack));
        } else
            tooltip.add(TextFormatting.GREEN + "Hold LSHIFT for info.");
    }

    private NBTTagCompound getCompound(ItemStack stack) {
        NBTTagCompound com = stack.getTag();
        if (com == null)
            onCreated(stack, null, null);
        com = stack.getTag();

        return com;
    }
}
