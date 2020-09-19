package me.memeszz.aurora.module.modules.world;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.event.events.PlayerJoinEvent;
import me.memeszz.aurora.event.events.PlayerLeaveEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.World, "Sends a message when someone joins the server");
        publicChat = this.registerB("Public", "Public", false);
    }

    Setting.b publicChat;

    @Listener
    public void join(PlayerJoinEvent event) {
        if (publicChat.getValue()) mc.player.sendChatMessage(event.getName() + " joined the game");
        else Command.sendClientMessage(event.getName() + " joined the game");
    }

    @Listener
    public void leave(PlayerLeaveEvent event) {
        if (publicChat.getValue()) mc.player.sendChatMessage(event.getName() + " left the game");
        else Command.sendClientMessage(event.getName() + " left the game");
    }

}