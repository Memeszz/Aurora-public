package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", Category.Movement, "Automatically sprint");
    }

    Setting.mode mode;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Legit");
        modes.add("Rage");
        mode = this.registerMode("Mode","Mode", modes, "Rage");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (mc.world != null) {
            mc.player.setSprinting(false);
        }
    }

    @Override
        public String getHudInfo() {
            return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";
        }


    @Listener
    public void onUpdate(UpdateEvent event) {
        switch (mode.getValue()) {
            case "Rage":
                if ((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())
                        && !(mc.player.isSneaking()) && !(mc.player.collidedHorizontally) && !(mc.player.getFoodStats().getFoodLevel() <= 6f)) {
                    mc.player.setSprinting(true);
                }
                break;
            case "Legit":
                if ((mc.gameSettings.keyBindForward.isKeyDown()) && !(mc.player.isSneaking()) && !(mc.player.isHandActive()) && !(mc.player.collidedHorizontally) && mc.currentScreen == null
                        && !(mc.player.getFoodStats().getFoodLevel() <= 6f)) {
                    mc.player.setSprinting(true);
                }
                break;
        }
    }

}
