package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileWildlifeAttractor;
import net.minecraft.inventory.IInventory;

public class ContainerWildlifeAttractor extends ContainerBase {
    public ContainerWildlifeAttractor(IInventory playerInv,
                                      TileWildlifeAttractor te) {
        super(playerInv, te, 0, 24);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 59));
    }
}
