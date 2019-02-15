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

    public static TileEntityType<CondenserTile> CONDENSER_1 = TileEntityType.register("condenser_1", TileEntityType.Builder.create(() -> new CondenserTile(1)));
    public static TileEntityType<CondenserTile> CONDENSER_2 = TileEntityType.register("condenser_2", TileEntityType.Builder.create(() -> new CondenserTile(2)));
    public static TileEntityType<CondenserTile> CONDENSER_3 = TileEntityType.register("condenser_3", TileEntityType.Builder.create(() -> new CondenserTile(3)));
    public static TileEntityType<CondenserTile> CONDENSER_4 = TileEntityType.register("condenser_4", TileEntityType.Builder.create(() -> new CondenserTile(4)));
    public static TileEntityType<CrucibleTile> CRUCIBLE = TileEntityType.register("crucible", TileEntityType.Builder.create(CrucibleTile::new));
    public static TileEntityType<CrystallizerTile> CRYSTALLIZER_1 = TileEntityType.register("crystallizer_1", TileEntityType.Builder.create(() -> new CrystallizerTile(1)));
    public static TileEntityType<CrystallizerTile> CRYSTALLIZER_2 = TileEntityType.register("crystallizer_2", TileEntityType.Builder.create(() -> new CrystallizerTile(1)));
    public static TileEntityType<CrystallizerTile> CRYSTALLIZER_3 = TileEntityType.register("crystallizer_3", TileEntityType.Builder.create(() -> new CrystallizerTile(1)));
    public static TileEntityType<CrystallizerTile> CRYSTALLIZER_4 = TileEntityType.register("crystallizer_4", TileEntityType.Builder.create(() -> new CrystallizerTile(1)));
    public static TileEntityType<LifeInfuserTile> LIFE_INFUSER = TileEntityType.register("life_infuser", TileEntityType.Builder.create(LifeInfuserTile::new));
    public static TileEntityType<LifeInjectorTile> LIFE_INJECTOR = TileEntityType.register("life_injector", TileEntityType.Builder.create(LifeInjectorTile::new));
    public static TileEntityType<DirtFurnaceTile> DIRT_FURNACE = TileEntityType.register("dirt_furnace", TileEntityType.Builder.create(DirtFurnaceTile::new));
    public static TileEntityType<FluidDropperTile> FLUID_DROPPER = TileEntityType.register("fluid_dropper", TileEntityType.Builder.create(FluidDropperTile::new));
    public static TileEntityType<FreezerTile> FREEZER = TileEntityType.register("freezer", TileEntityType.Builder.create(FreezerTile::new));
    public static TileEntityType<MiniFreezerTile> MINI_FREEZER = TileEntityType.register("mini_freezer", TileEntityType.Builder.create(MiniFreezerTile::new));
    public static TileEntityType<TileAqueousConcentrator> AQUEOUS_CONCENTRATOR = TileEntityType.register("aqueous_concentrator", TileEntityType.Builder.create(TileAqueousConcentrator::new));
    public static TileEntityType<TileCombustionCollector> COMBUSTION_COLLECTOR = TileEntityType.register("combustion_collector", TileEntityType.Builder.create(TileCombustionCollector::new));
    public static TileEntityType<TileCombustionHeater> COMBUSTION_HEATER_1 = TileEntityType.register("combustion_heater_1", TileEntityType.Builder.create(() -> new TileCombustionHeater(1)));
    public static TileEntityType<TileCombustionHeater> COMBUSTION_HEATER_2 = TileEntityType.register("combustion_heater_2", TileEntityType.Builder.create(() -> new TileCombustionHeater(2)));
    public static TileEntityType<TileCrucibleInserter> CRUCIBLE_INSERTER = TileEntityType.register("crucible_inserter", TileEntityType.Builder.create(TileCrucibleInserter::new));
    public static TileEntityType<TileDarkMatterWarper> DARK_MATTER_WARPER = TileEntityType.register("dark_matter_warper", TileEntityType.Builder.create(TileDarkMatterWarper::new));
    public static TileEntityType<TileEndPortalCore> END_PORTAL_CORE = TileEntityType.register("end_portal_core", TileEntityType.Builder.create(TileEndPortalCore::new));
    public static TileEntityType<TileHeater> HEATER_1 = TileEntityType.register("heater_1", TileEntityType.Builder.create(() -> new TileHeater(1)));
    public static TileEntityType<TileHeater> HEATER_2 = TileEntityType.register("heater_2", TileEntityType.Builder.create(() -> new TileHeater(2)));
    public static TileEntityType<TilePoweredCombustionHeater> POWERED_COMBUSTION_HEATER_1 = TileEntityType.register("powered_combustion_heater_3", TileEntityType.Builder.create(() -> new TilePoweredCombustionHeater(3)));
    public static TileEntityType<TilePoweredCombustionHeater> POWERED_COMBUSTION_HEATER_2 = TileEntityType.register("powered_combustion_heater_4", TileEntityType.Builder.create(() -> new TilePoweredCombustionHeater(4)));
    public static TileEntityType<TilePoweredHeater> POWERED_HEATER_1 = TileEntityType.register("powered_heater_3", TileEntityType.Builder.create(() -> new TilePoweredHeater(3)));
    public static TileEntityType<TilePoweredHeater> POWERED_HEATER_2 = TileEntityType.register("powered_heater_4", TileEntityType.Builder.create(() -> new TilePoweredHeater(4)));
    public static TileEntityType<TileQuickDropper> QUICK_DROPPER = TileEntityType.register("quick_dropper", TileEntityType.Builder.create(TileQuickDropper::new));
    public static TileEntityType<TileRockCleaner> ROCK_CLEABER = TileEntityType.register("rock_cleaner", TileEntityType.Builder.create(TileRockCleaner::new));
    public static TileEntityType<TileRockCrusher> ROCK_CRUSHER = TileEntityType.register("rock_crusher", TileEntityType.Builder.create(TileRockCrusher::new));
    public static TileEntityType<TileWildlifeAttractor> WILDLIFE_ATTRACTOR = TileEntityType.register("wildlife_attractor", TileEntityType.Builder.create(TileWildlifeAttractor::new));

    public static TileEntityType condeserType(int tier) {
        switch (tier) {
            case 1:
                return CONDENSER_1;
            case 2:
                return CONDENSER_2;
            case 3:
                return CONDENSER_3;
            case 4:
                return CONDENSER_4;
            default:
                return null;
        }
    }

    public static TileEntityType crystallizerType(int tier) {
        switch (tier) {
            case 1:
                return CRYSTALLIZER_1;
            case 2:
                return CRYSTALLIZER_2;
            case 3:
                return CRYSTALLIZER_3;
            case 4:
                return CRYSTALLIZER_4;
            default:
                return null;
        }
    }

    public static TileEntityType heaterType(int tier) {
        switch (tier) {
            case 1:
                return HEATER_1;
            case 2:
                return HEATER_2;
            case 3:
                return POWERED_HEATER_1;
            case 4:
                return POWERED_HEATER_1;
            default:
                return null;
        }
    }

    public static TileEntityType combustionHeaterType(int tier) {
        switch (tier) {
            case 1:
                return COMBUSTION_HEATER_1;
            case 2:
                return COMBUSTION_HEATER_2;
            case 3:
                return POWERED_COMBUSTION_HEATER_1;
            case 4:
                return POWERED_COMBUSTION_HEATER_2;
            default:
                return null;
        }
    }
}