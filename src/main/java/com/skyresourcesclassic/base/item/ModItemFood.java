package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.ItemFood;

public class ModItemFood extends ItemFood {

    public ModItemFood(int amount, float saturation, boolean isWolfFood,
                       String name) {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(References.ModID + "." + name);
        setRegistryName(name);
        this.setCreativeTab(ModItemGroups.tabMain);
    }

}
