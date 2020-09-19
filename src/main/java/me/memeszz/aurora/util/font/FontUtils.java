package me.memeszz.aurora.util.font;

import me.memeszz.aurora.Aurora;
import net.minecraft.client.Minecraft;

public class FontUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, int color){
        if(customFont) return Aurora.fontRenderer.drawStringWithShadow(text, x, y, color);
        else return mc.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public static float drawString(boolean customFont, String text, int x, int y, int color){
        if(customFont) return Aurora.fontRenderer.drawString(text, x, y, color);
        else return mc.fontRenderer.drawString(text, x, y, color);
    }

    public static int getStringWidth(boolean customFont, String str){
        if(customFont) return Aurora.fontRenderer.getStringWidth(str);
        else return mc.fontRenderer.getStringWidth(str);
    }



    public static int getFontHeight(boolean customFont){
        if(customFont) return Aurora.fontRenderer.getHeight();
        else return mc.fontRenderer.FONT_HEIGHT;
    }
}
