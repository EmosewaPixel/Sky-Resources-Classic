package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.Item;

public class DirtyGemItem extends Item {

    public DirtyGemItem(String gem) {
        super();

        setUnlocalizedName(References.ModID + ".dirty_" + gem);
        setRegistryName("dirty_" + gem);
        this.setCreativeTab(ModItemGroups.tabTech);
    }
}
