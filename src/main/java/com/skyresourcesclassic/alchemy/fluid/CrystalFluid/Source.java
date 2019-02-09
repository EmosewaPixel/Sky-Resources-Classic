package com.skyresourcesclassic.alchemy.fluid.CrystalFluid;

import net.minecraft.fluid.IFluidState;

public class Source extends CrystalFluid {
    public Source(int type) {
        super(type);
    }

    @Override
    public boolean isSource(IFluidState state) {
        return true;
    }

    @Override
    public int getLevel(IFluidState p_207192_1_) {
        return 8;
    }
}
