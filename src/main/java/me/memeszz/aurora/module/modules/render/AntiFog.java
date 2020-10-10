package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.module.Module;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiFog extends Module {
    public AntiFog() {
        super("AntiFog", Category.Render);
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {

        event.setDensity(0);
        event.setCanceled(true);
    }


    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);

    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);

    }
}