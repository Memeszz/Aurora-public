package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Step extends Module {
    public Step() {
        super("Step", Category.Movement);
    }

    Setting.d height;
    Setting.mode mode;

    @Override
    public void onDisable() {
        mc.player.stepHeight = 0.5f;
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Vanilla");
        mode = registerMode("Mode", "Mode", modes, "Vanilla");
        height = registerD("Height","Height", 2.0, 0.0, 6.0);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player != null || mc.world != null) {
            if (mode.getValue().equals("Vanilla")) {
                mc.player.stepHeight = (float) height.getValue();
            }
        }
    }
}
