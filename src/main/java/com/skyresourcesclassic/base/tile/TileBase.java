package com.skyresourcesclassic.base.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileBase extends TileEntity {
    private String name;

    public TileBase(String name) {
        super(TileEntityType.FURNACE);
        this.name = name;
    }

    public ITextComponent getDisplayName() {
        return new TextComponentTranslation("container.skyresourcesclassic." + name);
    }

    private int redstoneSignal, prevRedstoneSignal;

    public boolean receivedPulse() {
        boolean pulse = getRedstoneSignal() > 0 && prevRedstoneSignal == 0;
        return pulse;
    }

    private boolean canAcceptRedstone() {
        return true;
    }

    public int getRedstoneSignal() {

        updateRedstone();
        return redstoneSignal;
    }

    public void updateRedstone() {
        if (!world.isRemote) {

            prevRedstoneSignal = redstoneSignal + 0;
            redstoneSignal = 0;
            if (canAcceptRedstone()) {
                for (EnumFacing dir : EnumFacing.values()) {
                    int redstoneSide = getWorld().getRedstonePower(getPos().offset(dir), dir);
                    redstoneSignal = Math.max(redstoneSignal, redstoneSide);
                }
            }
        }
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.write(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        this.read(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        write(nbtTagCompound);
        return nbtTagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.read(tag);
    }

    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isRemote)
            world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), world.getBlockState(getPos()), 0);
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);

        compound.setInt("pSignal", prevRedstoneSignal);
        compound.setInt("signal", redstoneSignal);
        return compound;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        prevRedstoneSignal = compound.getInt("pSignal");
        redstoneSignal = compound.getInt("signal");
    }
}
