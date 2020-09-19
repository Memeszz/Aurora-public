package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.event.EventStageable;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.mixin.accessor.ICPacketPlayerTryUseItemOnBlock;
import me.memeszz.aurora.module.Module;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public final class BuildHeight extends Module {
    public BuildHeight() {
        super("BuildHeight", Category.Misc);
    }

    @Listener
    public void sendPacket(PacketEvent event) {
        if(event.getStage() == EventStageable.EventStage.PRE) {
            if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                final CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock) event.getPacket();
                if(packet.getPos().getY() >= 255 && packet.getDirection() == EnumFacing.UP) {
                    ((ICPacketPlayerTryUseItemOnBlock) packet).setPlacedBlockDirection(EnumFacing.DOWN);
                }
            }
        }
    }
}
