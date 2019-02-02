package com.skyresourcesclassic.base.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BaseBlock extends Block {

    public BaseBlock(Material material, String name, float hardness, float resistance,
                     SoundType stepSound) {
        super(Block.Builder.create(material).hardnessAndResistance(hardness, resistance).sound(stepSound));
        this.setRegistryName(name);
    }
}
