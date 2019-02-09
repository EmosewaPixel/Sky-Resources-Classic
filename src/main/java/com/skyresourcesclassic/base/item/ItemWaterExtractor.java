package com.skyresourcesclassic.base.item;

import com.skyresourcesclassic.recipe.ProcessRecipe;
import com.skyresourcesclassic.recipe.ProcessRecipeManager;
import com.skyresourcesclassic.registry.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnowLayer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
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
        tank = new FluidTank(new FluidStack(Fluids.WATER, 0), maxAmount);
        setRegistryName("water_extractor");
    }

    @Override
    public EnumAction getUseAction(ItemStack stack) {
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
                //Vec3d vec3d2 = vec3d.scale(5);// vec3d1 * 5;
                Vec3d vec3d2 = vec3d.add(vec3d1.x * 5, vec3d1.y * 5, vec3d1.z * 5);
                RayTraceResult rayTrace = world.rayTraceBlocks(vec3d, vec3d2, RayTraceFluidMode.SOURCE_ONLY, false, true);
                if (rayTrace != null) {
                    BlockPos pos = rayTrace.getBlockPos();
                    EnumFacing blockHitSide = rayTrace.sideHit;
                    Block block = world.getBlockState(pos).getBlock();
                    ProcessRecipe recipe = ProcessRecipeManager.waterExtractorExtractRecipes.getRecipe(
                            new ItemStack(block, 1), 0, false, false);
                    if (recipe != null) {
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                for (int z = -1; z <= 1; z++) {
                                    BlockPos blockPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                                    Block radiusBlock = world.getBlockState(blockPos).getBlock();
                                    if (block == radiusBlock) {
                                        ProcessRecipe radiusBlockRecipe = ProcessRecipeManager.waterExtractorExtractRecipes.getRecipe(
                                                new ItemStack(radiusBlock, 1), 0, false, false);
                                        if (radiusBlockRecipe != null) {
                                            IBlockState recipeOut = Block.getBlockFromItem(radiusBlockRecipe.getOutputs().get(0).getItem())
                                                    .getDefaultState();
                                            int newAmount = 0;
                                            if (getCompound(stack).getInt("amount") + tank.fill(radiusBlockRecipe.getFluidOutputs().get(0).copy(), true) <= maxAmount)
                                                newAmount = getCompound(stack).getInt("amount") +
                                                        tank.fill(radiusBlockRecipe.getFluidOutputs().get(0).copy(), true);
                                            else newAmount = maxAmount;
                                            getCompound(stack).setInt("amount", newAmount);
                                            tank.getFluid().amount = getCompound(stack).getInt("amount");
                                            world.setBlockState(blockPos, radiusBlockRecipe.getOutputs().get(0) == ItemStack.EMPTY
                                                    ? Blocks.AIR.getDefaultState() : recipeOut, 3);
                                        }
                                    }
                                }
                            }
                        }
                        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL,
                                1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
                        return;
                    }
                    if (world.getBlockState(pos.add(blockHitSide.getDirectionVec())) == Blocks.WATER.getDefaultState()
                            && getCompound(stack).getInt("amount") < maxAmount) {
                        world.removeBlock(pos.add(blockHitSide.getDirectionVec()));
                        int newAmount = 0;
                        if (getCompound(stack).getInt("amount") + 1000 <= maxAmount)
                            newAmount = getCompound(stack).getInt("amount") + 1000;
                        else newAmount = maxAmount;
                        getCompound(stack).setInt("amount", newAmount);
                        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL,
                                1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
                    }
                }
            }
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemUseContext context) {
        IBlockState blockState = context.getWorld().getBlockState(context.getPos());
        Block block = blockState.getBlock();
        ItemStack stack = context.getItem();
        tank.setFluid(new FluidStack(Fluids.WATER, getCompound(stack).getInt("amount")));
        if (context.getWorld().getTileEntity(context.getPos()) instanceof IFluidHandler) {
            IFluidHandler tile = (IFluidHandler) context.getWorld().getTileEntity(context.getPos());
            getCompound(stack).setInt("amount", stack.getTag().getInt("amount") - tile.fill(tank.getFluid(), true));
            tank.getFluid().amount = getCompound(stack).getInt("amount");
            return EnumActionResult.SUCCESS;
        }

        ProcessRecipe recipe = ProcessRecipeManager.waterExtractorInsertRecipes.getRecipe(
                new ArrayList<Object>(Arrays.asList(new ItemStack(block, 1),
                        tank.getFluid().copy())), 0, false, false);
        if (recipe != null) {
            IBlockState recipeOut = Block.getBlockFromItem(recipe.getOutputs().get(0).getItem()).getDefaultState();
            context.getWorld().setBlockState(context.getPos(), recipeOut, 3);
            getCompound(stack).setInt("amount",
                    stack.getTag().getInt("amount") - recipe.getFluidInputs().get(0).amount);
            tank.getFluid().amount = getCompound(stack).getInt("amount");
            context.getWorld().playSound((EntityPlayer) null, context.getPlayer().posX, context.getPlayer().posY, context.getPlayer().posZ, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.NEUTRAL,
                    1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F));
            return EnumActionResult.SUCCESS;
        }

        //Placement of water
        if (getCompound(stack).getInt("amount") >= 1000 && context.getPlayer().isSneaking()) {
            EnumFacing side = context.getFace();
            BlockPos pos = context.getPos();
            if (block == Blocks.SNOW && blockState.get(BlockSnowLayer.LAYERS).intValue() < 1) {
                side = EnumFacing.UP;
            } else if (!block.isReplaceable(context.getWorld().getBlockState(context.getPos()), new BlockItemUseContext(context))) {
                pos = context.getPos().offset(context.getFace());
            }

            if (!context.getPlayer().canPlayerEdit(context.getPos(), side, stack) || stack.getCount() == 0) {
                return EnumActionResult.FAIL;
            } else {
                if (FluidUtil.tryPlaceFluid(context.getPlayer(), context.getWorld(), pos, tank, tank.getFluid())) {

                    getCompound(stack).setInt("amount", getCompound(stack).getInt("amount") - 1000);
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
