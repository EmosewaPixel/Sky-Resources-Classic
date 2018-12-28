package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.base.gui.ContainerBase;
import com.skyresourcesclassic.base.gui.SlotSpecial;
import com.skyresourcesclassic.technology.tile.MiniFreezerTile;
import net.minecraft.inventory.IInventory;

public class ContainerFreezer extends ContainerBase {
    private MiniFreezerTile tile;

    public ContainerFreezer(IInventory playerInv, MiniFreezerTile te) {
        super(playerInv, te);
        tile = te;

        for (int y = 0; y < Math.floor((float) tile.getInventory().getSlots() / 2f / 5f) + 1; ++y) {
            for (int x = 0; x < Math.min(5, tile.getInventory().getSlots() / 2f - y * 5); ++x) {
                this.addSlot(
                        new SlotSpecial(tile.getInventory(), x + y * 5, 53 + x * 18, 22 + y * 18));
                this.addSlot(new SlotSpecial(tile.getInventory(),
                        x + y * 5 + tile.getInventory().getSlots() / 2, 53 + x * 18, 40 + y * 18));

            }
        }
    }
}
