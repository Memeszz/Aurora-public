package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Category.Movement, "Prevents you from taking knockback");
    }

    @Listener
    public void listener(PacketEvent.Receive event) {
        if(event.getPacket() instanceof SPacketEntityVelocity){
            if(((SPacketEntityVelocity) event.getPacket()).getEntityID() == mc.player.getEntityId())
                event.setCanceled(true);
        }
        if(event.getPacket() instanceof SPacketExplosion)
            event.setCanceled(true);
    }
}
