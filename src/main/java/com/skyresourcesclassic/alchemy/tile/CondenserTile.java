package com.skyresourcesclassic.alchemy.tile;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.alchemy.fluid.FluidCrystalBlock;
import com.skyresourcesclassic.alchemy.fluid.FluidRegisterInfo;
import com.skyresourcesclassic.base.HeatSources;
import com.skyresourcesclassic.base.tile.TileBase;
import com.skyresourcesclassic.registry.ModBlocks;
import com.skyresourcesclassic.registry.ModEntities;
import com.skyresourcesclassic.registry.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Particles;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.Random;

public class CondenserTile extends TileBase implements ITickable {
    public CondenserTile(int tier) {
        super("condenser", ModEntities.condeserType(tier));
        this.tier = tier;
    }

    private int timeCondense;
    private int tier;

    @Override
    public void tick() {
        updateRedstone();
        crystalFluidUpdate();
    }

    private void crystalFluidUpdate() {
        Random rand = world.rand;
        Block block = getBlockAbove();
        if (block instanceof FluidCrystalBlock && getRedstoneSignal() == 0) {
            FluidCrystalBlock crystalBlock = (FluidCrystalBlock) block;
            String type = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(crystalBlock)].name;
            FluidRegisterInfo.CrystalFluidType fluidType = ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                    .indexOf(crystalBlock)].type;

            String tagCheck = "forge:ingots/" + type;

            if ((tier != 1 || fluidType == FluidRegisterInfo.CrystalFluidType.NORMAL) && crystalBlock.getFluidState(getBlockAbove().getDefaultState()).isSource()
                    && crystalBlock.isNotFlowing(world, pos.up(), world.getBlockState(pos.up()))
                    && new ItemTags.Wrapper(new ResourceLocation(tagCheck)).getAllElements().size() > 0
                    && HeatSources.isValidHeatSource(pos.down(), world)) {
                this.world.spawnParticle(Particles.SMOKE, pos.getX() + rand.nextFloat(),
                        pos.getY() + 1.5D, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
                if (!world.isRemote)
                    timeCondense += HeatSources.getHeatSourceValue(pos.down(), world);
            } else if (!world.isRemote)
                timeCondense = 0;

            if (timeCondense >= getTimeToCondense(crystalBlock)) {
                world.removeBlock(pos.up());
                Item item = new ItemTags.Wrapper(new ResourceLocation(tagCheck)).getAllElements().iterator().next();
                BlockPos p = getSpawnPos();
                Entity entity = new EntityItem(world, p.getX() + 0.5F, p.getY() + 0.5F, p.getZ() + 0.5F, new ItemStack(item));
                world.spawnEntity(entity);
                timeCondense = 0;
            }
        }
    }


    private BlockPos getSpawnPos() {
        BlockPos[] poses = new BlockPos[]{pos.add(-1, 0, 0), pos.add(1, 0, 0), pos.add(0, 0, -1), pos.add(0, 0, 1)};
        for (BlockPos p : poses) {
            TileEntity t = world.getTileEntity(p);
            if (t.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent())
                return p;
        }
        return pos.add(0, 1, 0);
    }

    private int getTimeToCondense(FluidCrystalBlock block) {
        return (int) (ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks.indexOf(block)].rarity
                * ConfigOptions.condenser.condenserProcessTimeBase.get()
                * (ModFluids.crystalFluidInfos()[ModBlocks.crystalFluidBlocks
                .indexOf(block)].type == FluidRegisterInfo.CrystalFluidType.NORMAL ? 1 : 20)
                * (1F / tier));
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("time", timeCondense);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);

        timeCondense = compound.getInt("time");
    }

    private Block getBlockAbove() {
        return getWorld().getBlockState(pos.add(0, 1, 0)).getBlock();
    }
}
