package com.skyresourcesclassic.registry;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ModItemGroups {

    public static ItemGroup tabMain = new ItemGroup(
            "skyresourcesclassic.tabMain") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItems.ironKnife);
        }
    };

    public static ItemGroup tabAlchemy = new ItemGroup(
            "skyresourcesclassic.tabAlchemy") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModItems.sandstoneInfusionStone);
        }
    };

    public static ItemGroup tabTech = new ItemGroup(
            "skyresourcesclassic.tabTech") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.combustionHeater[0]);
        }
    };
}
