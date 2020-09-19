package me.memeszz.aurora.util;


import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.util.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;


public class Wrapper {
     private static CFontRenderer fontRenderer;
     public static final Aurora mod;
     private static String prefix;
     public static void init() { fontRenderer = Aurora.fontRenderer; }
     public static final Minecraft mc;

     public static volatile Wrapper INSTANCE = new Wrapper();

   public static Minecraft getMinecraft() { return Minecraft.getMinecraft(); }

     public Minecraft mc() {
         return Minecraft.getMinecraft();
     }



    public static Entity getRenderEntity() {
        return mc.getRenderViewEntity();
    }

   public static EntityPlayerSP getPlayer() { return (getMinecraft()).player; }


   public static World getWorld() { return (getMinecraft()).world; }


    static {
         mc = Minecraft.getMinecraft();
         mod = Aurora.getInstance();
         prefix = ChatFormatting.DARK_AQUA + "[" + ChatFormatting.AQUA + "Aurora" + ChatFormatting.DARK_AQUA + "] ";
     }
     public static void sendClientMessage(final String message) {
     if (mc.player == null) {
         return;
     }
     final ITextComponent itc = new TextComponentString(prefix + ChatFormatting.GRAY + message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Aurora"))));
     mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
 }
   public static CFontRenderer getFontRenderer() { return fontRenderer; }
}
