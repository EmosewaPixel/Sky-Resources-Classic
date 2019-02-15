package com.skyresourcesclassic.technology.gui.container;

import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.technology.tile.TileAqueousConcentrator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;

import javax.annotation.Nullable;

public class AqueousMachineInterface implements IInteractionObject {
    private BlockPos pos;

    public AqueousMachineInterface(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerAqueousConcentrator(playerInventory, (TileAqueousConcentrator) playerIn.world.getTileEntity(pos));
    }

    @Override
    public String getGuiID() {
        return "skyresourcesclassic:aqueous_concentrator_gui";
    }

    @Override
    public ITextComponent getName() {
        return new TextComponentTranslation(ModBlocks.aqueousConcentrator.getTranslationKey() + ".name", new Object[0]);
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