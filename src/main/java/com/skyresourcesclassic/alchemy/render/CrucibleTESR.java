package com.skyresourcesclassic.alchemy.render;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.alchemy.tile.CrucibleTile;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class CrucibleTESR extends TileEntityRenderer<CrucibleTile> {

    @Override
    public void render(CrucibleTile te, double x, double y, double z, float partialTicks, int destroyStage) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5f, (float) y + 1.50f, (float) z + 0.5f);
        GL11.glRotatef(180f, 1, 0, 0);
        GL11.glPopMatrix();
        if (te.getTank().getFluid() != null && te.getTank().getFluidAmount() > 0) {
            GL11.glPushMatrix();

            GL11.glTranslatef((float) x, (float) y + 1f, (float) z + 1);
            GL11.glRotatef(180f, 1f, 0f, 0f);
            renderFluidContents(te);
            GL11.glPopMatrix();

        }
    }

    private void renderFluidContents(CrucibleTile crucible) {
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        FluidStack fluidStack = crucible.getTank().getFluid();

        double liquid = crucible.getTank().getFluidAmount();
        double maxLiquid = crucible.getTank().getCapacity();
        double height = (liquid / maxLiquid) * 0.7;
        GL11.glRotated(180f, 1, 0, 0);
        RandomHelper.renderFluidCuboid(fluidStack, crucible.getPos(), 0, -0.85, -1, 0.1, 0.2,
                0.1, 1 - 0.1, 0.1 + height, 1 - 0.1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
