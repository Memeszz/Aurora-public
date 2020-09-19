package me.memeszz.aurora.module.modules.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class AntiVoid extends Module {

    public AntiVoid() {
        super("AntiVoid", Category.Player, "Attacks nearby players");
    }
    Setting.mode mode;
//ez
    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Bounce");
        modes.add("Mini");
        modes.add("Dolphin");
        mode = this.registerMode("Mode", "Mode",modes, "Bounce");
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        double yLevel = mc.player.posY;
        if (yLevel <= .5) {
            Wrapper.sendClientMessage(("\u00A7aAttempting To Get ") + ChatFormatting.RED + mc.player.getName() + ChatFormatting.GREEN + " Out Of The void!");
            if (mode.getValue().equals("Bounce")) {
                mc.player.moveVertical = 10;
                mc.player.jump();
            }
            if (mode.getValue().equals("Mini")) {
                mc.player.moveVertical = 5;
                mc.player.jump();
            }
            if (mode.getValue().equals("Dolphin")) {
                mc.player.moveVertical = 2;
                mc.player.jump();
            }
            event.setY(event.getY() + 0.10984509);
        }
        else {
            mc.player.moveVertical = 0;
        }
    }

    @Override
    public void onDisable() {
        mc.player.moveVertical = 0;
    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";
    }
}