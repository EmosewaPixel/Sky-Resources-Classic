package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileCombustionCollector;
import net.minecraft.inventory.IInventory;

public class ContainerCombustionCollector extends ContainerBase {
    public ContainerCombustionCollector(IInventory playerInv,
                                        TileCombustionCollector te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 44, 53));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 62, 53));
        this.addSlot(new SlotSpecial(tile.getInventory(), 2, 80, 53));
        this.addSlot(new SlotSpecial(tile.getInventory(), 3, 98, 53));
        this.addSlot(new SlotSpecial(tile.getInventory(), 4, 116, 53));
    }
}
