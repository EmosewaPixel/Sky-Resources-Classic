package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileRockCrusher;
import net.minecraft.inventory.IInventory;

public class ContainerRockCrusher extends ContainerBase {
    public ContainerRockCrusher(IInventory playerInv, TileRockCrusher te) {
        super(playerInv, te, 0, 24);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 44, 84));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 55, 49));
        this.addSlot(new SlotSpecial(tile.getInventory(), 2, 109, 31));
        this.addSlot(new SlotSpecial(tile.getInventory(), 3, 109, 49));
        this.addSlot(new SlotSpecial(tile.getInventory(), 4, 109, 67));
    }
}
