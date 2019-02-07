package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.alchemy.tile.*;
import com.skyresourcesclassic.base.entity.EntityHeavyExplosiveSnowball;
import com.skyresourcesclassic.base.entity.EntityHeavySnowball;
import com.skyresourcesclassic.technology.tile.*;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;

public final class ModEntities {
    public static EntityType<EntityHeavySnowball> HEAVY_SNOWBALL = EntityType.register("heavy_snowball", EntityType.Builder.create(EntityHeavySnowball.class, EntityHeavySnowball::new));
    public static EntityType<EntityHeavyExplosiveSnowball> EXPLOSIVE_HEAVY_SNOWBALL = EntityType.register("explosive_heavy_snowball", EntityType.Builder.create(EntityHeavyExplosiveSnowball.class, EntityHeavyExplosiveSnowball::new));

    public static TileEntityType<CondenserTile> CONDENSER = TileEntityType.register("condenser", TileEntityType.Builder.create(CondenserTile::new));
    public static TileEntityType<CrucibleTile> CRUCIBLE = TileEntityType.register("crucible", TileEntityType.Builder.create(CrucibleTile::new));
    public static TileEntityType<CrystallizerTile> CRYSTALLIZER = TileEntityType.register("crystallizer", TileEntityType.Builder.create(CrystallizerTile::new));
    public static TileEntityType<LifeInfuserTile> LIFE_INFUSER = TileEntityType.register("life_infuser", TileEntityType.Builder.create(LifeInfuserTile::new));
    public static TileEntityType<LifeInjectorTile> LIFE_INJECTOR = TileEntityType.register("life_injector", TileEntityType.Builder.create(LifeInjectorTile::new));
    public static TileEntityType<DirtFurnaceTile> DIRT_FURNACE = TileEntityType.register("dirt_furnace", TileEntityType.Builder.create(DirtFurnaceTile::new));
    public static TileEntityType<FluidDropperTile> FLUID_DROPPER = TileEntityType.register("fluid_dropper", TileEntityType.Builder.create(FluidDropperTile::new));
    public static TileEntityType<FreezerTile> FREEZER = TileEntityType.register("freezer", TileEntityType.Builder.create(FreezerTile::new));
    public static TileEntityType<MiniFreezerTile> MINI_FREEZER = TileEntityType.register("mini_freezer", TileEntityType.Builder.create(MiniFreezerTile::new));
    public static TileEntityType<TileAqueousConcentrator> AQUEOUS_CONCENTRATOR = TileEntityType.register("aqueous_concentrator", TileEntityType.Builder.create(TileAqueousConcentrator::new));
    public static TileEntityType<TileCombustionCollector> COMBUSTION_COLLECTOR = TileEntityType.register("combustion_collector", TileEntityType.Builder.create(TileCombustionCollector::new));
    public static TileEntityType<TileCombustionCollector> COMBUSTION_HEATER = TileEntityType.register("combustion_heater", TileEntityType.Builder.create(TileCombustionHeater::new));
    public static TileEntityType<TileCrucibleInserter> CRUCIBLE_INSERTER = TileEntityType.register("crucible_inserter", TileEntityType.Builder.create(TileCrucibleInserter::new));
    public static TileEntityType<TileDarkMatterWarper> DARK_MATTER_WARPER = TileEntityType.register("dark_matter_warper", TileEntityType.Builder.create(TileDarkMatterWarper::new));
    public static TileEntityType<TileEndPortalCore> END_PORTAL_CORE = TileEntityType.register("end_portal_core", TileEntityType.Builder.create(TileEndPortalCore::new));
    public static TileEntityType<TileHeater> HEATER = TileEntityType.register("heater", TileEntityType.Builder.create(TileHeater::new));
    public static TileEntityType<TilePoweredCombustionHeater> POWERED_COMBUSTION_HEATER = TileEntityType.register("powered_combustion_heater", TileEntityType.Builder.create(TilePoweredCombustionHeater::new));
    public static TileEntityType<TilePoweredHeater> POWERED_HEATER = TileEntityType.register("powered_heater", TileEntityType.Builder.create(TilePoweredHeater::new));
    public static TileEntityType<TileQuickDropper> QUICK_DROPPER = TileEntityType.register("quick_dropper", TileEntityType.Builder.create(TileQuickDropper::new));
    public static TileEntityType<TileRockCleaner> ROCK_CLEABER = TileEntityType.register("rock_cleaner", TileEntityType.Builder.create(TileRockCleaner::new));
    public static TileEntityType<TileRockCrusher> ROCK_CRUSHER = TileEntityType.register("rock_crusher", TileEntityType.Builder.create(TileRockCrusher::new));
    public static TileEntityType<TileWildlifeAttractor> WILDLIFE_ATTRACTOR = TileEntityType.register("wildlife_attractor", TileEntityType.Builder.create(TileWildlifeAttractor::new));
}