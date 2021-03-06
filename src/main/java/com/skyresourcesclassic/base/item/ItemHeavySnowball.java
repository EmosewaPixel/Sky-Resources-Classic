package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.base.entity.EntityHeavySnowball;
import com.skyresourcesclassic.registry.ModCreativeTabs;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.base.entity.EntityHeavySnowball;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemHeavySnowball extends Item
{
	public ItemHeavySnowball(String name)
	{
		this.maxStackSize = 8;
		this.setTranslationKey(References.ModID + "." + name);
		setRegistryName(name);
		this.setCreativeTab(ModCreativeTabs.tabMain);
	}

	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if (!player.capabilities.isCreativeMode)
		{
			player.getHeldItem(hand).shrink(1);
		}

		world.playSound(null, player.posX, player.posY,
				player.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
				SoundCategory.NEUTRAL, 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote)
		{
			EntityHeavySnowball entitysnowball = new EntityHeavySnowball(
					world, player);
			entitysnowball.shoot(player, player.rotationPitch,
					player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(entitysnowball);
		}

		return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

}
