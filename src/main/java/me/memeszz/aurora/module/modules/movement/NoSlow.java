package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.module.Module;
import net.minecraftforge.client.event.InputUpdateEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class NoSlow extends Module {
    public NoSlow() {
        super("NoSlow", Category.Movement, "Prevents item use form slowing you down");
    }

    @Listener
    public void onUpdate(InputUpdateEvent event) {
        if (mc.player.isHandActive() && !mc.player.isRiding()) {
            event.getMovementInput().moveStrafe *= 5;
            event.getMovementInput().moveForward *= 5;
        }
    }

}