package com.skyresourcesclassic.technology.tile;

import com.skyresourcesclassic.base.tile.TileItemInventory;
import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.registry.ModBlocks;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileEndPortalCore extends TileItemInventory implements ITickable {

    public TileEndPortalCore() {
        super("end_portal_core", 1, null, new Integer[]{0});
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            if (hasValidMultiblock()) {
                spawnFish();

                if (receivedPulse()) {
                    List<EntityPlayerMP> list = world.getEntitiesWithinAABB(EntityPlayerMP.class, new AxisAlignedBB(
                            pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2F, pos.getZ() + 1));

                    for (EntityPlayerMP player : list) {
                        if (!getInventory().getStackInSlot(0).isEmpty()
                                && getInventory().getStackInSlot(0).isItemEqual(new ItemStack(Items.ENDER_EYE))
                                && getInventory().getStackInSlot(0).getCount() >= 16) {
                            if (player.dimension == 0) {
                                player.changeDimension(1);
                                getInventory().getStackInSlot(0).shrink(16);
                                if (getInventory().getStackInSlot(0).getCount() == 0)
                                    getInventory().setStackInSlot(0, ItemStack.EMPTY);
                            }
                        }
                    }
                }
                updateRedstone();
            }
            this.markDirty();
        }
    }

    private void spawnFish() {
        List<EntitySilverfish> list = world.getEntitiesWithinAABB(EntitySilverfish.class, new AxisAlignedBB(
                pos.getX() - 4, pos.getY(), pos.getZ() - 4, pos.getX() + 4, pos.getY() + 5F, pos.getZ() + 4));
        if (!ConfigOptions.general.endWussMode && world.rand.nextInt(90) == 0 && list.size() < 16) {
            EntitySilverfish fish = new EntitySilverfish(world);
            fish.setDropChance(EntityEquipmentSlot.MAINHAND, 0);
            fish.setDropChance(EntityEquipmentSlot.HEAD, 0);
            fish.setDropChance(EntityEquipmentSlot.CHEST, 0);
            fish.setDropChance(EntityEquipmentSlot.LEGS, 0);
            fish.setDropChance(EntityEquipmentSlot.FEET, 0);
            ItemStack sword = new ItemStack(Items.IRON_SWORD);
            sword.addEnchantment(Enchantments.FIRE_ASPECT, 1);
            sword.addEnchantment(Enchantments.SHARPNESS, 3);
            fish.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, sword);
            fish.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
            fish.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
            fish.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
            fish.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
            fish.setPosition(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
            world.spawnEntity(fish);
        }
    }

    public boolean hasValidMultiblock() {
        BlockPos[] goldBlockPoses = new BlockPos[]{pos.north(), pos.south(), pos.west(), pos.east()};
        for (BlockPos pos : goldBlockPoses) {
            if (world.getBlockState(pos).getBlock() != Blocks.GOLD_BLOCK)
                return false;
        }
        BlockPos[] endBlockPoses = new BlockPos[]{pos.north(2).west(2), pos.north(2).east(2), pos.south(2).west(2),
                pos.south(2).east(2)};
        for (BlockPos pos : endBlockPoses) {
            if (world.getBlockState(pos.up()).getBlock() != Blocks.END_BRICKS)
                return false;
            if (world.getBlockState(pos.up(2)).getBlock() != Blocks.END_BRICKS)
                return false;
            if (world.getBlockState(pos.up(3)).getBlock() != Blocks.GLOWSTONE)
                return false;
        }
        BlockPos[] diamBlockPoses = new BlockPos[]{pos.north().west(), pos.north().east(), pos.south().west(),
                pos.south().east()};
        for (BlockPos pos : diamBlockPoses) {
            if (world.getBlockState(pos).getBlock() != Blocks.DIAMOND_BLOCK)
                return false;
        }

        for (int x = pos.getX() - 2; x < pos.getX() + 3; x++) {
            for (int z = pos.getZ() - 2; z < pos.getZ() + 3; z++) {
                if (Math.abs(pos.getX() - x) > 1 || Math.abs(pos.getZ() - z) > 1) {

                    if (world.getBlockState(new BlockPos(x, pos.getY(), z)).getBlock() != ModBlocks.darkMatterBlock)
                        return false;
                }
            }
        }
        return true;
    }
}
