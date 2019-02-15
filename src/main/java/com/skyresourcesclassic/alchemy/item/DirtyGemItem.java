package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.Item;

public class DirtyGemItem extends Item {

    public DirtyGemItem(String gem) {
        super(new Item.Properties().group(ModItemGroups.tabTech));

        setRegistryName("dirty_" + gem);
    }
}
