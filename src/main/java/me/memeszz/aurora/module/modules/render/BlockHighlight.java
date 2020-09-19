package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", Category.Render, "Highlights the block you're looking at");
    }
    public void setup() {
        red = this.registerI("Red", "Red",255, 0, 255);
        green = this.registerI("Green","Green", 255, 0, 255);
        blue = this.registerI("Blue", "Blue",255, 0, 255);
        alpha = this.registerI("Alpha", "Alpha",255, 0, 255);
    }
    public static Setting.i red;
    public static Setting.i green;
    public static Setting.i blue;
    public static Setting.i alpha;
}