package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.alchemy.tile.*;
import com.skyresourcesclassic.base.entity.EntityHeavyExplosiveSnowball;
import com.skyresourcesclassic.base.entity.EntityHeavySnowball;
import com.skyresourcesclassic.plugin.forestry.tile.TileBeeAttractor;
import com.skyresourcesclassic.technology.tile.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModEntities {
    public static EntityType<EntityHeavySnowball> HEAVY_SNOWBALL = EntityType.register("heavy_snowball", EntityType.Builder.create(EntityHeavySnowball.class, EntityHeavySnowball::new));
    public static EntityType<EntityHeavyExplosiveSnowball> EXPLOSIVE_HEAVY_SNOWBALL = EntityType.register("explosive_heavy_snowball", EntityType.Builder.create(EntityHeavyExplosiveSnowball.class, EntityHeavyExplosiveSnowball::new));

    public static void init() {
        registerTileEntity(TileCombustionHeater.class, "::combustionHeatertile");
        registerTileEntity(FluidDropperTile.class, ":fluidDroppertile");
        registerTileEntity(CondenserTile.class, ":condensertile");
        registerTileEntity(CrucibleTile.class, ":crucibletile");
        registerTileEntity(FreezerTile.class, ":freezertile");
        registerTileEntity(MiniFreezerTile.class, ":minifreezertile");
        registerTileEntity(DirtFurnaceTile.class, ":dirtfurnacetile");
        registerTileEntity(TilePoweredHeater.class, ":poweredHeatertile");
        registerTileEntity(TilePoweredCombustionHeater.class, ":poweredcombustionheatertile");
        registerTileEntity(CrystallizerTile.class, ":crystallizertile");
        registerTileEntity(TileDarkMatterWarper.class, ":darkmatterwarpertile");
        registerTileEntity(TileEndPortalCore.class, ":endportalcoretile");
        registerTileEntity(LifeInfuserTile.class, ":lifeinfusertile");
        registerTileEntity(LifeInjectorTile.class, ":lifeinjectortile");
        registerTileEntity(TileCrucibleInserter.class, ":crucibleinsertertile");
        registerTileEntity(TileRockCrusher.class, ":rockcrushertile");
        registerTileEntity(TileRockCleaner.class, ":rockcleanertile");
        registerTileEntity(TileCombustionCollector.class, ":combustioncollectortile");
        registerTileEntity(TileQuickDropper.class, ":quickdroppertile");
        registerTileEntity(TileAqueousConcentrator.class, ":aqueousconcentratortile");
        registerTileEntity(TileBeeAttractor.class, ":beeAttractorTile");
        registerTileEntity(TileWildlifeAttractor.class, ":wildlifeAttractorTile");
        registerTileEntity(TileHeater.class, ":heaterTile");

        EntityRegistry.registerModEntity(new ResourceLocation(References.ModID, "heavysnowball"),
                EntityHeavySnowball.class, References.ModID + ":heavysnowball", 1, SkyResourcesClassic.instance, 64, 2, true);
        EntityRegistry.registerModEntity(new ResourceLocation(References.ModID, "heavyexplosivesnowball"),
                EntityHeavyExplosiveSnowball.class, References.ModID + ":heavyexplosivesnowball", 2,
                SkyResourcesClassic.instance, 64, 2, true);
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String key) {
        GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(References.ModID + key));
    }
}
