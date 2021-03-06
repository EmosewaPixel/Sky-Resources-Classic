package com.skyresourcesclassic.recipe.Conditions;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.BooleanSupplier;

public class OreCondition implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
        String ore = JsonUtils.getString(json, "ore");
        return () -> !OreDictionary.getOres(ore).isEmpty();
    }
}
