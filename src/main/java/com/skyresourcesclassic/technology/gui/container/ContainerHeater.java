package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileHeater;
import net.minecraft.inventory.IInventory;

public class ContainerHeater extends ContainerBase {
    public ContainerHeater(IInventory playerInv, TileHeater te) {
        super(playerInv, te);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
