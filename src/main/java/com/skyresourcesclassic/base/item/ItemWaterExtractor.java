package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemWaterExtractor extends Item implements IFluidHandler {
    public static final int maxAmount = 4000;

    private FluidTank tank;

    public static final String[] extractorIcons = new String[]{"empty", "full1", "full2", "full3", "full4", "full5",
            "full6"};

    public ItemWaterExtractor() {
        super(new Item.Builder().maxStackSize(1).group(ModItemGroups.tabMain));
        tank = new FluidTank(new FluidStack(FluidRegistry.WATER, 0), maxAmount);
        setRegistryName("water_extractor");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 70000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return new ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (!world.isRemote && timeLeft <= getMaxItemUseDuration(stack) - 25) {
                Vec3d vec3d = player.getPositionVector().add(0, player.getEyeHeight(), 0);
                Vec3d vec3d1 = player.getLookVec();
                Vec3d vec3d2 = vec3d.add(vec3d1.x * 5, vec3d1.y * 5, vec3d1.z * 5);
                RayTraceResult rayTrace = world.rayTraceBlocks(vec3d, vec3d2, false, false, true);

                if ((rayTrace != null)) {
                    BlockPos pos = rayTrace.getBlockPos();

                    EnumFacing blockHitSide = rayTrace.sideHit;

                    Block block = world.getBlockState(pos).getBlock();

                    ProcessRecipe recipe = ProcessRecipeManager.waterExtractorExtractRecipes.getRecipe(
                            new ItemStack(block), 0, false, false);

                    if (recipe != null) {
                        IBlockState recipeOut = Block.getBlockFromItem(recipe.getOutputs().get(0).getItem())
                                .getDefaultState();
                        getCompound(stack).setInt("amount", getCompound(stack).getInt("amount")
                                + tank.fill(recipe.getFluidOutputs().get(0).copy(), true));
                        tank.getFluid().amount = getCompound(stack).getInt("amount");
                        world.setBlockState(pos, recipe.getOutputs().get(0) == ItemStack.EMPTY
                                ? Blocks.AIR.getDefaultState() : recipeOut, 3);
                        world.playSound(null, player.posX, player.posY, player.posZ,
                                SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
                                1.0F / (random.nextFloat() * 0.4F + 1.2F));
                        return;
                    }
                    if (world.getBlockState(pos.add(blockHitSide.getDirectionVec())) == Blocks.WATER.getDefaultState()
                            && getCompound(stack).getInt("amount") < maxAmount) {
                        world.removeBlock(pos.add(blockHitSide.getDirectionVec()));
                        getCompound(stack).setInt("amount", getCompound(stack).getInt("amount") + 1000);
                        world.playSound(null, player.posX, player.posY, player.posZ,
                                SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
                                1.0F / (random.nextFloat() * 0.4F + 1.2F));
                    }

                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World world, BlockPos pos, EnumHand hand, EnumFacing side,
                                      float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();
        ItemStack stack = playerIn.getHeldItem(hand);
        tank.setFluid(new FluidStack(FluidRegistry.WATER, getCompound(stack).getInt("amount")));
        if (world.getTileEntity(pos) instanceof IFluidHandler) {
            IFluidHandler tile = (IFluidHandler) world.getTileEntity(pos);
            getCompound(stack).setInt("amount",
                    stack.getTag().getInt("amount") - tile.fill(tank.getFluid(), true));
            tank.getFluid().amount = getCompound(stack).getInt("amount");
            return EnumActionResult.SUCCESS;
        }

        ProcessRecipe recipe = ProcessRecipeManager.waterExtractorInsertRecipes.getRecipe(new ArrayList<>(
                        Arrays.asList(new ItemStack(block),
                                tank.getFluid().copy())),
                0, false, false);

        if (recipe != null) {
            IBlockState recipeOut = Block.getBlockFromItem(recipe.getOutputs().get(0).getItem())
                    .getDefaultState();
            world.setBlockState(pos, recipeOut, 3);
            getCompound(stack).setInt("amount",
                    stack.getTag().getInt("amount") - recipe.getFluidInputs().get(0).amount);
            tank.getFluid().amount = getCompound(stack).getInt("amount");
            world.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ,
                    SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 1.2F));

            return EnumActionResult.SUCCESS;
        }

        // Place water
        if (getCompound(stack).getInt("amount") >= 1000 && playerIn.isSneaking()) {
            if (block == Blocks.SNOW && iblockstate.get(BlockSnow.LAYERS).intValue() < 1) {
                side = EnumFacing.UP;
            } else if (!block.isReplaceable(world, pos)) {
                pos = pos.offset(side);
            }

            if (!playerIn.canPlayerEdit(pos, side, stack) || stack.getCount() == 0) {
                return EnumActionResult.FAIL;
            } else {
                if (world.mayPlace(Blocks.WATER, pos, false, side, null)) {
                    IBlockState iblockstate1 = Blocks.WATER.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0,
                            playerIn);

                    world.setBlockState(pos, iblockstate1, 3);
                    getCompound(stack).setInt("amount", getCompound(stack).getInt("amount") - 1000);
                    tank.getFluid().amount = getCompound(stack).getInt("amount");
                    world.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ,
                            SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL, 1.0F,
                            1.0F / (random.nextFloat() * 0.4F + 1.2F));
                    return EnumActionResult.SUCCESS;
                }

                return EnumActionResult.FAIL;
            }
        }
        return EnumActionResult.FAIL;
    }

    private NBTTagCompound getCompound(ItemStack stack) {
        NBTTagCompound com = stack.getTag();
        if (com == null)
            onCreated(stack, null, null);
        com = stack.getTag();

        return com;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        itemStack.setTag(new NBTTagCompound());
        itemStack.getTag().setInt("amount", 0);
        tank.getFluid().amount = itemStack.getTag().getInt("amount");

    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return tank.getTankProperties();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource != null) {
            int filled = tank.fill(resource, doFill);

            return filled;
        }

        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource != null) {
            return tank.drain(resource.amount, doDrain);
        }

        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    public FluidTank getTank() {
        return tank;
    }

    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.getTag() != null) {
            tooltip.add(new TextComponentString("Water: " + stack.getTag().getInt("amount") + " mB"));
        } else
            tooltip.add(new TextComponentString("Water: 0 mB"));
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
