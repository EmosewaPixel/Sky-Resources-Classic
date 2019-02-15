package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.tile.FreezerTile;
import com.skyresourcesclassic.technology.tile.MiniFreezerTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;

public class FreezerInterface implements IInteractionObject {
    private BlockPos pos;

    public FreezerInterface(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerFreezer(playerInventory, (MiniFreezerTile) playerIn.world.getTileEntity(pos));
    }

    @Override
    public String getGuiID() {
        return "skyresourcesclassic:freezer_gui";
    }

    @Override
    public ITextComponent getName() {
        return new TextComponentTranslation(ModBlocks.miniFreezer.getTranslationKey() + ".name", new Object[0]);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return null;
    }
}