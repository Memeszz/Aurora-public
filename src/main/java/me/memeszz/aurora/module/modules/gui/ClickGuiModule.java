package me.memeszz.aurora.module.modules.gui;

import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {
    public ClickGuiModule INSTANCE;

    public static Setting.i red;

    public static Setting.i green;

    public static Setting.i blue;

    public static Setting.i redB;

    public static Setting.i greenB;

    public static Setting.i blueB;

    public static Setting.i alpha;

    public static Setting.b customFont;

    public static Setting.b desc;

    public static Setting.d rainbowSat;

    public static Setting.d rainbowBri;

    public ClickGuiModule() {
        super("ClickGUI", Category.Gui, "Opens the ClickGUI");
        setBind(Keyboard.KEY_P);
        INSTANCE = this;
        red = this.registerI("Red",  "Red",  255, 1, 255);
        green = this.registerI("Green", "Green", 255, 1, 255);
        blue = this.registerI("Blue",  "Blue", 255, 1, 255);
        redB = this.registerI("RedB",  "RedB",0, 0, 255);
        greenB = this.registerI("GreenB", "GreenB",  0, 0, 255);
        blueB = this.registerI("BlueB", "BlueB", 0, 0, 255);
        alpha = this.registerI("Alpha", "Alpha", 254, 0, 255);
        rainbowBri = this.registerD("RainbowBrightness","RainbowBrightness", 1, 0.1, 5.0);
        rainbowSat = registerD("RainbowSaturation", "RainbowSaturation",1, 0.1, 5.0);
        desc = this.registerB("Desc", "Desc",true);
        customFont = this.registerB("CustomFont", "CustomFont",false);
    }

    public void onEnable(){
        mc.displayGuiScreen(Aurora.getInstance().clickGui);
        disable();
    }
}