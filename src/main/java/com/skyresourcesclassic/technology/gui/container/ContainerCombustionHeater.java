package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileCombustionHeater;
import net.minecraft.inventory.IInventory;

public class ContainerCombustionHeater extends ContainerBase {
    public ContainerCombustionHeater(IInventory playerInv, TileCombustionHeater te) {
        super(playerInv, te);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
