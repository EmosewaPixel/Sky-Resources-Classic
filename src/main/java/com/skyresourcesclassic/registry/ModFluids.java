package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo.CrystalFluidType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class ModFluids {
    public static List<Fluid> crystalFluids;

    private static List<FluidRegisterInfo> crystalFluidInfos = new ArrayList<>();

    private static int curIndex = 0;

    public static void init() {
        addCrystalFluid("iron", 0xFFCC0000, 4, CrystalFluidType.NORMAL);
        addCrystalFluid("gold", 0xFFCCCC00, 6, CrystalFluidType.NORMAL);
        addCrystalFluid("copper", 0xFFFF6600, 2, CrystalFluidType.NORMAL);
        addCrystalFluid("tin", 0xFFBFBFBF, 4, CrystalFluidType.NORMAL);
        addCrystalFluid("silver", 0xFFD1F4FF, 5, CrystalFluidType.NORMAL);
        addCrystalFluid("zinc", 0xFFFFF7C2, 3, CrystalFluidType.NORMAL);
        addCrystalFluid("nickel", 0xFFFAF191, 6, CrystalFluidType.NORMAL);
        addCrystalFluid("platinum", 0xFF44EAFC, 8, CrystalFluidType.NORMAL);
        addCrystalFluid("aluminum", 0xFFF5FFFD, 4, CrystalFluidType.NORMAL);
        addCrystalFluid("lead", 0xFF5B2EFF, 5, CrystalFluidType.NORMAL);
        addCrystalFluid("cobalt", 0xFF0045D9, 7, CrystalFluidType.MOLTEN);
        addCrystalFluid("ardite", 0xFFDE9000, 7, CrystalFluidType.MOLTEN);
        addCrystalFluid("osmium", 0xFF7F13C2, 5, CrystalFluidType.NORMAL);
        addCrystalFluid("draconium", 0xFF9E6DCF, 10,
                ConfigOptions.general.draconiumType == 0 ? CrystalFluidType.NORMAL : CrystalFluidType.MOLTEN);
        addCrystalFluid("titanium", 0xFFBABABA, 7, CrystalFluidType.NORMAL);
        addCrystalFluid("tungsten", 0xFF464659, 7, CrystalFluidType.NORMAL);
        addCrystalFluid("chrome", 0xFFD6D6D6, 9, CrystalFluidType.NORMAL);
        addCrystalFluid("iridium", 0xFFE3E3E3, 12, CrystalFluidType.NORMAL);
        addCrystalFluid("boron", 0xFF9E9E9E, 6, CrystalFluidType.NORMAL);
        addCrystalFluid("lithium", 0xFFF2F2F2, 8, CrystalFluidType.NORMAL);
        addCrystalFluid("magnesium", 0xFFFFD4D4, 6, CrystalFluidType.NORMAL);
        addCrystalFluid("mithril", 0xFF45BCCC, 10, CrystalFluidType.NORMAL);

        registerCrystalFluid();
    }

    public static FluidRegisterInfo getFluidInfo(int index) {
        for (FluidRegisterInfo f : ModFluids.crystalFluidInfos()) {
            if (f.crystalIndex == index) {
                return f;
            }
        }
        return null;
    }

    private static void addCrystalFluid(String name, int color, int rarity, CrystalFluidType type) {
        crystalFluidInfos.add(new FluidRegisterInfo(name, color, rarity, curIndex, type));
        curIndex++;
    }

    private static void registerCrystalFluid() {
        crystalFluids = new ArrayList<>();
        for (int i = 0; i < crystalFluidInfos().length; i++) {
            String type = (crystalFluidInfos()[i].type == CrystalFluidType.MOLTEN ? "molten_" : "") + "crystal_fluid";
            final int val = i;
            Fluid fluid = new Fluid(crystalFluidInfos()[i].name + "_" + type, getStill("blocks/" + type + "_still"),
                    getFlowing("blocks/" + type + "_flow")) {
                @Override
                public int getColor() {
                    return crystalFluidInfos()[val].color;
                }
            };
            crystalFluids.add(fluid);
        }
    }

    private static ResourceLocation getStill(String name) {
        return new ResourceLocation(References.ModID, name);
    }

    private static ResourceLocation getFlowing(String name) {
        return new ResourceLocation(References.ModID, name);
    }

    public static FluidRegisterInfo[] crystalFluidInfos() {
        return crystalFluidInfos.toArray(new FluidRegisterInfo[crystalFluidInfos.size()]);
    }
}
