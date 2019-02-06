package com.skyresourcesclassic.recipe;

import net.minecraft.tags.ItemTags;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionSerializer;

public class Conditions {
    public static final IConditionSerializer TAG_CONDITION = condition("tag_condition", json -> {
        String tag = JsonUtils.getString(json, "tag");
        return () -> !new ItemTags.Wrapper(new ResourceLocation(tag)).getEntries().isEmpty();
    });

    private static IConditionSerializer condition(String name, IConditionSerializer serializer) {
        return CraftingHelper.register(new ResourceLocation("skyresourcesclassic", name), serializer);
    }
}
