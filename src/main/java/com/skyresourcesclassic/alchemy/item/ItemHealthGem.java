package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.effects.IHealthBoostItem;
import com.skyresourcesclassic.registry.ModCreativeTabs;
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
    private final int maxHealth = ConfigOptions.health.healthGemMaxHealth;

    public ItemHealthGem() {
        super();

        setTranslationKey(References.ModID + ".item_health_gem");
        setRegistryName("Item_health_gem");
        setHasSubtypes(true);
        setCreativeTab(ModCreativeTabs.tabAlchemy);
        setMaxStackSize(1);
    }

    @Override
    public int getHealthBoost(ItemStack stack) {
        return (int) (getCompound(stack).getInteger("health") * ConfigOptions.health.healthGemPercentage);
    }

    public int getHealthInjected(ItemStack stack) {
        return getCompound(stack).getInteger("health");
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTagCompound(new NBTTagCompound());
        itemStack.getTagCompound().setInteger("health", 0);
        itemStack.getTagCompound().setInteger("cooldown", 0);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        super.onItemRightClick(world, player, hand);

        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking()) {
            if (getCompound(stack) != null) {
                if (stack.getTagCompound().getInteger("health") + 2 <= maxHealth
                        && stack.getTagCompound().getInteger("cooldown") == 0) {
                    player.attackEntityFrom(DamageSource.GENERIC, 2);
                    stack.getTagCompound().setInteger("health",
                            stack.getTagCompound().getInteger("health") + 2);
                    stack.getTagCompound().setInteger("cooldown", 20);
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
            if (stack.getTagCompound() != null) {
                tooltip.add(TextFormatting.RED + "Health Injected: "
                        + stack.getTagCompound().getInteger("health"));
            } else
                tooltip.add("Health Injected: " + 0);

            tooltip.add(TextFormatting.DARK_RED + "Health Gained: "
                    + getHealthBoost(stack));
        } else
            tooltip.add(TextFormatting.GREEN + "Hold LSHIFT for info.");
    }

    private NBTTagCompound getCompound(ItemStack stack) {
        NBTTagCompound com = stack.getTagCompound();
        if (com == null)
            onCreated(stack, null, null);
        com = stack.getTagCompound();

        return com;
    }
}
