package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", Category.Render, "Prevents rendering some things");
    }

    public Setting.b armor;
    Setting.b fire;
    Setting.b blind;
    Setting.b nausea;
    public Setting.b hurtCam;

    public void setup(){
        armor = this.registerB("Armor","Armor", false);
        fire = this.registerB("Fire", "Fire",true);
        blind = this.registerB("Blindness", "Blindness",true);
        nausea = this.registerB("Nausea","Nausea", true);
        hurtCam = this.registerB("HurtCam", "HurtCam",true);
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        if(blind.getValue() && mc.player.isPotionActive(MobEffects.BLINDNESS)) mc.player.removePotionEffect(MobEffects.BLINDNESS);
        if(nausea.getValue() && mc.player.isPotionActive(MobEffects.NAUSEA)) mc.player.removePotionEffect(MobEffects.NAUSEA);
    }

    @Listener
    public void kek(RenderBlockOverlayEvent event) {
        if(fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) event.setCanceled(true);
    }

}
