package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileDarkMatterWarper;
import net.minecraft.inventory.IInventory;

public class ContainerDarkMatterWarper extends ContainerBase {
    public ContainerDarkMatterWarper(IInventory playerInv,
                                     TileDarkMatterWarper te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
