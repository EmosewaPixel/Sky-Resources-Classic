package com.skyresourcesclassic.alchemy.gui.container;

import com.skyresourcesclassic.alchemy.tile.LifeInfuserTile;
import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import net.minecraft.inventory.IInventory;

public class ContainerLifeInfuser extends ContainerBase {

    public ContainerLifeInfuser(IInventory playerInv, LifeInfuserTile te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 100, 25));
        this.addSlot(new SlotSpecial(tile.getInventory(), 1, 59, 25));
        this.addSlot(new SlotSpecial(tile.getInventory(), 2, 27, 25));
    }
}
