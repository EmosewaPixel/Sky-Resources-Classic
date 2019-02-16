package com.skyresourcesclassic.recipe;

import com.google.gson.JsonObject;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IConditionSerializer;

import java.util.function.BooleanSupplier;

public class TagCondition implements IConditionSerializer {
    @Override
    public BooleanSupplier parse(JsonObject json) {
        String tag = JsonUtils.getString(json, "tag");
        return () -> !new ItemTags.Wrapper(new ResourceLocation(tag)).getEntries().isEmpty();
    }
}
