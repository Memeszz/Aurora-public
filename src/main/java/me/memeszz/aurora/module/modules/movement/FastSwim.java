package me.memeszz.aurora.module.modules.movement;


import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.util.math.MathHelper;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class FastSwim extends Module {


    private Setting.b up;
    private Setting.b water;
    private Setting.b lava;
    private Setting.b sprint;
    private Setting.b only2b;
    private Setting.b down;



    public FastSwim() {
        super("FastSwim", Category.Movement, "Allows The Player To Swim Faster Horizontally and Vertically");
    }

    public void setup() {
        up = this.registerB("FastSwimUp", "FastSwimUp",true);
        down = this.registerB("FastSwimDown", "FastSwimDown",true);
        water = this.registerB("StrafeWater", "StrafeWater",false);
        lava = this.registerB("StrafeLava", "StrafeLava",true);
        sprint = this.registerB("AutoSprintInLiquid", "AutoSprintInLiquid",true);
        only2b = this.registerB("Only2b","Only2b", true);

    }
    int divider = 5;

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (only2b.getValue()) {
            if (!mc.isSingleplayer()) {
                if ( ((IMinecraft) mc).getCurrentServerData().serverIP.equalsIgnoreCase("2b2t.org")) {

                    if (sprint.getValue()) {
                        if (mc.player.isInLava() || mc.player.isInWater()) {
                            mc.player.setSprinting(true);
                        }
                    }

                    if (mc.player.isInWater() || mc.player.isInLava()) {
                        if (mc.gameSettings.keyBindJump.isKeyDown() && up.getValue()) {
                            mc.player.motionY = .725 / divider;
                        }
                    }
                    if (mc.player.isInWater() && water.getValue() && !mc.player.onGround) {
                        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                            final float yaw = GetRotationYawForCalc();
                            mc.player.motionX -= MathHelper.sin(yaw) * .25 / 10;
                            mc.player.motionZ += MathHelper.cos(yaw) * .25 / 10;
                        }
                    }

                    if (mc.player.isInLava() && lava.getValue() && !mc.player.onGround) {
                        if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                            final float yaw = GetRotationYawForCalc();
                            mc.player.motionX -= MathHelper.sin(yaw) * .7 / 10;
                            mc.player.motionZ += MathHelper.cos(yaw) * .7 / 10;
                        }
                    }


                    if (mc.player.isInWater() && down.getValue()) {
                        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                            int divider2 = divider * -1;
                            mc.player.motionY = 2.2 / divider2;
                        }
                    }
                    if (mc.player.isInLava() && down.getValue()) {
                        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                            int divider2 = divider * -1;
                            mc.player.motionY = .91 / divider2;
                        }
                        //
                    }
                    if (!mc.player.isInWater() && !mc.player.isInLava()) {
                        mc.player.motionX -= 0;
                        mc.player.motionZ += 0;
                    }
                }
            }
        }
        ///
        if (!only2b.getValue()) {
            if (sprint.getValue()) {
                if (mc.player.isInLava() || mc.player.isInWater()) {
                    mc.player.setSprinting(true);
                }
            }

            if (mc.player.isInWater() || mc.player.isInLava()) {
                if (mc.gameSettings.keyBindJump.isKeyDown() && up.getValue()) {
                    mc.player.motionY = .725 / divider;
                }
            }
            if (mc.player.isInWater() && water.getValue()) {
                if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                    final float yaw = GetRotationYawForCalc();
                    mc.player.motionX -= MathHelper.sin(yaw) * .2 / 10;
                    mc.player.motionZ += MathHelper.cos(yaw) * .2 / 10;
                }
            }

            if (mc.player.isInLava() && lava.getValue() && !mc.player.onGround) {
                if (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
                    final float yaw = GetRotationYawForCalc();
                    mc.player.motionX -= MathHelper.sin(yaw) * .7 / 10;
                    mc.player.motionZ += MathHelper.cos(yaw) * .7 / 10;
                }
            }

            if (mc.player.isInWater() && down.getValue() && !mc.player.onGround) {
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    int divider2 = divider * -1;
                    mc.player.motionY = 2.2 / divider2;
                }
            }
            if (mc.player.isInLava() && down.getValue()) {
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    int divider2 = divider * -1;
                    mc.player.motionY = .91 / divider2;
                }
            }
            if (!mc.player.isInWater() && !mc.player.isInLava()) {
                mc.player.motionX -= 0;
                mc.player.motionZ += 0;
            }
        }
    }
    private float GetRotationYawForCalc() {
        float rotationYaw = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
}

