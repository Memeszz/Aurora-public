package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.mixin.accessor.ICPacketChatMessage;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class ChatSuffix extends Module {
    public ChatSuffix() {
        super("ChatSuffix", Category.Misc, "Adds a suffix to your messages");
    }

    Setting.b blue;
    Setting.mode mode;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Green");
        modes.add("Blue");
        mode = this.registerMode("Color", "Color", modes, "Green");
        blue = this.registerB("Blue", "Blue",false);
    }

    @Listener
    public void packet(PacketEvent.Send event) {
        if(event.getPacket() instanceof CPacketChatMessage){
            if(((CPacketChatMessage) event.getPacket()).getMessage().startsWith("/") || ((CPacketChatMessage) event.getPacket()).getMessage().startsWith(Command.getPrefix())) return;
            String old = ((CPacketChatMessage) event.getPacket()).getMessage();
            String suffix = " \u23D0 "; //fuck pizzamod
            String s = old + suffix;
            if(blue.getValue()) s = old + "`" + suffix;
            if(s.length() > 255) return;
            ((ICPacketChatMessage) mc).setMessage(s);
        }
    }


}
