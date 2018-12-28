package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.TileAqueousConcentrator;
import net.minecraft.inventory.IInventory;

public class ContainerAqueousConcentrator extends ContainerBase {
    public ContainerAqueousConcentrator(IInventory playerInv, TileAqueousConcentrator te) {
        super(playerInv, te, 0, 24);
        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 55, 49));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 109, 49));
    }
}
