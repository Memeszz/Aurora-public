package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;

import java.util.ArrayList;

public class ESP extends Module {

    public static Setting.mode mode;
    public static Setting.d width;

    public ESP() {
        super("ESP", Category.Render);
    }

    public void setup() {
        width = this.registerD("Width", "Width", 3, 0.1, 10);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("OutLine");
        modes.add("WireFrame");
        mode = this.registerMode("RenderMode", "RenderMode", modes, "WireFrame");
    }

    public String getHudInfo() {
        return "§7[§f" + mode.getValue() + "§7]";
    }
}