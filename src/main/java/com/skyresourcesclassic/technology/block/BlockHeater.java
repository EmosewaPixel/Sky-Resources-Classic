package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.TileHeater;
import com.skyresourcesclassic.technology.tile.TilePoweredHeater;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;

import java.util.ArrayList;
import java.util.List;

public class BlockHeater extends BlockContainer {

    public static final BooleanProperty RUNNING = BooleanProperty.create("running");

    public BlockHeater(String material, float hardness, float resistance, int tier) {
        super(Block.Builder.create(Material.IRON).hardnessAndResistance(hardness, resistance));
        this.setRegistryName(material + "_heater");
        this.setDefaultState(this.stateContainer.getBaseState().with(RUNNING, false));
        this.tier = tier;
    }

    private int tier;

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        if (tier > 2)
            return new TilePoweredHeater(tier);
        return new TileHeater(tier);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        switch (tier) {
            case 1:
                return Material.WOOD;
            default:
                return Material.IRON;
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tileEntity, ItemStack stack) {
        if (tier < 3) {
            TileHeater te = (TileHeater) world.getTileEntity(pos);
            te.dropInventory();
        }

        super.harvestBlock(world, player, pos, state, tileEntity, stack);
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World world, BlockPos pos,
                                    EntityPlayer player, EnumHand hand, EnumFacing side, float hitX,
                                    float hitY, float hitZ) {
        if (!world.isRemote) {

            if (tier < 3) {
                player.openGui(SkyResourcesClassic.instance, ModGuiHandler.HeaterGUI, world, pos.getX(), pos.getY(),
                        pos.getZ());
            } else {
                if (player.getHeldItemMainhand().isEmpty() && !player.isSneaking()) {
                    List<ITextComponent> toSend = new ArrayList();

                    TilePoweredHeater tile = (TilePoweredHeater) world.getTileEntity(pos);
                    toSend.add(new TextComponentString(TextFormatting.RED + "FE Stored: " + tile.getEnergyStored()
                            + " / " + tile.getMaxEnergyStored()));

                    for (ITextComponent text : toSend) {
                        player.sendMessage(text);
                    }

                }
            }
        }
        return true;
    }

    protected void fillStateContainer(net.minecraft.state.StateContainer.Builder<Block, IBlockState> builder) {
        builder.add(new IProperty[]{RUNNING});
    }
}
