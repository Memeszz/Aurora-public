package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;

public class EntityESP extends Module {

    public EntityESP() {
        super("EntityESP", Category.Render);
    }

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Outline");
        modes.add("Wireframe");
        player = registerB("Players", "Players", true);
        skeleton = registerB("Skeleton", "Skeleton",true);
        exp = registerB("ExpBottles", "ExpBottles", false);
        epearls = registerB("EnderPearls", "EnderPearls",false);
        items = registerB("Items", "Items",false);
        orbs = registerB("ExpOrbs", "ExpOrbs",false);
        mode = this.registerMode("PlayerEspMode","PlayerEspMode", modes, "Both");
    }

    public static Setting.mode mode;
    public static Setting.b player;
    Setting.b exp;
    Setting.b epearls;
    Setting.b items;
    Setting.b orbs;
    Setting.b skeleton;

    @Listener
    public void onWorldRender(RenderEvent event) {
        for (Entity e : mc.world.loadedEntityList) {
            if (e != mc.player) {
                if (e instanceof EntityExpBottle) {
                    RenderUtil.prepare(GL11.GL_QUADS);
                    RenderUtil.drawBox(e.getRenderBoundingBox(), new Color(255, 255, 255, 150).getRGB(), RenderUtil.Quad.ALL);
                    RenderUtil.release();
                }
            }
        }

    }

    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";
    }
}