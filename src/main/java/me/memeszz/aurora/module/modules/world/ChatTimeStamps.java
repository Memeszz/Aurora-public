package me.memeszz.aurora.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatTimeStamps extends Module {
    public ChatTimeStamps() {
        super("ChatTimeStamps", Category.World);
        ArrayList<String> formats = new ArrayList<>();
        formats.add("H24:mm");
        formats.add("H12:mm");
        formats.add("H12:mm a");
        formats.add("H24:mm:ss");
        formats.add("H12:mm:ss");
        formats.add("H12:mm:ss a");
        ArrayList<String> deco = new ArrayList<>();
        deco.add("< >"); deco.add("[ ]");
        deco.add("{ }"); deco.add(" ");
        ArrayList<String> colors = new ArrayList<>();
        for(ChatFormatting cf : ChatFormatting.values()){
            colors.add(cf.getName());
        }

        format = this.registerMode("Format", "Format",formats, "H12:mm");
        color = this.registerMode("Color","Color",  colors, ChatFormatting.AQUA.getName());
        decoration =  this.registerMode("Deco","Deco", deco, "< >");
        space = this.registerB("Space", "Space",true);
    }

    Setting.mode format;
    Setting.mode color;
    Setting.mode decoration;
    Setting.b space;

    @Listener
    public void chat(ClientChatReceivedEvent event) {
        String decoLeft = decoration.getValue().equalsIgnoreCase(" ") ? "" : decoration.getValue().split(" ")[0];
        String decoRight = decoration.getValue().equalsIgnoreCase(" ") ? "" : decoration.getValue().split(" ")[1];
        if(space.getValue()) decoRight += " ";
        String dateFormat = format.getValue().replace("H24", "k").replace("H12", "h");
        String date = new SimpleDateFormat(dateFormat).format(new Date());
        TextComponentString time = new TextComponentString(ChatFormatting.getByName(color.getValue()) + decoLeft + date + decoRight + ChatFormatting.RESET);
        event.setMessage(time.appendSibling(event.getMessage()));
    }

    

}
