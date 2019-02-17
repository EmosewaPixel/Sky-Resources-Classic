package com.skyresourcesclassic.proxy;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.plugin.ModPlugins;
import com.skyresourcesclassic.registry.ModRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IModProxy {
    public static void setup() {

        OBJLoader.INSTANCE.addDomain(References.ModID);

        ModRenderers.preInit();

        ModPlugins.initRenderers();
    }

    @Override
    public void enque(InterModEnqueueEvent e) {
        ModRenderers.init();
    }

    @Override
    public void process(InterModProcessEvent e) {

    }
}
