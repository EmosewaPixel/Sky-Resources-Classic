package com.skyresourcesclassic.alchemy.fluid.CrystalFluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;

public class Flowing extends CrystalFluid {
    public Flowing(int type) {
        super(type);
    }

    protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> state) {
        super.fillStateContainer(state);
        state.add(new IProperty[]{LEVEL_1_TO_8});
    }

    @Override
    public boolean isSource(IFluidState state) {
        return false;
    }

    @Override
    public int getLevel(IFluidState p_207192_1_) {
        return p_207192_1_.get(LEVEL_1_TO_8);
    }
}