package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.technology.block.BlockCombustionHeater;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;

public class TileCombustionHeater extends TileItemInventory implements ITickable {
    public TileCombustionHeater(int tier) {
        super("combustionHeater", ModEntities.combustionHeaterType(tier), 1, null, new Integer[]{0});
        this.tier = tier;
    }

    public int currentHeatValue;
    public int fuelBurnTime;
    private int heatPerTick;
    public int currentItemBurnTime;
    private int tier;

    public int getMaxHeat() {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockCombustionHeater))
            return 0;
        BlockCombustionHeater block = (BlockCombustionHeater) world.getBlockState(pos).getBlock();

        return block.getMaximumHeat();
    }

    private int getMaxHeatPerTick() {
        if (!(world.getBlockState(pos).getBlock() instanceof BlockCombustionHeater))
            return 0;

        switch (tier) {
            case 1:
                return 8;
            default:
                return 16;
        }
    }

    private int getBurnTime(ItemStack stack) {
        int burnTime = stack.getBurnTime();
        return ForgeEventFactory.getItemBurnTime(stack, burnTime == -1 ? TileEntityFurnace.getBurnTimes().getOrDefault(stack, 0) : burnTime);
    }

    private int getHeatPerTick(ItemStack stack) {
        int fuelTime = getBurnTime(stack);
        if (fuelTime > 0) {
            return (int) Math.cbrt((float) fuelTime * ConfigOptions.combustion.combustionHeatMultiplier.get());
        }

        return 0;
    }

    private int getFuelBurnTime(ItemStack stack) {
        if ((float) getHeatPerTick(stack) <= 0)
            return 0;

        return (int) ((float) Math.pow(getBurnTime(stack), 0.75F) / getHeatPerTick(stack));
    }

    private boolean isValidFuel(ItemStack stack) {
        return !(getBurnTime(stack) <= 0 || getHeatPerTick(stack) <= 0
                || getHeatPerTick(stack) > getMaxHeatPerTick() || getFuelBurnTime(stack) <= 0);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("heat", currentHeatValue);
        compound.setInt("fuel", fuelBurnTime);
        compound.setInt("item", currentItemBurnTime);
        compound.setInt("hpt", heatPerTick);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        currentHeatValue = compound.getInt("heat");
        fuelBurnTime = compound.getInt("fuel");
        currentItemBurnTime = compound.getInt("item");
        heatPerTick = compound.getInt("hpt");
    }

    @Override
    public void tick() {
        if (receivedPulse() && hasValidMultiblock()) {
            craftItem();
        }
        updateRedstone();

        if (fuelBurnTime > 0) {
            this.fuelBurnTime--;
        } else
            this.currentItemBurnTime = this.heatPerTick = this.fuelBurnTime = 0;

        if (!this.world.isRemote) {
            if (fuelBurnTime > 0 || this.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
                if (fuelBurnTime == 0 && currentHeatValue < getMaxHeat()
                        && isValidFuel(getInventory().getStackInSlot(0))) {
                    this.currentItemBurnTime = this.fuelBurnTime = getFuelBurnTime(getInventory().getStackInSlot(0));
                    heatPerTick = getHeatPerTick(getInventory().getStackInSlot(0));

                    if (fuelBurnTime > 0) {
                        if (this.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
                            this.getInventory().getStackInSlot(0).shrink(1);

                            if (this.getInventory().getStackInSlot(0).getCount() == 0) {
                                this.getInventory().setStackInSlot(0, getInventory().getStackInSlot(0).getItem()
                                        .getContainerItem(getInventory().getStackInSlot(0)));
                            }
                        }
                    }
                }

                if (fuelBurnTime > 0 && currentHeatValue < getMaxHeat()) {
                    currentHeatValue += heatPerTick;
                }
            }

            if (!hasValidMultiblock()) {
                if (currentHeatValue > 0)
                    currentHeatValue--;
            }

            if (currentHeatValue > getMaxHeat())
                currentHeatValue = getMaxHeat();

            this.markDirty();
        }
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
            this.world.spawnParticle(Particles.EXPLOSION, pos.getX(), pos.getY() + 1.5D, pos.getZ(),
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

                    currentHeatValue *= tier == 1 ? 0.7F : 0.85F;

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
    }

    private ProcessRecipe recipeToCraft() {
        List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX(),
                pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));

        List<Object> items = new ArrayList<>();

        for (EntityItem i : list) {
            items.add(i.getItem());
        }

        return ProcessRecipeManager.combustionRecipes.getMultiRecipe(items, currentHeatValue, true,
                true);
    }
}
