package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileQuickDropper;
import net.minecraft.inventory.IInventory;

public class ContainerQuickDropper extends ContainerBase {
    public ContainerQuickDropper(IInventory playerInv,
                                 TileQuickDropper te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
