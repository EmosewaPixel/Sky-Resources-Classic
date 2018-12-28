package com.skyresourcesclassic.alchemy.gui.container;

import com.skyresourcesclassic.alchemy.tile.LifeInjectorTile;
import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import net.minecraft.inventory.IInventory;

public class ContainerLifeInjector extends ContainerBase {
    public ContainerLifeInjector(IInventory playerInv, LifeInjectorTile te) {
        super(playerInv, te);

        this.addSlot(new SlotSpecial(tile.getInventory(), 0, 80, 53));
    }
}
