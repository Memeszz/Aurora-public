package me.memeszz.aurora.module.modules.world;

import me.memeszz.aurora.event.events.DestroyBlockEvent;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.PlayerJumpEvent;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Announcer extends Module {
    public Announcer() {
        super("Announcer", Category.World, "Announces what you do in chat");
    }

    public static int blockBrokeDelay = 0;
    static int blockPlacedDelay = 0;
    static int jumpDelay = 0;
    static int attackDelay = 0;
    static int eattingDelay = 0;

    static long lastPositionUpdate;
    static double lastPositionX;
    static double lastPositionY;
    static double lastPositionZ;
    private static double speed;
    String heldItem = "";

    int blocksPlaced = 0;
    int blocksBroken = 0;
    int eaten = 0;

    public static String walkMessage;
    public static String placeMessage;
    public static String jumpMessage;
    public static String breakMessage;
    public static String attackMessage;
    public static String eatMessage;

    public Setting.b clientSide;
    private Setting.b walk;
    private Setting.b place;
    private Setting.b jump;
    private Setting.b breaking;
    private Setting.b attack;
    private Setting.b eat;
    private Setting.d delay;
    private Setting.mode lang;


    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("English");
        modes.add("Finnish");
        modes.add("German");
        lang = registerMode("Language", "Language", modes, "German");
        clientSide = this.registerB("ClientSide", "ClientSide", false);
        walk = this.registerB("Walk", "Walk",true);
        place = this.registerB("BlockPlace", "BlockPlace", false);
        jump = this.registerB("Jump", "Jump", true);
        breaking = this.registerB("BlockBreak", "BlockBreak",false);
        attack = this.registerB("AttackEntity", "AttackEntity", false);
        eat = this.registerB("Eat", "Eat",true);
        delay = this.registerD("DelayMultiplier", "DelayMultiplier",1, 1, 20);
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        blockBrokeDelay++;
        blockPlacedDelay++;
        jumpDelay++;
        attackDelay++;
        eattingDelay++;
        heldItem = mc.player.getHeldItemMainhand().getDisplayName();

        if (lang.getValue().equals("English")) {
            walkMessage = "I just fucking flew {blocks} blocks thanks to the power of Aurora!";
            placeMessage = "I just placed {amount} {name} thanks to power of Aurora!";
            jumpMessage = "I just jumped thanks to the power of Aurora!";
            breakMessage = "I just broke {amount} {name} thanks to Aurora!";
            attackMessage = "I just attacked {name} with a {item} thanks to Aurora!";
            eatMessage = "I just ate {amount] {name} thanks to Aurora!";
        }
        if (lang.getValue().equals("Finnish")) {
            walkMessage = "M\u00E4 just lensin {blocks} metrii, kiitokset Aurora!";
            placeMessage = "M\u00E4 just pistin {amount} {name} blockii, kiitokset Aurora!";
            jumpMessage = "M\u00E4 hyppäsin vitun korkeelle, kiitokset Aurora!";
            breakMessage = "M\u00E4 paskoin {amount} {name}, kiitokset Aurora!";
            attackMessage = "M\u00E4 just pistin {name} p\u00E4\u00E4h\u00E4 {item} avul, kiitokset Aurora!";
            eatMessage = "M\u00E4 just s\u00F6in {amount} {name}, kiitokset Aurora!";
        }
        if (lang.getValue().equals("English")) {
            walkMessage = "Ich bin gerade {blocks} verfickte Blöcke geflogen dank der Kraft von Aurora!";
            placeMessage = "Ich habe gerade {amount} {name} platziert dank der Kraft von Aurora!";
            jumpMessage = "Ich bin gesprungen dank der Kraft von Aurora!";
            breakMessage = "Ich habe gerade {amount} {name} abgebaut dank der Kraft von Aurora!";
            attackMessage = "Ich habe gerade {name} attackiert mit {item} dank Aurora!";
            eatMessage = "Ich habe gerade {amount] {name} dank Aurora gegessen!";
        }

        if (walk.getValue()) {
            if (lastPositionUpdate + (5000L * delay.getValue()) < System.currentTimeMillis()) {

                double d0 = lastPositionX - mc.player.lastTickPosX;
                double d2 = lastPositionY - mc.player.lastTickPosY;
                double d3 = lastPositionZ - mc.player.lastTickPosZ;

                speed = Math.sqrt(d0 * d0 + d2 * d2 + d3 * d3);

                if (!(speed <= 1) && !(speed > 5000)) {
                    String walkAmount = new DecimalFormat("0").format(speed);

                    if (clientSide.getValue()) {
                        Wrapper.sendClientMessage(walkMessage.replace("{blocks}", walkAmount));
                    } else {
                        mc.player.sendChatMessage(walkMessage.replace("{blocks}", walkAmount));
                    }
                    lastPositionUpdate = System.currentTimeMillis();
                    lastPositionX = mc.player.lastTickPosX;
                    lastPositionY = mc.player.lastTickPosY;
                    lastPositionZ = mc.player.lastTickPosZ;
                }
            }
        }

    }

    @Listener
    public void idk(LivingEntityUseItemEvent.Finish event) {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (event.getEntity() == mc.player) {
            if (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold) {
                eaten++;
                if (eattingDelay >= 300 * delay.getValue()) {
                    if (eat.getValue() && eaten > randomNum) {
                        if (clientSide.getValue()) {
                            Wrapper.sendClientMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}", mc.player.getHeldItemMainhand().getDisplayName()));
                        } else {
                            mc.player.sendChatMessage
                                    (eatMessage.replace("{amount}", eaten + "").replace("{name}", mc.player.getHeldItemMainhand().getDisplayName()));
                        }
                        eaten = 0;
                        eattingDelay = 0;
                    }
                }
            }
        }
    }

    @Listener
    public void send(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) {
            blocksPlaced++;
            int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            if (blockPlacedDelay >= 150 * delay.getValue()) {
                if (place.getValue() && blocksPlaced > randomNum) {
                    String msg = placeMessage.replace("{amount}", blocksPlaced + "").replace("{name}", mc.player.getHeldItemMainhand().getDisplayName());
                    if (clientSide.getValue()) {
                        Wrapper.sendClientMessage(msg);
                    } else {
                        mc.player.sendChatMessage(msg);
                    }
                    blocksPlaced = 0;
                    blockPlacedDelay = 0;
                }
            }
        }
    }

    @Listener
    public void setBreaking(DestroyBlockEvent event) {
        blocksBroken++;
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1);
        if (blockBrokeDelay >= 300 * delay.getValue()) {
            if (breaking.getValue() && blocksBroken > randomNum) {
                String msg = breakMessage
                        .replace("{amount}", blocksBroken + "")
                        .replace("{name}", mc.world.getBlockState(event.getBlockPos()).getBlock().getLocalizedName());
                if (clientSide.getValue()) {
                    Wrapper.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                blocksBroken = 0;
                blockBrokeDelay = 0;
            }
        }
    }

    @Listener
    public void setAttack(AttackEntityEvent event) {
        if (attack.getValue() && !(event.getTarget() instanceof EntityEnderCrystal)) {
            if (attackDelay >= 300 * delay.getValue()) {
                String msg = attackMessage.replace("{name}", event.getTarget().getName()).replace("{item}", mc.player.getHeldItemMainhand().getDisplayName());
                if (clientSide.getValue()) {
                    Wrapper.sendClientMessage(msg);
                } else {
                    mc.player.sendChatMessage(msg);
                }
                attackDelay = 0;
            }
        }
    }

    @Listener
    public void setJump(PlayerJumpEvent event) {
        if (jump.getValue()) {
            if (jumpDelay >= 300 * delay.getValue()) {
                if (clientSide.getValue()) {
                    Wrapper.sendClientMessage(jumpMessage);
                } else {
                    mc.player.sendChatMessage(jumpMessage);
                }
                jumpDelay = 0;
            }
        }
    }

    public void onEnable() {
        blocksPlaced = 0;
        blocksBroken = 0;
        eaten = 0;
        speed = 0;
        blockBrokeDelay = 0;
        blockPlacedDelay = 0;
        jumpDelay = 0;
        attackDelay = 0;
        eattingDelay = 0;
    }
}