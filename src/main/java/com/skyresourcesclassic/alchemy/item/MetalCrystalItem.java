package com.skyresourcesclassic.alchemy.item;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.item.Item;

public class MetalCrystalItem extends Item {

    public MetalCrystalItem(String material) {
        super();

        setUnlocalizedName(References.ModID + "." + material + "_crystal");
        setRegistryName(material + "_crystal");

        this.setCreativeTab(ModItemGroups.tabAlchemy);
    }
}
