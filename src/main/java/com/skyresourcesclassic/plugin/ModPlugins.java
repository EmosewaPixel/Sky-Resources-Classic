package com.skyresourcesclassic.plugin;

import com.skyresourcesclassic.plugin.actuallyadditions.ActAddPlugin;
import com.skyresourcesclassic.plugin.armorplus.ArmorPlusPlugin;
import com.skyresourcesclassic.plugin.ctweaker.CraftTweakerPlugin;
import com.skyresourcesclassic.plugin.extremereactors.ExtremeReactorsPlugin;
import com.skyresourcesclassic.plugin.forestry.ForestryPlugin;
import com.skyresourcesclassic.plugin.integdyn.IntegratedDynamicsPlugin;
import com.skyresourcesclassic.plugin.tconstruct.TConPlugin;
import com.skyresourcesclassic.plugin.techreborn.TechRebornPlugin;
import com.skyresourcesclassic.plugin.theoneprobe.TOPPlugin;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;

public class ModPlugins {
    public static ArrayList<IModPlugin> plugins = new ArrayList<IModPlugin>();

    public static void preInit() {
        if (ModList.get().isLoaded("forestry"))
            plugins.add(new ForestryPlugin());
        if (ModList.get().isLoaded(("tconstruct"))
        plugins.add(new TConPlugin());
        if (ModList.get().isLoaded(("techreborn"))
        plugins.add(new TechRebornPlugin());
        if (ModList.get().isLoaded(("armorplus"))
        plugins.add(new ArmorPlusPlugin());
        if (ModList.get().isLoaded(("integrateddynamics"))
        plugins.add(new IntegratedDynamicsPlugin());
        if (ModList.get().isLoaded(("bigreactors"))
        plugins.add(new ExtremeReactorsPlugin());
        if (ModList.get().isLoaded(("actuallyadditions"))
        plugins.add(new ActAddPlugin());
        if (ModList.get().isLoaded(("crafttweaker"))
        plugins.add(new CraftTweakerPlugin());
        if (ModList.get().isLoaded(("theoneprobe"))
        plugins.add(new TOPPlugin());

        for (IModPlugin p : plugins) {
            p.preInit();
        }
    }

    public static void init() {
        for (IModPlugin p : plugins) {
            p.init();
        }
    }

    public static void postInit() {
        for (IModPlugin p : plugins) {
            p.postInit();
        }
    }

    public static void initRenderers() {
        for (IModPlugin p : plugins) {
            p.initRenderers();
        }
    }
}
