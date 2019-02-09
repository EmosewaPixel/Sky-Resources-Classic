package com.skyresourcesclassic.events;

import com.skyresourcesclassic.ConfigOptions;
import com.skyresourcesclassic.InfoToast;
import com.skyresourcesclassic.References;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = References.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) {
            EntityPlayer player = event.player;
            if (ConfigOptions.guide.allowGuide.get()
                    && Minecraft.getInstance().player != null
                    && Minecraft.getInstance().player.getGameProfile().getId().equals(player.getGameProfile().getId())
                    && player.ticksExisted > 100 && player.ticksExisted < 150
                    && Minecraft.getInstance().getToastGui().getToast(InfoToast.class, InfoToast.Type.Info) == null) {
                Minecraft.getInstance().getToastGui().add(new InfoToast(new TextComponentString("Sky Resources Guide"),
                        new TextComponentString("Press " + TextFormatting.AQUA + "Open Guide Key (G)"), 5000));
            }
        }
    }
}
