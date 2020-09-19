package me.memeszz.aurora.module.modules.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.TotemPopEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.HashMap;

public class TotemPopCounter extends Module {

    private final Setting.b friend;

    public TotemPopCounter() {
        super("PopCounter", Category.Combat, "Alerts When A Player Pops A Totem");
        friend = this.registerB("AlertFriends","AlertFriends", true);

    }
//e
    private HashMap<String, Integer> popList = new HashMap();

    @Listener
    public void onUpdate(TotemPopEvent event) {
        if (mc.player == null || mc.world == null) {
            return;
        }

        if (popList == null) {
            popList = new HashMap<>();
        }

        if (!friend.getValue()) {
            if (!Friends.isFriend(event.getEntity().getName())) {
                if (popList.get(event.getEntity().getName()) == null) {
                    popList.put(event.getEntity().getName(), 1);
                    Command.sendClientMessage(ChatFormatting.RED + event.getEntity().getName() + " popped " + ChatFormatting.YELLOW + 1 + ChatFormatting.RED + " totem!");
                }

            } else if (!(popList.get(event.getEntity().getName()) == null)) {
                int popCounter = popList.get(event.getEntity().getName());
                int newPopCounter = popCounter += 1;
                popList.put(event.getEntity().getName(), newPopCounter);
                Command.sendClientMessage(ChatFormatting.RED + event.getEntity().getName() + ChatFormatting.RED + " popped " + ChatFormatting.YELLOW + newPopCounter + ChatFormatting.RED + " totems!");
            }
        }

        if (friend.getValue())
            if (popList.get(event.getEntity().getName()) == null) {
                popList.put(event.getEntity().getName(), 1);
                Command.sendClientMessage(ChatFormatting.RED + event.getEntity().getName() + ChatFormatting.RED + " popped " + ChatFormatting.YELLOW + 1 + ChatFormatting.RED + " totem!");
            } else if (!(popList.get(event.getEntity().getName()) == null)) {
                int popCounter = popList.get(event.getEntity().getName());
                int newPopCounter = popCounter += 1;
                popList.put(event.getEntity().getName(), newPopCounter);
                Command.sendClientMessage(ChatFormatting.RED + event.getEntity().getName() + ChatFormatting.RED + " popped " + ChatFormatting.YELLOW + newPopCounter + ChatFormatting.RED + " totems!");
            }
    }

    @Listener
    public void onUpdate() {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.getHealth() <= 0) {
                if (popList.containsKey(player.getName())) {
                    Command.sendClientMessage(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + popList.get(player.getName()) + ChatFormatting.RED + " totems!");
                    popList.remove(player.getName(), popList.get(player.getName()));
                }
            }
        }
    }
    public int getTotemPops(final EntityPlayer player) {
        final Integer pops = this.popList.get(player);
        if (pops == null) {
            return 0;
        }
        return pops;
    }

        public String getTotemPopString ( final EntityPlayer player){
            return "§f" + ((this.getTotemPops(player) <= 0) ? "" : ("-" + this.getTotemPops(player) + " "));
        }


    @Listener
    public void onUpdate(PacketEvent.Receive event) {
        if (mc.world == null || mc.player == null) {
            return;
        }

        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
            if (packet.getOpCode() == 35) {
                Entity entity = packet.getEntity(mc.world);
                Aurora.getInstance().getEventManager().dispatchEvent(new TotemPopEvent(entity));
            }
        }
    }
}
