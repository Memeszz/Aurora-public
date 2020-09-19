package me.memeszz.aurora.module.modules.world;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AutoGG extends Module {
    public static AutoGG INSTANCE;
    public AutoGG() {
        super("AutoGG", Category.World, "Sends a message in chat when you kill someone");
        INSTANCE = this;
    }

    Setting.mode mode;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Get fuck by aurora");
        modes.add("Get fuck by nutgod");
        modes.add("GG, {name}");
        mode = this.registerMode("Mode","Mode", modes, "GG, {name}");

    }
    static List<String> AutoGgMessages = new ArrayList<>();
    private ConcurrentHashMap targetedPlayers = null;
    int index = -1;

    @Listener
    public void send(PacketEvent.Send event) {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            if (event.getPacket() instanceof CPacketUseEntity) {
                CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
                if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK)) {
                    Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);
                    if (targetEntity instanceof EntityPlayer) {
                        this.addTargetedPlayer(targetEntity.getName());
                    }
                }
            }
        }
    }

    @Listener
    public void e(LivingDeathEvent event) {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            EntityLivingBase entity = event.getEntityLiving();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    if (player.getHealth() <= 0.0F) {
                        String name = player.getName();
                        if (this.shouldAnnounce(name)) {
                            this.doAnnounce(name);
                        }

                    }
                }
            }
        }
    }

    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap();
    }

    public void onDisable() {
        this.targetedPlayers = null;
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0.0F) {
                    String name = player.getName();
                    if (this.shouldAnnounce(name)) {
                        this.doAnnounce(name);
                        break;
                    }
                }
            }
        }



            targetedPlayers.forEach((namex, timeout) -> {
                if ((int)timeout <= 0) {
                    this.targetedPlayers.remove(namex);
                } else {
                    this.targetedPlayers.put(namex, (int)timeout - 1);
                }

            });
    }

    private boolean shouldAnnounce(String name) {
        return this.targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {
        targetedPlayers.remove(name);
        if (index >= (AutoGgMessages.size() - 1)) index = -1;
        index++;
        String message;
        if (AutoGgMessages.size() > 0)
            message = AutoGgMessages.get(index);
        if (mode.getValue().equals("Get fuck by aurora")) {
            message = "GET FUCK BY AURORA PUSSY!!!";
        }
        if (mode.getValue().equals("Get fuck by nutgod")) {
            message = "GET FUCK BY NUTGOD PUSSY!!!";
        }
        else {
            message = "GG, " + name + " :^)";
        }

        String messageSanitized = message.replaceAll("ยง", "").replace("{name}", name);
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }

        mc.player.connection.sendPacket(new CPacketChatMessage(messageSanitized));
    }

    public void addTargetedPlayer(String name) {
        if (!Objects.equals(name, mc.player.getName())) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            targetedPlayers.put(name, 20);
        }
    }


    public static void addAutoGgMessage(String s){
        AutoGgMessages.add(s);
    }

    public static List<String> getAutoGgMessages(){
        return AutoGgMessages;
    }
}
