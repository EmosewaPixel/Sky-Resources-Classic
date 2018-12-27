package com.skyresourcesclassic.technology.gui;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.technology.gui.container.ContainerHeater;
import com.skyresourcesclassic.technology.tile.TileHeater;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHeater extends GuiContainer {

    private IInventory playerInv;
    private TileHeater tile;

    public GuiHeater(IInventory playerInv, TileHeater te) {
        super(new ContainerHeater(playerInv, te));

        this.playerInv = playerInv;
        this.tile = te;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(References.ModID, "textures/gui/heater.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = tile.getDisplayName().getUnformattedComponentText();
        this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedComponentText(), 8, 72, 4210752);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(new ResourceLocation(References.ModID, "textures/gui/gui_icons.png"));

        int burn = tile.currentItemBurnTime != 0
                ? (int) ((float) tile.fuelBurnTime * 13F / (float) tile.currentItemBurnTime) : 0;
        this.drawTexturedModalRect(81, 50 - burn, 59, 13 - burn, 14, burn);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
