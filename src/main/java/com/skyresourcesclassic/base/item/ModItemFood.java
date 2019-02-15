package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ModItemFood extends ItemFood {

    public ModItemFood(int amount, float saturation, boolean isWolfFood, String name) {
        super(amount, saturation, isWolfFood, new Item.Properties().group(ModItemGroups.tabMain));
        setRegistryName(name);
    }

}
