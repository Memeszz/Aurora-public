package me.memeszz.aurora.module.modules.gui;


import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.setting.Setting;

import java.awt.*;


public class DurabiltyWarning extends Module {
    public DurabiltyWarning() {
        super("DurabiltyWarning", Category.Gui, "Attacks nearby players");
        setDrawn(false);
    }

    private Setting.b rainbow;
    Setting.i x;
    Setting.i y;
    Setting.i red;
    Setting.i green;
    Setting.i blue;
    Setting.i threshold;
    ClickGuiModule mod = ((ClickGuiModule) ModuleManager.getModuleByName("ClickGuiModule"));


    Color c;

    public void setup() {
        threshold = this.registerI("Percent", "Percent",50, 0, 100);
        x = this.registerI("X", "X",255, 0, 960);
        y = this.registerI("Y", "Y",255, 0, 530);
        red = this.registerI("Red","Red", 255, 0, 255);
        green = this.registerI("Green", "Green",255, 0, 255);
        blue = this.registerI("Blue", "Blue",255, 0, 255);
        rainbow = this.registerB("Rainbow", "Rainbow",false);


    }

    @Override
    public void onRender() {
        final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
        int rgb = Color.HSBtoRGB(hue[0], 1, 1);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (this.shouldMend(0) || this.shouldMend(1) || this.shouldMend(2) || this.shouldMend(3)) {
            final String text = "Armor Durability Is Below " + this.threshold.getValue() + "%";
            final int divider = getScale();
            if (rainbow.getValue()) {
                FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), text, x.getValue(),
                        y.getValue(), new Color(r, g, b).getRGB());
            } else {
                FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), text, x.getValue(),
                        y.getValue(), new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB());

            }
        }
    }

    private boolean shouldMend(final int i) {
        return DurabiltyWarning.mc.player.inventory.armorInventory.get(i).getMaxDamage()
                != 0 && 100 * DurabiltyWarning.mc.player.inventory.armorInventory.get(i).getItemDamage()
                / DurabiltyWarning.mc.player.inventory.armorInventory.get(i).getMaxDamage()
                > reverseNumber(this.threshold.getValue(), 1, 100);
    }

    public static int reverseNumber(final int num, final int min, final int max) {
        return max + min - num;
    }

    public static int getScale() {
        int scaleFactor = 0;
        int scale = Wrapper.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        while (scaleFactor < scale && Wrapper.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Wrapper.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        if (scaleFactor == 0) {
            scaleFactor = 1;
        }
        return scaleFactor;
    }
}