package me.memeszz.aurora.module.modules.render;


import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Swing extends Module {
    public Swing() {
        super("Swing", Category.Render, "Swing With Your OffHand");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null)
            return;

            mc.player.swingingHand = EnumHand.OFF_HAND;

    }
}
