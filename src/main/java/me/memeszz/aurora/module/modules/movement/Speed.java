package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.PlayerMoveEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.MobEffects;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public class Speed extends Module {
    public Speed() {
        super("Speed", Category.Movement, "Makes you go fast");
    }



    Setting.mode mode;
    Setting.i acceleration;
    Setting.i specialMoveSpeed;
    Setting.b limiter;
    Setting.b limiter2;
    Setting.b potion;
    Setting.i potionSpeed1;
    Setting.i potionSpeed2;

    public void setup() {
        acceleration = registerI("Accel","Accel", 2149, 1000, 2500);
        specialMoveSpeed = registerI("Speed","Speed", 100, 0, 150);
        limiter = registerB("Limiter", "Limiter",true);
        limiter2 = registerB("Limiter 2","Limiter 2",  true);
        potion = registerB("Speed", "Speed",true);
        potionSpeed1 = registerI("Speed1", "Speed1",130, 0, 150);
        potionSpeed2 = registerI("Speed2", "Speed2",125, 0, 150);
    }

    private int stage;
    private int cooldownHops;
    private final int ticks = 0;
    private double moveSpeed, lastDist;

    public static double defaultSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getMinecraft().player.isPotionActive(MobEffects.SPEED)) {
            int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
            baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
        }
        return baseSpeed;
    }

    public void onEnable() {
        if (mc.player != null) {
            moveSpeed = defaultSpeed();
        }
        lastDist = 0.0;
        stage = 2;
    }



    @Listener
    public void onMove(PlayerMoveEvent event) {
        if (mc.player != null) {
            float moveForward = mc.player.movementInput.moveForward;
            float moveStrafe = mc.player.movementInput.moveStrafe;
            float rotationYaw = mc.player.rotationYaw;
            if (this.limiter2.getValue() && mc.player.onGround) {
                this.stage = 2;
            }
            if (this.limiter.getValue() && round(mc.player.posY - (int)mc.player.posY, 3) == round(0.138, 3)) {
                final EntityPlayerSP player = mc.player;
                player.motionY -= 0.13;
                event.setY(event.getY() - 0.13);
                final EntityPlayerSP player2 = mc.player;
                player2.posY -= 0.13;
            }
            if (this.stage == 1 && EntityUtil.isMoving()) {
                this.stage = 2;
                this.moveSpeed = this.getMultiplier() * getBaseMoveSpeed() - 0.01;
            }
            else if (this.stage == 2) {
                this.stage = 3;
                if (EntityUtil.isMoving()) {
                    event.setY(mc.player.motionY = 0.4);
                    if (this.cooldownHops > 0) {
                        --this.cooldownHops;
                    }
                    this.moveSpeed *= this.acceleration.getValue() / 1000.0;
                }
            }
            else if (this.stage == 3) {
                this.stage = 4;
                final double difference = 0.66 * (this.lastDist - getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(0D, mc.player.motionY, 0D)).size() > 0 || (mc.player.collidedVertically)) {
                    this.stage = 1;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
            if (moveForward == 0.0f && moveStrafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
                this.moveSpeed = 0.0;
            }
            else if (moveForward != 0.0f) {
                if (moveStrafe >= 1.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? -45.0f : 45.0f);
                    moveStrafe = 0.0f;
                }
                else if (moveStrafe <= -1.0f) {
                    rotationYaw += ((moveForward > 0.0f) ? 45.0f : -45.0f);
                    moveStrafe = 0.0f;
                }
                if (moveForward > 0.0f) {
                    moveForward = 1.0f;
                }
                else if (moveForward < 0.0f) {
                    moveForward = -1.0f;
                }
            }
            final double motionX = Math.cos(Math.toRadians(rotationYaw + 90.0f));
            final double motionZ = Math.sin(Math.toRadians(rotationYaw + 90.0f));
            if (this.cooldownHops == 0) {
                event.setX(moveForward * this.moveSpeed * motionX + moveStrafe * this.moveSpeed * motionZ);
                event.setZ(moveForward * this.moveSpeed * motionZ - moveStrafe * this.moveSpeed * motionX);
            }
            mc.player.stepHeight = 0.6f;
            if (moveForward == 0.0f && moveStrafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
            }
        }
    }



    private float getMultiplier() {
        float baseSpeed = this.specialMoveSpeed.getValue();
        if (this.potion.getValue() && mc.player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1;
            if (amplifier >= 2) {
                baseSpeed = this.potionSpeed2.getValue();
            }
            else {
                baseSpeed = this.potionSpeed1.getValue();
            }
        }
        return baseSpeed / 100.0f;
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.272;

        if (mc.player != null) {
            if (mc.player.isPotionActive(MobEffects.SPEED)) {
                int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                baseSpeed *= (1D + 0.2D * (amplifier + 1));
            }
        }

        return baseSpeed;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bigDecimal = new BigDecimal(value).setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }


}

