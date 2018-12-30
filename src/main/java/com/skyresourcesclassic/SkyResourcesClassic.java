package com.skyresourcesclassic;

import com.skyresourcesclassic.proxy.CommonProxy;
import net.minecraft.item.ItemTier;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.javafmlmod.FMLModLoadingContext;
import org.apache.logging.log4j.Logger;

@Mod(References.ModID)
public class SkyResourcesClassic {
    @SidedProxy(clientSide = "com.skyresourcesclassic.proxy.ClientProxy", serverSide = "com.skyresourcesclassic.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static SkyResourcesClassic instance;

    public static Logger logger;

    public static ItemTier materialCactusNeedle = EnumHelper.addItemTier("CACTUSNEEDLE", 0, 4, 5, 1, 5);

    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public SkyResourcesClassic() {
        FMLModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLModLoadingContext.get().getModEventBus().addListener(this::postInit);
        FluidRegistry.enableUniversalBucket();
    }
}
