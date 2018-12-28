package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.tile.TileGenericPower;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.technology.block.CombustionHeaterBlock;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TilePoweredCombustionHeater extends TileGenericPower implements ITickable {

    public TilePoweredCombustionHeater(int tier) {
        super("combustionHeaterPowered", 100000, 2000, 0);
        this.tier = tier;
    }

    public int currentHeatValue;
    private int heatPerTick = 20;
    private int powerUsage = 800;
    private int tier;

    public int getMaxHeat() {
        if (!(world.getBlockState(pos).getBlock() instanceof CombustionHeaterBlock))
            return 0;
        CombustionHeaterBlock block = (CombustionHeaterBlock) world.getBlockState(pos).getBlock();

        return block.getMaximumHeat();
    }

    @Override
    public void tick() {
        if (receivedPulse() && hasValidMultiblock()) {
            craftItem();
        }

        if (!this.world.isRemote) {
            updateRedstone();
            heatPerTick = 40 * (tier - 2);
            powerUsage = 400 * (tier - 1);
            if (getEnergyStored() >= powerUsage && currentHeatValue < getMaxHeat()) {
                internalExtractEnergy(powerUsage, false);
                currentHeatValue += heatPerTick;
            }

            if (!hasValidMultiblock()) {
                if (currentHeatValue > 0)
                    currentHeatValue -= 2;
            }

            if (currentHeatValue < 0)
                currentHeatValue = 0;

            if (currentHeatValue > getMaxHeat())
                currentHeatValue = getMaxHeat();
            this.markDirty();
        }
    }

    public boolean hasValidMultiblock() {
        return isBlockValid(pos.up(), pos.add(-1, 1, 0))
                && isBlockValid(pos.up(), pos.add(1, 1, 0))
                && isBlockValid(pos.up(), pos.add(0, 2, 0))
                && isBlockValid(pos.up(), pos.add(0, 1, -1))
                && isBlockValid(pos.up(), pos.add(0, 1, 1))
                && world.isAirBlock(pos.up());
    }

    private boolean isBlockValid(BlockPos center, BlockPos pos) {
        BlockPos dir = center.subtract(pos);
        return world.getBlockState(pos).getBlockFaceShape(world, pos,
                EnumFacing.getFacingFromVector(dir.getX(), dir.getY(), dir.getZ())) == BlockFaceShape.SOLID;
    }

    private void craftItem() {
        ProcessRecipe recipe = recipeToCraft();
        if (recipe != null) {
            this.world.spawnParticle(Particles.
                            EXPLOSION, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
                    0.0D, 0.0D, 0.0D);
            this.world.playSound(null, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
                    SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F,
                    (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

            if (!world.isRemote) {
                List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
                        pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));
                ItemStack item1 = list.get(0).getItem();
                ItemStack item2 = ItemStack.EMPTY;
                for (Object recStack : recipe.getInputs()) {
                    if (item1.isItemEqual((ItemStack) recStack))
                        item2 = (ItemStack) recStack;
                }

                int timesToCraft = (int) Math.floor((float) item1.getCount() / (float) item2.getCount());

                for (int times = 0; times < timesToCraft; times++) {
                    if (currentHeatValue < recipe.getIntParameter())
                        break;
                    List<ItemStack> inputs = new ArrayList<>();
                    for (Object o : recipe.getInputs())
                        inputs.add(((ItemStack) o).copy());
                    for (EntityItem item : list) {
                        ItemStack stack = item.getItem();
                        for (ItemStack i2 : inputs) {
                            int count = Math.min(i2.getCount(), stack.getCount());
                            if (stack.isItemEqual(i2)) {
                                stack.shrink(count);
                                i2.shrink(count);
                            }
                        }
                        for (int i2 = inputs.size() - 1; i2 >= 0; i2--) {
                            if (inputs.get(i2).isEmpty())
                                inputs.remove(i2);
                        }
                    }

                    currentHeatValue *= 0.95F;

                    ItemStack stack = recipe.getOutputs().get(0).copy();

                    TileCombustionCollector collector = getCollector();
                    if (collector != null) {
                        for (int i = 0; i < 5; i++) {
                            if (!stack.isEmpty())
                                stack = collector.getInventory().insertItem(i, stack, false);
                            else
                                break;
                        }
                    }
                    if (!stack.isEmpty()) {
                        Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F,
                                stack);
                        world.spawnEntity(entity);
                    }
                }
            }
        }
        if (!world.isRemote)
            this.markDirty();
    }

    private TileCombustionCollector getCollector() {
        BlockPos[] poses = new BlockPos[]{pos.add(-1, 1, 0), pos.add(1, 1, 0), pos.add(0, 1, -1), pos.add(0, 1, 1),
                pos.add(0, 2, 0)};
        for (BlockPos p : poses) {
            TileEntity t = world.getTileEntity(p);
            if (t instanceof TileCombustionCollector)
                return (TileCombustionCollector) t;
        }
        return null;
    }

    private ProcessRecipe recipeToCraft() {
        List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
                pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));

        List<Object> items = new ArrayList<>();

        for (EntityItem i : list) {
            items.add(i.getItem());
        }

        ProcessRecipe recipe = ProcessRecipeManager.combustionRecipes.getMultiRecipe(items, currentHeatValue, true,
                true);

        return recipe;
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        compound = super.write(compound);

        compound.setInt("heat", currentHeatValue);

        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        currentHeatValue = compound.getInt("heat");
    }

    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
}
