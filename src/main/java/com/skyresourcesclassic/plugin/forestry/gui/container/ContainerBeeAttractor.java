package com.skyresourcesclassic.plugin.forestry.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.plugin.forestry.tile.TileBeeAttractor;
import net.minecraft.inventory.IInventory;

public class ContainerBeeAttractor extends ContainerBase {
    public ContainerBeeAttractor(IInventory playerInv, TileBeeAttractor te) {
        super(playerInv, te, 0, 24);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 59, 39));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 80, 26));
        this.addSlot(new SlotSpecial(tile.getInventory(), 2, 101, 39));
        this.addSlot(new SlotSpecial(tile.getInventory(), 3, 101, 65));
        this.addSlot(new SlotSpecial(tile.getInventory(), 4, 80, 78));
        this.addSlot(new SlotSpecial(tile.getInventory(), 5, 59, 65));
    }
}
