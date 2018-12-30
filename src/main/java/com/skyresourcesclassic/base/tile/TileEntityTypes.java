package com.skyresourcesclassic.base.tile;

import com.skyresourcesclassic.alchemy.tile.CrystallizerTile;
import net.minecraft.tileentity.TileEntityType;

public class TileEntityTypes {
    public static final TileEntityType<CrystallizerTile> crystallizerType = TileEntityType.register("crystallizer", TileEntityType.Builder.create(CrystallizerTile::new));

}
