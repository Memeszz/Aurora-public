package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class PortalGodMode extends Module {
    public PortalGodMode() {
        super("PortalGodmode", Category.Player, "Godmode when you go through a portal");
    }


    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if(event.getPacket() instanceof CPacketConfirmTeleport)
            event.setCanceled(true);
    }
}
