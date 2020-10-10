package me.memeszz.aurora.module.modules.gui;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;

public class RainbowOffset extends Module
{
    public static Setting.i offset;

    public RainbowOffset()
    {
        super("RainbowOffset", Category.Gui);
    }

    public void setup()
    {
        offset = registerI("Offset", "Offset", 300, 1, 3000);
    }
}
