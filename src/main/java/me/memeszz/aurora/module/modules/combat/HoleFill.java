package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.ICPacketPlayer;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.block.BlockInteractionHelper;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.memeszz.aurora.util.entity.EntityUtil.calculateLookAt;

public class HoleFill extends Module {
    public HoleFill() {
        super("HoleFiller", Category.Combat, "Attacks nearby players");
    }
    private Setting.d range;
    private Setting.i smartRange;
    private Setting.b smart;
    private Setting.b toggleOff;
    private Setting.b webs;
    private BlockPos render;
    private Entity closestTarget;
    private int delay = 0;
    public void setup() {
        range = this.registerD("Range", "Range",3, 0.0, 6);
        smartRange = this.registerI("SmartRange", "SmartRange", 3, 0, 6);
        smart = this.registerB("SmartFill", "SmartFill",false);
        toggleOff = this.registerB("ToggleOff","ToggleOff", true);
        webs = this.registerB("Webs", "Webs",true);

    }
    public void onEnable() {
        delay = 0;
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        delay++;
        if (mc.world == null) {
            return;
        }
        if (delay > 6 && toggleOff.getValue()) {
            this.disable();
            delay = 0;
        }
        if (smart.getValue())
            findClosestTarget();
        List<BlockPos> blocks = findCrystalBlocks();
        BlockPos q = null;
        int obsidianSlot = mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)
                ? mc.player.inventory.currentItem
                : -1;
        if (obsidianSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (webs.getValue()) {
                    if (mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.WEB)) {
                        } else {
                    if (mc.player.inventory.getStackInSlot(l).getItem() == Item.getItemFromBlock(Blocks.OBSIDIAN)) {
                        obsidianSlot = l;
                        break;
                        }
                    }
                }
            }
        }
        if (obsidianSlot == -1) {
            return;
        }
        for (BlockPos blockPos : blocks) {
            if (mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockPos)).isEmpty())
                if (smart.getValue() && isInRange(blockPos))
                    q = blockPos;
                else
                    q = blockPos;
        }
        render = q;
        if (q != null && mc.player.onGround) {
            int oldSlot = mc.player.inventory.currentItem;
            if (mc.player.inventory.currentItem != obsidianSlot)
                mc.player.inventory.currentItem = obsidianSlot;
            lookAtPacket(q.getX() + .5, q.getY() - .5, q.getZ() + .5, mc.player);
            BlockInteractionHelper.placeBlockScaffold(render);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.inventory.currentItem = oldSlot;
            resetRotation();
        }
    }

    public void onWorldRender(RenderEvent event) {
        if (render != null) {
            RenderUtil.prepare(GL11.GL_QUADS);
            RenderUtil.drawBox(render, 253, 253, 11, 30, RenderUtil.Quad.ALL);
            RenderUtil.release();
            RenderUtil.prepare(GL11.GL_QUADS);
            RenderUtil.drawBoundingBoxBlockPos(render, 1f, 253, 253, 11, 255);
            RenderUtil.release();
        }
    }


    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private boolean IsHole(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 0, 0);
        BlockPos boost3 = blockPos.add(0, 0, -1);
        BlockPos boost4 = blockPos.add(1, 0, 0);
        BlockPos boost5 = blockPos.add(-1, 0, 0);
        BlockPos boost6 = blockPos.add(0, 0, 1);
        BlockPos boost7 = blockPos.add(0, 2, 0);
        BlockPos boost8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos boost9 = blockPos.add(0, -1, 0);
        return mc.world.getBlockState(boost).getBlock() == Blocks.AIR
                && (mc.world.getBlockState(boost2).getBlock() == Blocks.AIR)
                && (mc.world.getBlockState(boost7).getBlock() == Blocks.AIR)
                && ((mc.world.getBlockState(boost3).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(boost3).getBlock() == Blocks.BEDROCK))
                && ((mc.world.getBlockState(boost4).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(boost4).getBlock() == Blocks.BEDROCK))
                && ((mc.world.getBlockState(boost5).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(boost5).getBlock() == Blocks.BEDROCK))
                && ((mc.world.getBlockState(boost6).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(boost6).getBlock() == Blocks.BEDROCK))
                && (mc.world.getBlockState(boost8).getBlock() == Blocks.AIR)
                && ((mc.world.getBlockState(boost9).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(boost9).getBlock() == Blocks.BEDROCK));
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public BlockPos getClosestTargetPos() {
        if (closestTarget != null) {
            return new BlockPos(Math.floor(closestTarget.posX), Math.floor(closestTarget.posY), Math.floor(closestTarget.posZ));
        } else {
            return null;
        }
    }

    private void findClosestTarget() {

        List<EntityPlayer> playerList = mc.world.playerEntities;

        closestTarget = null;

        for (EntityPlayer target : playerList) {

            if (target == mc.player) {
                continue;
            }

            if (Friends.isFriend(target.getName())) {
                continue;
            }

            if (EntityUtil.isLiving(target)) {
                continue;
            }

            if ((target).getHealth() <= 0) {
                continue;
            }

            if (closestTarget == null) {
                closestTarget = target;
                continue;
            }

            if (mc.player.getDistance(target) < mc.player.getDistance(closestTarget)) {
                closestTarget = target;
            }

        }

    }

    private boolean isInRange(BlockPos blockPos) {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(
                getSphere(getPlayerPos(), (float)range.getValue(), (int)range.getValue(), false, true, 0)
                        .stream().filter(this::IsHole).collect(Collectors.toList()));
        return positions.contains(blockPos);
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> positions = NonNullList.create();
        if (smart.getValue() && closestTarget != null)
            positions.addAll(
                    getSphere(getClosestTargetPos(), (float)smartRange.getValue(),(int) range.getValue(), false, true, 0)
                            .stream().filter(this::IsHole).filter(this::isInRange).collect(Collectors.toList()));
        else if(!smart.getValue())
            positions.addAll(
                    getSphere(getPlayerPos(), (float)range.getValue(), (int)range.getValue(), false, true, 0)
                            .stream().filter(this::IsHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> circleblocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }

    // Better Rotation Spoofing System:

    private static boolean isSpoofingAngles;
    private static double yaw;
    private static double pitch;

    // this modifies packets being sent so no extra ones are made. NCP used to flag
    // with "too many packets"
    private static void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    private static void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.rotationYaw;
            pitch = mc.player.rotationPitch;
            isSpoofingAngles = false;
        }
    }

    @Listener
    public void onUpdate(PacketEvent.Send event) {
        CPacketPlayer packet = (CPacketPlayer) event.getPacket();
        if (packet instanceof CPacketPlayer) {
            if (isSpoofingAngles) {
                ((ICPacketPlayer) packet).setYaw((float) yaw);
                ((ICPacketPlayer) packet).setPitch((float) pitch);
            }
        }
    }

    @Override
    public void onDisable() {
        delay = 0;
        closestTarget = null;
        render = null;
        resetRotation();
    }
}