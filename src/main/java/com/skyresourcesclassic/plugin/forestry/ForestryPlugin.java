package com.skyresourcesclassic.plugin.forestry;

import com.skyresourcesclassic.plugin.IModPlugin;
import com.skyresourcesclassic.plugin.forestry.block.BlockBeeAttractor;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModRenderers;
import forestry.api.recipes.RecipeManagers;
import forestry.apiculture.ModuleApiculture;
import forestry.core.ModuleCore;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class ForestryPlugin implements IModPlugin {

    public static Block beeAttractor;

    public void preInit() {
        beeAttractor = ModBlocks.registerBlock(new BlockBeeAttractor("bee_attractor", "bee_attractor", 2F, 12F));
    }

    public void init() {
        RecipeManagers.carpenterManager.addRecipe(25, FluidRegistry.getFluidStack("seed.oil", 500), ItemStack.EMPTY,
                new ItemStack(beeAttractor),
                new Object[]{"XXX", "XYX", "ZZZ", Character.valueOf('X'), "plankWood", Character.valueOf('Y'),
                        ModuleCore.items.impregnatedCasing, Character.valueOf('Z'), "ingotBronze"});
    }

    public void initRenderers() {
        ModRenderers.registerItemRenderer(Item.getItemFromBlock(beeAttractor));
    }

    public void postInit() {

    }
}
