package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class SkyColor extends Module {
    public SkyColor() {
        super("SkyColor", Category.Render, "Changes skycolor");
        r = this.registerI("Red","Red",  255, 0, 255);
        g = this.registerI("Green", "Green",0, 0, 255);
        b = this.registerI("Blue", "Blue",255, 0, 255);
        rainbow = registerB("Rainbow", "Rainbow",true);
    }
    Setting.i r;
    Setting.i g;
    Setting.i b;
    Setting.b rainbow;


    @Listener
    public void onUpdate(UpdateEvent event) {
        if (this.isEnabled()) {
        MinecraftForge.EVENT_BUS.register(this);
    } else {
        MinecraftForge.EVENT_BUS.unregister(this);
        }
    }




    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float) r.getValue() / 255f); //colours in java are in decimal between 0 and 1 stupid paster
        event.setGreen((float) g.getValue() / 255f);
        event.setBlue((float) b.getValue() / 255f);
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