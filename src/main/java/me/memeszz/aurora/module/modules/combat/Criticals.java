package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Criticals extends Module {
    public Criticals() {
        super("Criticals", Category.Combat, "Increases chance for a critical hit");
    }

    Setting.mode mode;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Packet");
        modes.add("Jump");
        mode = this.registerMode("Mode","Mode", modes, "Packet");
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        if ((mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
            if (event.getPacket() instanceof CPacketUseEntity) {
                if (((CPacketUseEntity) event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && mc.player.onGround) {
                    if (mode.getValue().equals("Jump")) {
                        mc.player.jump();
                    }
                    if (mode.getValue().equals("Packet")) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.1f, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, false));
                    }
                }
            }
        }
    }


    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";
    }
}