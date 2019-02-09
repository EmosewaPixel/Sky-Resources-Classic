package com.skyresourcesclassic.base.guide;

import com.skyresourcesclassic.registry.ModGuidePages;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.Heightmap;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

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

        GlStateManager.translatef(x + width / 2, y + height / 2, 150);

        int min = 0;
        int max = 0;
        for (BlockPos pos : blocks.keySet()) {
            if (pos.getY() < min)
                min = pos.getY();
            else if (pos.getY() > max)
                max = pos.getY();

        }

        int layer = (int) (Util.milliTime() % (2000 * (max - min + 1))) / 2000 + min;

        double sc = 150 / (max - min + 1);
        GlStateManager.scaled(-sc, -sc, -sc);

        GlStateManager.rotatef(-15, 1, 0, 0);
        GlStateManager.rotatef((Util.milliTime() % (360 * 40)) / 40f, 0, 1, 0);
        GlStateManager.translated(-0.5, -0.5, -0.5);

        Minecraft.getInstance().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        drawBlocks.clear();
        for (BlockPos pos : drawTEs.keySet()) {
            Minecraft.getInstance().world.removeTileEntity(pos);
        }
        drawTEs.clear();
        for (BlockPos pos : blocks.keySet()) {
            if (pos.getY() <= layer) {
                drawBlocks.put(pos, blocks.get(pos));
            }
        }
        for (BlockPos pos : drawBlocks.keySet()) {
            renderer.renderBlock(drawBlocks.get(pos), pos, world, Tessellator.getInstance().getBuffer(), new Random());
        }
        Tessellator.getInstance().draw();
        GlStateManager.popMatrix();
    }

    private static class ImageWorld implements IWorldReader {

        private ImageWorld(GuideImage image) {
            this.image = image;
        }

        private GuideImage image;

        public IBlockState getBlockState(BlockPos pos) {
            return image.drawBlocks.keySet().contains(pos) ? image.drawBlocks.get(pos) : Blocks.AIR.getDefaultState();
        }

        @Override
        public int getCombinedLight(BlockPos blockPos, int i) {
            return 0;
        }

        @Override
        public boolean isAirBlock(BlockPos blockPos) {
            return getBlockState(blockPos) == Blocks.AIR;
        }

        @Override
        public Biome getBiome(BlockPos blockPos) {
            return Biomes.PLAINS;
        }

        @Override
        public int getLightFor(EnumLightType enumLightType, BlockPos blockPos) {
            return enumLightType == EnumLightType.SKY ? 15 : getBlockState(blockPos).getLightValue();
        }

        @Override
        public int getLightSubtracted(BlockPos blockPos, int i) {
            return getBlockState(blockPos).getLightValue() - i;
        }

        @Override
        public boolean isChunkLoaded(int i, int i1, boolean b) {
            return false;
        }

        @Override
        public boolean canSeeSky(BlockPos blockPos) {
            return false;
        }

        @Override
        public int getHeight(Heightmap.Type type, int i, int i1) {
            return 0;
        }

        @Nullable
        @Override
        public EntityPlayer getClosestPlayer(double v, double v1, double v2, double v3, Predicate<Entity> predicate) {
            return null;
        }

        @Override
        public int getSkylightSubtracted() {
            return 0;
        }

        @Override
        public WorldBorder getWorldBorder() {
            return null;
        }

        @Override
        public boolean checkNoEntityCollision(@Nullable Entity entity, VoxelShape voxelShape) {
            return false;
        }

        @Override
        public List<Entity> getEntitiesWithinAABBExcludingEntity(@Nullable Entity entity, AxisAlignedBB axisAlignedBB) {
            return null;
        }

        @Override
        public int getStrongPower(BlockPos blockPos, EnumFacing enumFacing) {
            return this.getBlockState(blockPos).getStrongPower(this, blockPos, enumFacing);
        }

        @Override
        public boolean isRemote() {
            return false;
        }

        @Override
        public int getSeaLevel() {
            return 0;
        }

        @Override
        public Dimension getDimension() {
            return new OverworldDimension();
        }

        @Nullable
        @Override
        public TileEntity getTileEntity(BlockPos blockPos) {
            return null;
        }

        @Override
        public int getMaxLightLevel() {
            return 15;
        }

        @Override
        public IFluidState getFluidState(BlockPos blockPos) {
            return null;
        }
    }
}
