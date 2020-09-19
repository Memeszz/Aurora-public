package me.memeszz.aurora.module.modules.render;


import io.netty.util.internal.ConcurrentSet;
import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.block.BlockInteractionHelper;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//skidded will rewrite later

public class VoidESP extends Module {
    public VoidESP() {
        super("VoidESP", Category.Render);
    }

    Setting.b rainbow;
    Setting.i radius;
    Setting.i activeYValue;
    Setting.i red;
    Setting.i green;
    Setting.i blue;
    Setting.i alpha;

    Setting.mode renderType;

    public void setup() {
        ArrayList<String> render = new ArrayList<>();
        render.add("Outline");
        render.add("Fill");
        render.add("Both");

        radius = registerI("Radius", "Radius",10, 1, 40);
        activeYValue = registerI("Activate Y","Activate Y", 20, 0, 256);
        red = this.registerI("Red", "Red",255, 0, 255);
        green = this.registerI("Green", "Green",255, 0, 255);
        blue = this.registerI("Blue","Blue",  255, 0, 255);
        alpha = this.registerI("Alpha", "Alpha",50, 0, 255);
        renderType = registerMode("Render", "Render",render, "Outline");
    }

    private ConcurrentSet<BlockPos> voidHoles;

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player.dimension == 1) {
            return;
        }
        if (mc.player.getPosition().getY() > activeYValue.getValue()) {
            return;
        }
        if (voidHoles == null) {
            voidHoles = new ConcurrentSet<>();
        } else {
            voidHoles.clear();
        }

        List<BlockPos> blockPosList = BlockInteractionHelper.getCircle(getPlayerPos(), 0, radius.getValue(), false);

        for (BlockPos blockPos : blockPosList) {
            if (mc.world.getBlockState(blockPos).getBlock().equals(Blocks.BEDROCK)) {
                continue;
            }
            if (isAnyBedrock(blockPos, Offsets.center)) {
                continue;
            }
            voidHoles.add(blockPos);
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        if (mc.player == null || voidHoles == null) {
            return;
        }
        if (mc.player.getPosition().getY() > activeYValue.getValue()) {
            return;
        }
        if (voidHoles.isEmpty()) {
            return;
        }
        for (BlockPos blockPos : voidHoles) {
            RenderUtil.prepare(GL11.GL_QUADS);
            drawBox(blockPos, red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
            RenderUtil.release();
            RenderUtil.prepare(7);
            drawOutline(blockPos, 2, red.getValue(), green.getValue(), blue.getValue());
            RenderUtil.release();
        }
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private boolean isAnyBedrock(BlockPos origin, BlockPos[] offset) {
        for (BlockPos pos : offset) {
            if (mc.world.getBlockState(origin.add(pos)).getBlock().equals(Blocks.BEDROCK)) {
                return true;
            }
        }
        return false;
    }

    private static class Offsets {
        static final BlockPos[] center = {
                new BlockPos(0, 0, 0),
                new BlockPos(0, 1, 0),
                new BlockPos(0, 2, 0)
        };
    }


    private void drawBox(BlockPos blockPos, int value, int r, int g, int b) {
        if (renderType.getValue().equalsIgnoreCase("Fill") || renderType.getValue().equalsIgnoreCase("Both")) {
            Color color;
            color = new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue());
            RenderUtil.drawBox(blockPos, color.getRGB(), RenderUtil.Quad.ALL);
        }
    }

    public void drawOutline(BlockPos blockPos, int width, int r, int g, int b) {
        if (renderType.getValue().equalsIgnoreCase("Outline") || renderType.getValue().equalsIgnoreCase("Both")) {
            final float[] hue = {(System.currentTimeMillis() % (360 * 32)) / (360f * 32)};
            hue[0] += .02f;
            RenderUtil.drawBoundingBoxBlockPos(blockPos, width, r, g, b, 255);
            }
        }
    }
