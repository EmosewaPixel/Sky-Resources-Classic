package com.skyresourcesclassic.technology.block;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.SkyResourcesClassic;
import com.skyresourcesclassic.registry.ModCreativeTabs;
import com.skyresourcesclassic.registry.ModGuiHandler;
import com.skyresourcesclassic.technology.tile.TileHeater;
import com.skyresourcesclassic.technology.tile.TilePoweredHeater;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockHeater extends BlockContainer {

    public static final PropertyBool RUNNING = PropertyBool.create("running");

    public BlockHeater(String material, float hardness, float resistance, int tier) {
        super(Material.IRON);
        this.setTranslationKey(References.ModID + "." + material + "_heater");
        this.setCreativeTab(ModCreativeTabs.tabTech);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setRegistryName(material + "_heater");
        this.setDefaultState(this.blockState.getBaseState().withProperty(RUNNING, false));
        this.tier = tier;
    }

    private int tier;

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (tier > 2)
            return new TilePoweredHeater();
        return new TileHeater();
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

    public int getTier() {
        return tier;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (tier < 3) {
            TileHeater te = (TileHeater) world.getTileEntity(pos);
            te.dropInventory();
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
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

    public int getMetaFromState(IBlockState state) {

        if (state.getValue(RUNNING) == true) {
            return 1;
        }

        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]
                {RUNNING});
    }
}
