package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.alchemy.item.ItemHealthGem;
import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.ConfigOptions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class LifeInjectorTile extends TileItemInventory implements ITickable {

    public LifeInjectorTile() {
        super("lifeInjector", 1);
    }

    private int cooldown;

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("cooldown", cooldown);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        cooldown = compound.getInt("cooldown");
    }

    public int getHealthInGem() {
        ItemStack gemStack = getInventory().getStackInSlot(0);
        if (gemStack != ItemStack.EMPTY && gemStack.getItem() instanceof ItemHealthGem) {
            ItemHealthGem healthGem = (ItemHealthGem) gemStack.getItem();
            return healthGem.getHealthInjected(gemStack);
        }
        return 0;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (cooldown <= 0) {
                List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(
                        pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

                for (EntityLivingBase entity : list) {
                    float dmg = Math.min(entity.getHealth(), 2);

                    ItemStack stack = getInventory().getStackInSlot(0);
                    if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemHealthGem) {
                        if (stack.getTag().getInt("health") + dmg <= ConfigOptions.health.healthGemMaxHealth.get()) {
                            entity.attackEntityFrom(DamageSource.MAGIC, dmg);
                            stack.getTag().setInt("health",
                                    stack.getTag().getInt("health") + 2);
                            stack.getTag().setInt("cooldown", 20);
                        }
                    }
                }
                cooldown = 60;
            } else
                cooldown--;
        }
    }
}
