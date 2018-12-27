package com.skyresourcesclassic.base.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Particles;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityHeavyExplosiveSnowball extends EntityThrowable {
    public EntityHeavyExplosiveSnowball(World worldIn) {
        super(worldIn);
    }

    public EntityHeavyExplosiveSnowball(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
        this.moveRelative(1, .5f, 1, 0.1F);
    }

    public EntityHeavyExplosiveSnowball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.world.spawnParticle(Particles.ITEM_SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, );
            }
        }
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        if (result.entity != null) {
            int i = 12;

            if (result.entity instanceof EntityBlaze) {
                i = 18;
            }

            result.entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) i);
            result.entity.world.createExplosion(result.entity, result.entity.posX, result.entity.posY,
                    result.entity.posZ, 0.01f, false);
        }

        if (!this.world.isRemote) {
            this.remove();
        }
    }

}
