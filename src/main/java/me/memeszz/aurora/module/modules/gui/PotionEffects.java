package me.memeszz.aurora.module.modules.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.text.DecimalFormat;

public class PotionEffects extends Module {
    public PotionEffects() {
        super("PotionEffects", Category.Gui);
        setDrawn(false);
    }

    int count;
    Color c;
    private Setting.i x;
    private Setting.i y;
    private Setting.b sortUp;
    private Setting.b right;

    DecimalFormat format2 = new DecimalFormat("00");

    public void setup(){
        x = this.registerI("X", "X",100, 0,1000);
        y = this.registerI("Y","Y", 100, 0,1000);
        sortUp = this.registerB("SortUp", "SortUp",true);
        right = this.registerB("AlignRight", "AlignRight",false);
        // get custom font from ClickGuiModule
    }

    public void onRender(){

        count = 0;
        try {
            mc.player.getActivePotionEffects().forEach(effect -> {
                String name = I18n.format(effect.getPotion().getName());
                double duration = effect.getDuration() / 19.99f;
                int amplifier = effect.getAmplifier() + 1;
                int color = effect.getPotion().getLiquidColor();
                double p1 = duration % 60f;
                //double p2 = duration / 60f;
                //double p3 = p2 % 60f;
                //String minutes = format1.format(p3);
                String seconds = format2.format(p1);
                String s = name + " " + amplifier + ChatFormatting.GRAY + " " +  (int) duration / 60 + ":" + seconds;
                if (sortUp.getValue()) {
                    if (right.getValue()) {
                        drawText(s, x.getValue() - getWidth(s), y.getValue() + (count * 10), color);
                    } else {
                        drawText(s, x.getValue(), y.getValue() + (count * 10), color);
                    }
                    count++;
                } else {
                    if (right.getValue()) {
                        drawText(s, x.getValue() - getWidth(s), y.getValue() + (count * -10), color);
                    } else {
                        drawText(s, x.getValue(), y.getValue() + (count * -10), color);
                    }
                    count++;
                }
            });
        } catch(NullPointerException e){e.printStackTrace();}
    }

    private void drawText(String s, int x, int y, int color){
        c = new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(), ClickGuiModule.blue.getValue());
        if (((Hud) ModuleManager.getModuleByName("Hud")).rainbow.getValue()) FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue(), s, x, y, color);
        else FontUtils.drawStringWithShadow(((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue(), s, x, y, color);
    }

    private int getWidth(String s){
        if (((ClickGuiModule) ModuleManager.getModuleByName("ClickGui")).customFont.getValue()) return Aurora.fontRenderer.getStringWidth(s);
         else return mc.fontRenderer.getStringWidth(s);

    }
}
