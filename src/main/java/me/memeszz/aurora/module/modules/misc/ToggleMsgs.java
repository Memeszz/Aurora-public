package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.module.Module;

public class ToggleMsgs extends Module {
    public ToggleMsgs() {
        super("ToggleMsgs", Category.Misc, "Sends a client side message when you toggle any module");
        setDrawn(false);
        setEnabled(true);
    }
}
