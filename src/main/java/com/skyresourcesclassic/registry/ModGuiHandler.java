package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.gui.GuiLifeInfuser;
import com.skyresourcesclassic.alchemy.gui.GuiLifeInjector;
import com.skyresourcesclassic.alchemy.tile.LifeInfuserTile;
import com.skyresourcesclassic.alchemy.tile.LifeInjectorTile;
import com.skyresourcesclassic.plugin.forestry.gui.GuiBeeAttractor;
import com.skyresourcesclassic.plugin.forestry.tile.TileBeeAttractor;
import com.skyresourcesclassic.technology.gui.*;
import com.skyresourcesclassic.technology.tile.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class ModGuiHandler {
    public static GuiScreen modGUis(FMLPlayMessages.OpenContainer container) {
        EntityPlayer player = Minecraft.getInstance().player;
        TileEntity tile = player.world.getTileEntity(player.getPosition());
        switch (container.getId().toString()) {
            case References.ModID + "combustion_heater_gui":
                return new GuiCombustionHeater(player.inventory,
                        (TileCombustionHeater) tile);
            case References.ModID + "freezer_gui":
                return new GuiFreezer(player.inventory, (MiniFreezerTile) tile);
            case References.ModID + "dirt_furnace_gui":
                return new GuiDirtFurnace(player.inventory, (DirtFurnaceTile) tile);
            case References.ModID + "dark_matter_warper_gui":
                return new GuiDarkMatterWarper(player.inventory,
                        (TileDarkMatterWarper) tile);
            case References.ModID + "end_portal_core_gui":
                return new GuiEndPortalCore(player.inventory,
                        (TileEndPortalCore) tile);
            case References.ModID + "life_infuser_gui":
                return new GuiLifeInfuser(player.inventory, (LifeInfuserTile) tile);
            case References.ModID + "life_injector_gui":
                return new GuiLifeInjector(player.inventory, (LifeInjectorTile) tile);
            case References.ModID + "rock_crusher_gui":
                return new GuiRockCrusher(player.inventory, (TileRockCrusher) tile);
            case References.ModID + "rock_cleaner_gui":
                return new GuiRockCleaner(player.inventory, (TileRockCleaner) tile);
            case References.ModID + "combustion_collector_gui":
                return new GuiCombustionCollector(player.inventory,
                        (TileCombustionCollector) tile);
            case References.ModID + "quick_dropper_gui":
                return new GuiQuickDropper(player.inventory, (TileQuickDropper) tile);
            case References.ModID + "aqueous_concentrator_gui":
                return new GuiAqueousConcentrator(player.inventory,
                        (TileAqueousConcentrator) tile);
            case References.ModID + "bee_attractor_gui":
                return new GuiBeeAttractor(player.inventory, (TileBeeAttractor) tile);
            case References.ModID + "wildlife_attractor_gui":
                return new GuiWildlifeAttractor(player.inventory,
                        (TileWildlifeAttractor) tile);
            case References.ModID + "heater_gui":
                return new GuiHeater(player.inventory,
                        (TileHeater) tile);
            default:
                return null;
        }
    }
}
