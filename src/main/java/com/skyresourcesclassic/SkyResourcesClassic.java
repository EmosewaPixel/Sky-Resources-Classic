package com.skyresourcesclassic;

import com.skyresourcesclassic.proxy.ClientProxy;
import com.skyresourcesclassic.proxy.CommonProxy;
import com.skyresourcesclassic.proxy.IModProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(References.ModID)
public class SkyResourcesClassic {
    public static IModProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static SkyResourcesClassic instance;

    public static Logger logger;

    public void commonSetup(final FMLCommonSetupEvent event) {
        CommonProxy.setup();
        logger = LogManager.getLogger(References.ModID);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        CommonProxy.setup();
        ClientProxy.clientSetup();
    }

    public void enque(InterModEnqueueEvent event) {
        proxy.enque(event);
    }

    public void process(InterModProcessEvent event) {
        proxy.process(event);
    }

    public SkyResourcesClassic() {
        FMLModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLModLoadingContext.get().getModEventBus().addListener(this::enque);
        FMLModLoadingContext.get().getModEventBus().addListener(this::process);

        FMLModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigOptions.client_spec);
        FMLModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigOptions.server_spec);

        FluidRegistry.enableUniversalBucket();
    }
}
