package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.alchemy.gui.GuiLifeInfuser;
import com.skyresourcesclassic.alchemy.gui.GuiLifeInjector;
import com.skyresourcesclassic.alchemy.gui.container.ContainerLifeInfuser;
import com.skyresourcesclassic.alchemy.gui.container.ContainerLifeInjector;
import com.skyresourcesclassic.alchemy.tile.LifeInfuserTile;
import com.skyresourcesclassic.alchemy.tile.LifeInjectorTile;
import com.skyresourcesclassic.plugin.forestry.gui.GuiBeeAttractor;
import com.skyresourcesclassic.plugin.forestry.gui.container.ContainerBeeAttractor;
import com.skyresourcesclassic.plugin.forestry.tile.TileBeeAttractor;
import com.skyresourcesclassic.technology.gui.*;
import com.skyresourcesclassic.technology.gui.container.*;
import com.skyresourcesclassic.technology.tile.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {
    public static final int CombustionHeaterGUI = 0;
    public static final int FreezerGUI = 2;
    public static final int FurnaceGUI = 3;
    public static final int DarkMatterWarperGUI = 4;
    public static final int EndPortalCoreGUI = 5;
    public static final int LifeInfuserGUI = 6;
    public static final int LifeInjectorGUI = 7;
    public static final int RockCrusherGUI = 8;
    public static final int RockCleanerGUI = 9;
    public static final int CombustionCollectorGUI = 10;
    public static final int QuickDropperGUI = 11;
    public static final int AqueousConcentratorGUI = 12;
    public static final int BeeAttractorGUI = 15;
    public static final int WildlifeAttractorGUI = 20;
    public static final int HeaterGUI = 21;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CombustionHeaterGUI:
                return new ContainerCombustionHeater(player.inventory,
                        (TileCombustionHeater) world.getTileEntity(new BlockPos(x, y, z)));
            case FreezerGUI:
                return new ContainerFreezer(player.inventory, (MiniFreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
            case FurnaceGUI:
                return new ContainerDirtFurnace(player.inventory,
                        (DirtFurnaceTile) world.getTileEntity(new BlockPos(x, y, z)));
            case DarkMatterWarperGUI:
                return new ContainerDarkMatterWarper(player.inventory,
                        (TileDarkMatterWarper) world.getTileEntity(new BlockPos(x, y, z)));
            case EndPortalCoreGUI:
                return new ContainerEndPortalCore(player.inventory,
                        (TileEndPortalCore) world.getTileEntity(new BlockPos(x, y, z)));
            case LifeInfuserGUI:
                return new ContainerLifeInfuser(player.inventory,
                        (LifeInfuserTile) world.getTileEntity(new BlockPos(x, y, z)));
            case LifeInjectorGUI:
                return new ContainerLifeInjector(player.inventory,
                        (LifeInjectorTile) world.getTileEntity(new BlockPos(x, y, z)));
            case RockCrusherGUI:
                return new ContainerRockCrusher(player.inventory,
                        (TileRockCrusher) world.getTileEntity(new BlockPos(x, y, z)));
            case RockCleanerGUI:
                return new ContainerRockCleaner(player.inventory,
                        (TileRockCleaner) world.getTileEntity(new BlockPos(x, y, z)));
            case CombustionCollectorGUI:
                return new ContainerCombustionCollector(player.inventory,
                        (TileCombustionCollector) world.getTileEntity(new BlockPos(x, y, z)));
            case QuickDropperGUI:
                return new ContainerQuickDropper(player.inventory,
                        (TileQuickDropper) world.getTileEntity(new BlockPos(x, y, z)));
            case AqueousConcentratorGUI:
                return new ContainerAqueousConcentrator(player.inventory,
                        (TileAqueousConcentrator) world.getTileEntity(new BlockPos(x, y, z)));
            case BeeAttractorGUI:
                return new ContainerBeeAttractor(player.inventory,
                        (TileBeeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
            case WildlifeAttractorGUI:
                return new ContainerWildlifeAttractor(player.inventory,
                        (TileWildlifeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
            case HeaterGUI:
                return new ContainerHeater(player.inventory,
                        (TileHeater) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case CombustionHeaterGUI:
                return new GuiCombustionHeater(player.inventory,
                        (TileCombustionHeater) world.getTileEntity(new BlockPos(x, y, z)));
            case FreezerGUI:
                return new GuiFreezer(player.inventory, (MiniFreezerTile) world.getTileEntity(new BlockPos(x, y, z)));
            case FurnaceGUI:
                return new GuiDirtFurnace(player.inventory, (DirtFurnaceTile) world.getTileEntity(new BlockPos(x, y, z)));
            case DarkMatterWarperGUI:
                return new GuiDarkMatterWarper(player.inventory,
                        (TileDarkMatterWarper) world.getTileEntity(new BlockPos(x, y, z)));
            case EndPortalCoreGUI:
                return new GuiEndPortalCore(player.inventory,
                        (TileEndPortalCore) world.getTileEntity(new BlockPos(x, y, z)));
            case LifeInfuserGUI:
                return new GuiLifeInfuser(player.inventory, (LifeInfuserTile) world.getTileEntity(new BlockPos(x, y, z)));
            case LifeInjectorGUI:
                return new GuiLifeInjector(player.inventory, (LifeInjectorTile) world.getTileEntity(new BlockPos(x, y, z)));
            case RockCrusherGUI:
                return new GuiRockCrusher(player.inventory, (TileRockCrusher) world.getTileEntity(new BlockPos(x, y, z)));
            case RockCleanerGUI:
                return new GuiRockCleaner(player.inventory, (TileRockCleaner) world.getTileEntity(new BlockPos(x, y, z)));
            case CombustionCollectorGUI:
                return new GuiCombustionCollector(player.inventory,
                        (TileCombustionCollector) world.getTileEntity(new BlockPos(x, y, z)));
            case QuickDropperGUI:
                return new GuiQuickDropper(player.inventory, (TileQuickDropper) world.getTileEntity(new BlockPos(x, y, z)));
            case AqueousConcentratorGUI:
                return new GuiAqueousConcentrator(player.inventory,
                        (TileAqueousConcentrator) world.getTileEntity(new BlockPos(x, y, z)));
            case BeeAttractorGUI:
                return new GuiBeeAttractor(player.inventory, (TileBeeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
            case WildlifeAttractorGUI:
                return new GuiWildlifeAttractor(player.inventory,
                        (TileWildlifeAttractor) world.getTileEntity(new BlockPos(x, y, z)));
            case HeaterGUI:
                return new GuiHeater(player.inventory,
                        (TileHeater) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }
}
