package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.List;

//TODO: rewrite
public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", Category.Render, "Shows holes nigga");
    }

    public int radius = 8;
    public final List<Hole> holes = new ArrayList<>();

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null) {
            return;
        }
        this.holes.clear();
        final Vec3i playerPos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = playerPos.getX() - this.radius; x < playerPos.getX() + this.radius; ++x) {
            for (int z = playerPos.getZ() - this.radius; z < playerPos.getZ() + this.radius; ++z) {
                for (int y = playerPos.getY(); y > playerPos.getY() - 4; --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    final IBlockState blockState = mc.world.getBlockState(blockPos);
                    if (this.isBlockValid(blockState, blockPos)) {
                        this.holes.add(new Hole(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                    }
                }
            }
        }
    }

    @Override
    public void onWorldRender(RenderEvent event) {
        for (final Hole hole : this.holes) {
            final AxisAlignedBB bb = new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + (1) - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ);
            if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                if (isBedrockHole(new BlockPos(hole.getX(), hole.getY(), hole.getZ()))) {
                    RenderUtil.drawESP(bb, 0f, 255, 0f, 35F);
                    RenderUtil.drawESPOutline(bb, 0f, 255, 0f, 255f, 1f);
                } else if (isObbyHole(new BlockPos(hole.getX(), hole.getY(), hole.getZ())) || isBothHole(new BlockPos(hole.getX(), hole.getY(), hole.getZ()))) {
                    RenderUtil.drawESP(bb, 255f, 160f, 0f, 35F);
                    RenderUtil.drawESPOutline(bb, 255f, 160f, 0f, 255f, 1f);
                }
            }
        }
    }

    private boolean isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (this.holes.contains(blockPos)) {
            return false;
        }
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (mc.player.getDistanceSq(blockPos) < 1.0) {
            return false;
        }
        if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return false;
        }
        return isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos);
    }

    private boolean isObbyHole(BlockPos blockPos) {
        final BlockPos[] touchingBlocks = {blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.OBSIDIAN)
                return false;
        }
        return true;
    }

    private boolean isBedrockHole(BlockPos blockPos) {
        final BlockPos[] touchingBlocks = {blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || touchingState.getBlock() != Blocks.BEDROCK)
                return false;
        }
        return true;
    }

    private boolean isBothHole(BlockPos blockPos) {
        final BlockPos[] touchingBlocks = {blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west(), blockPos.down()};
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.world.getBlockState(touching);
            if (touchingState.getBlock() == Blocks.AIR || (touchingState.getBlock() != Blocks.BEDROCK && touchingState.getBlock() != Blocks.OBSIDIAN))
                return false;
        }
        return true;
    }



    private class Hole extends Vec3i {
        Hole(final int x, final int y, final int z) {
            super(x, y, z);
        }
    }

}