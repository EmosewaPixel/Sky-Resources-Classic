package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileEndPortalCore;
import net.minecraft.inventory.IInventory;

public class ContainerEndPortalCore extends ContainerBase {
    public ContainerEndPortalCore(IInventory playerInv,
                                  TileEndPortalCore te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
