package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.EventStageable;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketParticles;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoLag extends Module {
    public NoLag() {
        super ("NoLag", Category.Misc);
    }

    @Listener
    public void onPacketRec(PacketEvent.Receive event) {
        if (event.getStage() == EventStageable.EventStage.PRE) {
            if (event.getPacket() instanceof SPacketParticles || event.getPacket() instanceof SPacketEffect) {
                event.setCanceled(true);
            }

        }
    }


}
