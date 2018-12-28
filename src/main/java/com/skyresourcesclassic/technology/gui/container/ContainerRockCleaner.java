package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileRockCleaner;
import net.minecraft.inventory.IInventory;

public class ContainerRockCleaner extends ContainerBase {
    public ContainerRockCleaner(IInventory playerInv, TileRockCleaner te) {
        super(playerInv, te, 0, 24);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 55, 49));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 109, 31));
        this.addSlot(new SlotSpecial(tile.getInventory(), 2, 109, 49));
        this.addSlot(new SlotSpecial(tile.getInventory(), 3, 109, 67));
    }
}
