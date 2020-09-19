package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IEntity;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;


public class FastWebFall extends Module {
    public FastWebFall() {
        super("FastFallWeb", Category.Player);
    }

    Setting.mode mode;
    Setting.i speed;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("2b");
        modes.add("Non2b");
        mode = this.registerMode("Mode","Mode", modes, "2b");
        speed = registerI("Speed","Speed", 10, 1, 100);
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (((IEntity) mc.player).getIsInWeb()) {
            if (mode.getValue().equalsIgnoreCase("non2b")) {
                for (int i = 0; i < speed.getValue(); i++) {
                    mc.player.motionY--;
                }
            } else
                mc.player.motionY = 1.1 / -5;
        }
    }
}
