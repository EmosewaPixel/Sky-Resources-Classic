package com.skyresourcesclassic.proxy;

import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.base.guide.SkyResourcesGuide;
import com.skyresourcesclassic.events.EventHandler;
import com.skyresourcesclassic.events.ModBucketHandler;
import com.skyresourcesclassic.plugin.ModPlugins;
import com.skyresourcesclassic.registry.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    EventHandler events = new EventHandler();

    public void preInit(FMLPreInitializationEvent e) {
        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        ModPlugins.preInit();

        new HeatSources();
        new SkyResourcesGuide();

        ModGuidePages.init();
        new ModGuiHandler();

    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(events);
        MinecraftForge.EVENT_BUS.register(new ModBucketHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(SkyResourcesClassic.instance, new ModGuiHandler());
        ModEntities.init();
        ModCrafting.init();

        ModPlugins.init();
    }

    public void postInit(FMLPostInitializationEvent e) {
        ModCrafting.postInit();
        ModPlugins.postInit();
    }
}
