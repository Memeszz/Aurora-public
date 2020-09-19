package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class ReverseStep extends Module {
    public ReverseStep() {
        super("ReverseStep", Category.Movement, "Makes you fall faster");
    }

    private Setting.d height;


    public void setup() {
        height = registerD("Height","Height",  2, 1, 15);
    }



    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.isInWater() || mc.player.isInLava() || mc.player.isOnLadder() || mc.gameSettings.keyBindJump.isKeyDown()) {
            return;
        }

        if (mc.player != null && mc.player.onGround && !mc.player.isInWater() && !mc.player.isOnLadder()) {
            for (double y = 0.0; y < this.height.getValue() + 0.5; y += 0.01) {
                if (!mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0.0, -y, 0.0)).isEmpty()) {
                    mc.player.motionY = -10.0;
                    break;
                }
            }
        }
    }
}
