package com.skyresourcesclassic;

import com.skyresourcesclassic.proxy.ClientProxy;
import com.skyresourcesclassic.proxy.CommonProxy;
import com.skyresourcesclassic.proxy.IModProxy;
import com.skyresourcesclassic.recipe.TagCondition;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.registry.ModGuiHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(References.ModID)
public class SkyResourcesClassic {
    public static IModProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());

    public static SkyResourcesClassic instance;

    public static Logger logger;

    public void commonSetup(final FMLCommonSetupEvent event) {
        CommonProxy.setup();
        logger = LogManager.getLogger(References.ModID);
        CraftingHelper.register(new ResourceLocation(References.ModID, "tag_condition"), new TagCondition());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        ClientProxy.clientSetup();
    }

    public void enque(InterModEnqueueEvent event) {
        proxy.enque(event);
    }

    public void process(InterModProcessEvent event) {
        proxy.process(event);
    }

    public SkyResourcesClassic() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enque);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::process);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigOptions.client_spec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigOptions.common_spec);
        FMLJavaModLoadingContext.get().getModEventBus().register(ConfigOptions.class);

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> ModGuiHandler::modGUis);

        FluidRegistry.enableUniversalBucket();
    }

    @SubscribeEvent
    public void onEntityRegistry(final RegistryEvent.Register<EntityType<?>> event) {
        registerType(event.getRegistry(), ModEntities.HEAVY_SNOWBALL, "heavy_snowball");
        registerType(event.getRegistry(), ModEntities.EXPLOSIVE_HEAVY_SNOWBALL, "heavy_explosive_snowball");
    }

    @SubscribeEvent
    public void onTERegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        registerType(event.getRegistry(), ModEntities.CRUCIBLE, "crucible");
        registerType(event.getRegistry(), ModEntities.LIFE_INFUSER, "life_infuser");
        registerType(event.getRegistry(), ModEntities.LIFE_INJECTOR, "life_injector");
        registerType(event.getRegistry(), ModEntities.DIRT_FURNACE, "dirt_furnace");
        registerType(event.getRegistry(), ModEntities.FLUID_DROPPER, "fluid_dropper");
        registerType(event.getRegistry(), ModEntities.FREEZER, "freezer");
        registerType(event.getRegistry(), ModEntities.MINI_FREEZER, "mini_freezer");
        registerType(event.getRegistry(), ModEntities.AQUEOUS_CONCENTRATOR, "aqueous_concentrator");
        registerType(event.getRegistry(), ModEntities.COMBUSTION_COLLECTOR, "combustion_collector");
        registerType(event.getRegistry(), ModEntities.CRUCIBLE_INSERTER, "crucible_inserter");
        registerType(event.getRegistry(), ModEntities.DARK_MATTER_WARPER, "dark_matter_warper");
        registerType(event.getRegistry(), ModEntities.END_PORTAL_CORE, "end_portal_core");
        registerType(event.getRegistry(), ModEntities.QUICK_DROPPER, "quick_dropper");
        registerType(event.getRegistry(), ModEntities.ROCK_CLEABER, "rock_cleaner");
        registerType(event.getRegistry(), ModEntities.ROCK_CRUSHER, "rock_crusher");
        registerType(event.getRegistry(), ModEntities.WILDLIFE_ATTRACTOR, "wildlife_attractor");
        for (int i = 1; i < 5; i++) {
            registerType(event.getRegistry(), ModEntities.condeserType(i), "condenser" + i);
            registerType(event.getRegistry(), ModEntities.crystallizerType(i), "crystallizer" + i);
            registerType(event.getRegistry(), ModEntities.heaterType(i), "heater" + i);
            registerType(event.getRegistry(), ModEntities.heaterType(i), "powered_heater" + i);
            registerType(event.getRegistry(), ModEntities.combustionHeaterType(i), "combustion_heater" + i);
            registerType(event.getRegistry(), ModEntities.combustionHeaterType(i), "powered_combustion_heater" + i);
        }
    }

    private static <T extends TileEntityType<?>> T registerType(IForgeRegistry<TileEntityType<?>> registry, T tileEntityType, String name) {
        register(registry, tileEntityType, new ResourceLocation(References.ModID, name));
        return tileEntityType;
    }

    private static <T extends EntityType<?>> T registerType(IForgeRegistry<EntityType<?>> registry, T entityType, String name) {
        register(registry, entityType, new ResourceLocation(References.ModID, name));
        return entityType;
    }

    private static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, T thing, ResourceLocation name) {
        thing.setRegistryName(name);
        registry.register(thing);
        return thing;
    }
}
