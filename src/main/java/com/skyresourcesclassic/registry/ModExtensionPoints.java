package com.skyresourcesclassic.registry;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.function.Function;

public class ModExtensionPoints {
    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () ->
            new Function<>
        );
    }
}
