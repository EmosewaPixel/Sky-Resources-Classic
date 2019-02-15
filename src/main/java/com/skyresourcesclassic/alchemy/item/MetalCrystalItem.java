package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.Item;

public class MetalCrystalItem extends Item {

    public MetalCrystalItem(String material) {
        super(new Item.Properties().group(ModItemGroups.tabAlchemy));

        setRegistryName(material + "_crystal");
    }
}
