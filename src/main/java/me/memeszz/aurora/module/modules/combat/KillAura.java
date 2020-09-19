package me.memeszz.aurora.module.modules.combat;


import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.math.MathUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class KillAura extends Module {
    Setting.d range;
    Setting.b criticals;
    Setting.b rotate;
    Setting.mode aimMode;
    boolean rotating;
    public static EntityPlayer target;

    public KillAura() {
        super("KillAura", Category.Combat, "Attacks nearby players");
    }

    @Override
    public void setup() {
        ArrayList<String> aimModes = new ArrayList<>();
        aimModes.add("Leg");
        aimMode = registerMode("Mode","Mode",  aimModes, "Leg");
        boolean swordOnly = true;
        boolean caCheck = true;
        boolean tpsSync = false;
        boolean isAttacking = false;
        this.range = this.registerD("Range", "Range",4.5, 0.0, 10.0);
        this.criticals = this.registerB("Criticals", "Criticals",true);
        this.rotate = this.registerB("Rotate", "Rotate", true);
    }

    public void onDisable() {
        rotating = false;
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player != null || mc.world != null) {
            for (EntityPlayer player : mc.world.playerEntities) {
                if (player != mc.player) {
                    if (mc.player.getDistance(player) < range.getValue()) {
                        if (Friends.isFriend(player.getName())) return;
                        if (player.isDead || player.getHealth() > 0) {
                            if (rotating && rotate.getValue()) {
                                float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), player.getPositionVector());
                                mc.player.rotationYaw = angle[0];
                                switch (aimMode.getValue()) {
                                    case "Leg":
                                        mc.player.rotationPitch = angle[1];
                                        break;
                                }
                            }
                            attackPlayer(player);
                        }
                        target = player;
                    } else {
                        rotating = false;
                    }

                }
            }
        }
    }


    public void attackPlayer(EntityPlayer player) {
        if (player != null) {
            if (player != mc.player) {
                if (mc.player.getCooledAttackStrength(0.0f) >= 1) {
                    rotating = true;
                    mc.playerController.attackEntity(mc.player, player);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
        else {
            rotating = false;
        }
    }
}