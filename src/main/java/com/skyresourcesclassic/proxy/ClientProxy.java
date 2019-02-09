package com.skyresourcesclassic.proxy;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.base.ModKeyBindings;
import com.skyresourcesclassic.events.ClientEventHandler;
import com.skyresourcesclassic.plugin.ModPlugins;
import com.skyresourcesclassic.registry.ModRenderers;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;

public class ClientProxy extends CommonProxy {

    public static void clientSetup() {

        OBJLoader.INSTANCE.addDomain(References.ModID);

        ModRenderers.preInit();

        ModPlugins.initRenderers();
    }

    @Override
    public void enque(InterModEnqueueEvent e) {
        FMLModLoadingContext.get().getModEventBus().register(new ClientEventHandler());
        new ModKeyBindings();
        super.enque(e);
        ModRenderers.init();
    }

    @Override
    public void process(InterModProcessEvent e) {

    }
}
