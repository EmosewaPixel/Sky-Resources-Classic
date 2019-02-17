package com.skyresourcesclassic.proxy;

import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.events.EventHandler;
import com.skyresourcesclassic.events.ModBucketHandler;
import com.skyresourcesclassic.plugin.ModPlugins;
import com.skyresourcesclassic.registry.*;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLPlayMessages;

import java.util.function.Function;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class ServerProxy implements IModProxy {
    private EventHandler events = new EventHandler();

    public static void setup() {
        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        ModPlugins.preInit();

        new HeatSources();

        new ModGuiHandler();
    }

    @Override
    public void enque(InterModEnqueueEvent e) {
        FMLJavaModLoadingContext.get().getModEventBus().register(events);
        FMLJavaModLoadingContext.get().getModEventBus().register(new ModBucketHandler());
        ModCrafting.init();
        ModPlugins.init();
    }

    @Override
    public void process(InterModProcessEvent e) {
        ModCrafting.postInit();
        ModPlugins.postInit();
    }
}
