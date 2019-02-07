package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.registry.ModEntities;

public class TileCombustionCollector extends TileItemInventory {
    public TileCombustionCollector() {
        super("combustionCollector", ModEntities.COMBUSTION_COLLECTOR, 5);
    }
}
