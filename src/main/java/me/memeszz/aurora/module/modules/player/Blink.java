package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Blink extends Module {
    public Blink() {
        super("Blink", Category.Player, "Cancels most packets");
    }
    EntityOtherPlayerMP entity;
    private final Queue<Packet> packets = new ConcurrentLinkedQueue<>();

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        Packet packet = event.getPacket();
        if (packet instanceof CPacketChatMessage || packet instanceof CPacketConfirmTeleport || packet instanceof CPacketKeepAlive || packet instanceof CPacketTabComplete || packet instanceof CPacketClientStatus) {
            return;
        }
        packets.add(packet);
        event.setCanceled(true);
    }

    public void onEnable() {
        entity = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
        entity.copyLocationAndAnglesFrom(mc.player);
        entity.rotationYaw = mc.player.rotationYaw;
        entity.rotationYawHead = mc.player.rotationYawHead;
        mc.world.addEntityToWorld(666, entity);
    }

    public void onDisable() {
        if (this.entity != null) {
            mc.world.removeEntity(entity);
        }
        if (this.packets.size() > 0) {
            for (Packet packet : this.packets) {
                mc.player.connection.sendPacket(packet);
            }
            this.packets.clear();
        }
    }

    public String getHudInfo(){
        return "\u00A77[\u00A7f" + packets.size() + "\u00A77]";
    }
}