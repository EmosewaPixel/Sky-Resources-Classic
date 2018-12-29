package com.skyresourcesclassic.base;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModKeyBindings {
    public static KeyBinding guideKey;

    public ModKeyBindings() {
        guideKey = new KeyBinding("key.skyresourcesclassic.guide", 0x22, "Sky Resources");
        ClientRegistry.registerKeyBinding(guideKey);
    }
}
