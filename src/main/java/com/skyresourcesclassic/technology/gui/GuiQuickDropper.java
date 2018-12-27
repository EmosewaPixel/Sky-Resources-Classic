package com.skyresourcesclassic.technology.gui;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.technology.gui.container.ContainerQuickDropper;
import com.skyresourcesclassic.technology.tile.TileQuickDropper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiQuickDropper extends GuiContainer {

    private IInventory playerInv;
    private TileQuickDropper tile;

    public GuiQuickDropper(IInventory playerInv, TileQuickDropper te) {
        super(new ContainerQuickDropper(playerInv, te));

        this.playerInv = playerInv;
        this.tile = te;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(References.ModID, "textures/gui/blank_inventory.png"));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 52, 7, 83, 18, 18);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = tile.getDisplayName().getUnformattedComponentText();
        this.fontRenderer.drawString(s, 88 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.getDisplayName().getUnformattedComponentText(), 8, 72, 4210752);

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
