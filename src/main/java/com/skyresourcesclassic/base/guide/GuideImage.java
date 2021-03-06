package com.skyresourcesclassic.base.guide;

import com.skyresourcesclassic.registry.ModGuidePages;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class GuideImage {
    private Map<BlockPos, IBlockState> blocks = new HashMap();
    private Map<BlockPos, IBlockState> drawBlocks = new HashMap();
    private Map<BlockPos, TileEntity> drawTEs = new HashMap();
    protected String imgAddress;

    public ImageWorld world = new ImageWorld(this);

    protected GuideImage(String imgAddress) {
        this.imgAddress = imgAddress;
        if (ModGuidePages.imageDesigns.containsKey(imgAddress))
            blocks = ModGuidePages.imageDesigns.get(imgAddress);
        else
            blocks = new HashMap();
    }

    public void draw(Minecraft mc, int x, int y, int width, int height, float partialTicks) {
        BlockRendererDispatcher renderer = mc.getBlockRendererDispatcher();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x + width / 2, y + height / 2, 150);

        int min = 0;
        int max = 0;
        for (BlockPos pos : blocks.keySet()) {
            if (pos.getY() < min)
                min = pos.getY();
            else if (pos.getY() > max)
                max = pos.getY();

        }

        int layer = (int) (mc.getSystemTime() % (2000 * (max - min + 1))) / 2000 + min;

        double sc = 150 / (max - min + 1);
        GlStateManager.scale(-sc, -sc, -sc);

        GlStateManager.rotate(-15, 1, 0, 0);
        GlStateManager.rotate((mc.getSystemTime() % (360 * 40)) / 40f, 0, 1, 0);
        GlStateManager.translate(-0.5, -0.5, -0.5);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        drawBlocks.clear();
        for (BlockPos pos : drawTEs.keySet()) {
            Minecraft.getMinecraft().world.removeTileEntity(pos);
        }
        drawTEs.clear();
        for (BlockPos pos : blocks.keySet()) {
            if (pos.getY() <= layer) {
                drawBlocks.put(pos, blocks.get(pos));
            }
        }
        for (BlockPos pos : drawBlocks.keySet()) {
            renderer.renderBlock(drawBlocks.get(pos), pos, world, Tessellator.getInstance().getBuffer());
        }
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    private static class ImageWorld implements IBlockAccess {

        private ImageWorld(GuideImage image) {
            this.image = image;
        }

        private GuideImage image;

        public boolean isSideSolid(BlockPos pos, EnumFacing side, boolean _default) {
            return getBlockState(pos).isSideSolid(this, pos, side);
        }

        public IBlockState getBlockState(BlockPos pos) {
            return image.drawBlocks.keySet().contains(pos) ? image.drawBlocks.get(pos) : Blocks.AIR.getDefaultState();
        }

        public boolean isAirBlock(BlockPos pos) {
            return getBlockState(pos).getBlock() == Blocks.AIR;
        }

        public Biome getBiome(BlockPos pos) {
            return Biomes.PLAINS;
        }

        public int getStrongPower(BlockPos pos, EnumFacing direction) {
            return 0;
        }

        public WorldType getWorldType() {
            return WorldType.DEFAULT;
        }

        public int getCombinedLight(BlockPos pos, int lightValue) {
            return 0xF000F0;
        }

        public TileEntity getTileEntity(BlockPos pos) {
            return image.drawTEs.get(pos);
        }
    }
}
