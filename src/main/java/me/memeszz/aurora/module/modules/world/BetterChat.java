package me.memeszz.aurora.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class BetterChat extends Module {
    public BetterChat() {
        super("BetterChat", Category.World);
    }

    public Setting.b clearBkg;
    Setting.b nameHighlight;
    Setting.b friendHighlight;

    public void setup(){
        clearBkg = this.registerB("Clear", "Clear", true);
        nameHighlight = this.registerB("NameHighlight","NameHighlight", false);
        friendHighlight = this.registerB("FriendHighlight", "FriendHighlight",false);
    }

    @Listener
    public void chat(ClientChatReceivedEvent event) {
        if(mc.player == null) return;
        String name = mc.player.getName().toLowerCase();
        if(friendHighlight.getValue()){
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">")){
                Friends.getFriends().forEach(f -> {
                    if(event.getMessage().getUnformattedText().contains(f.getName())){
                        event.getMessage().setStyle(event.getMessage().getStyle().setColor(TextFormatting.LIGHT_PURPLE));
                    }
                });
            }
        }
        if(nameHighlight.getValue()){
            String s = ChatFormatting.GOLD + "" + ChatFormatting.BOLD + mc.player.getName() + ChatFormatting.RESET;
            Style style = event.getMessage().getStyle();
            if(!event.getMessage().getUnformattedText().startsWith("<"+mc.player.getName()+">") && event.getMessage().getUnformattedText().toLowerCase().contains(name)) {
                event.getMessage().getStyle().setParentStyle(style.setBold(true).setColor(TextFormatting.GOLD));
            }
        }
    }

}
