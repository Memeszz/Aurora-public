package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.mixin.accessor.ICPacketPlayer;
import me.memeszz.aurora.module.Module;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.network.play.client.CPacketPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

/**
 * @author Memeszz_
 */

public class FootXp extends Module {
    public FootXp() {
        super("FootXp", Category.Combat, "Makes you look down serverside while holding xp");
    }

    //todo just realized this originally had a memory leek bullet plz help

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            ((ICPacketPlayer) packet).setPitch(90.0F);
        }
    }
}