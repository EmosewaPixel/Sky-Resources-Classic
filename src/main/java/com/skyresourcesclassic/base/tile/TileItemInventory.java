package com.skyresourcesclassic.base.tile;

import com.skyresourcesclassic.base.gui.ItemHandlerSpecial;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileItemInventory extends TileBase {
    private ItemHandlerSpecial inventory;

    public TileItemInventory(String name, TileEntityType type, int slots) {
        super(name, type);
        inventory = new ItemHandlerSpecial(slots) {
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                TileItemInventory.this.markDirty();
            }
        };
    }

    public TileItemInventory(String name, TileEntityType type, int slots, Integer[] noInsert, Integer[] noExtract) {
        super(name, type);
        inventory = new ItemHandlerSpecial(slots, noInsert, noExtract) {
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                TileItemInventory.this.markDirty();
            }
        };
    }

    public ItemHandlerSpecial getInventory() {
        return inventory;
    }

    public void setInventory(ItemHandlerSpecial handler) {
        inventory = handler;
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setTag("inv", inventory.serializeNBT());
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        inventory.deserializeNBT((NBTTagCompound) compound.getTag("inv"));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> this.inventory).cast();
        }
        return super.getCapability(capability, facing);
    }

    public void dropInventory() {
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);

            if (!itemstack.isEmpty()) {
                InventoryHelper.spawnItemStack(getWorld(), pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }
}
