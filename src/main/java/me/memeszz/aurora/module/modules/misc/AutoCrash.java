package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.module.Module;

public class AutoCrash extends Module {
    public AutoCrash() {
        super ("AutoCrash", Category.Misc);
    }



    public void onEnable() {
        mc.player = null;
    }
}
