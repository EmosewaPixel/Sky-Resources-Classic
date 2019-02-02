package com.skyresourcesclassic.proxy;

import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.base.guide.SkyResourcesGuide;
import com.skyresourcesclassic.events.EventHandler;
import com.skyresourcesclassic.events.ModBucketHandler;
import com.skyresourcesclassic.plugin.ModPlugins;
import com.skyresourcesclassic.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

public class CommonProxy implements IModProxy {
    EventHandler events = new EventHandler();

    public static void setup() {
        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        ModPlugins.preInit();

        new HeatSources();
        new SkyResourcesGuide();

        ModGuidePages.init();
        new ModGuiHandler();
    }

    @Override
    public void enque(InterModEnqueueEvent e) {
        MinecraftForge.EVENT_BUS.register(events);
        MinecraftForge.EVENT_BUS.register(new ModBucketHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(SkyResourcesClassic.instance, new ModGuiHandler());
        ModEntities.init();
        ModCrafting.init();

        ModPlugins.init();
    }

    @Override
    public void process(InterModProcessEvent e) {
        ModCrafting.postInit();
        ModPlugins.postInit();
    }
}
