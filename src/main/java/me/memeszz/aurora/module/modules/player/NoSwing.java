package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.network.play.client.CPacketAnimation;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoSwing extends Module {
    public NoSwing() {
        super("NoSwing", Category.Player, "Prevents swinging animation server side");
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketAnimation) {
            event.setCanceled(true);
        }
    }
}