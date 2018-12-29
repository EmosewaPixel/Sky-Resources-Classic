package com.skyresourcesclassic.recipe;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TagVariables {
    public static final Tag<Item> STEEL_INGOT = tag("ingots/steel");
    public static final Tag<Item> ELECTRICAL_STEEL_INGOT = tag("ingots/electrical_steel");
    public static final Tag<Item> URANIUM_INGOT = tag("ingots/uranium");
    public static final Tag<Item> THORIUM_INGOT = tag("ingots/thorium");

    private static Tag<Item> tag(String name)
    {
        return new ItemTags.Wrapper(new ResourceLocation("forge", name));
    }
}
