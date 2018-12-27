package com.skyresourcesclassic.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {

    public static ItemGroup tabMain = new ItemGroup(
            "skyresourcesclassic.tabMain") {
        @Override
        public ItemStack getIconItem() {
            return new ItemStack(ModItems.ironKnife);
        }
    };

    public static ItemGroup tabAlchemy = new ItemGroup(
            "skyresourcesclassic.tabAlchemy") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.sandstoneInfusionStone);
        }
    };

    public static ItemGroup tabTech = new ItemGroup(
            "skyresourcesclassic.tabTech") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ModBlocks.combustionHeater[0]);
        }
    };
}
