package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.block.BlockInteractionHelper;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.memeszz.aurora.module.modules.combat.Surround.canBeClicked;
import static me.memeszz.aurora.module.modules.combat.Surround.faceVectorPacketInstant;

public class AutoWeb extends Module {
    public AutoWeb() {
        super("AutoWeb", Category.Combat, "Webs Players");
    }

    private Setting.d range;
    private Setting.b rotate;
    private Setting.i tickDelay;
    private Setting.i blocksPerTick;
    Setting.mode mode;

    public void setup() {
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Full");
        modes.add("Feet");
        modes.add("Body");
        mode = this.registerMode("Mode", "Mode", modes, "Feet");
        range = this.registerD("Range", "Range",4.5, 0.0, 6.0);
        blocksPerTick = this.registerI("BPT", "BPT",4, 0, 10);
        tickDelay = this.registerI("TimeoutTicks", "TimeoutTicks", 1, 0, 10);
        rotate = this.registerB("Rotate", "Rotate", true);
    }
    private EntityPlayer closestTarget;
    private String lastTickTargetName;
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int delayStep = 0;
    private boolean isSneaking = false;
    private int offsetStep = 0;
    private boolean firstRun;

    @Override
    protected void onEnable() {

        if (mc.player == null) {
            this.disable();
            return;
        }

        firstRun = true;

        playerHotbarSlot = mc.player.inventory.currentItem;
        lastHotbarSlot = -1;

    }

    @Override
    protected void onDisable() {

        if (mc.player == null) {
            return;
        }

        if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
            mc.player.inventory.currentItem = playerHotbarSlot;
        }

        if (isSneaking) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            isSneaking = false;
        }

        playerHotbarSlot = -1;
        lastHotbarSlot = -1;


    }

    @Listener
    public void onUpdate(UpdateEvent event) {

        if (mc.player == null) {
            return;
        }


        if (!firstRun) {
            if (delayStep < tickDelay.getValue()) {
                delayStep++;
                return;
            } else {
                delayStep = 0;
            }
        }

        findClosestTarget();

        if (closestTarget == null) {
            if (firstRun) {
                firstRun = false;

            }
            return;
        }
        if (firstRun) {
            firstRun = false;
            lastTickTargetName = closestTarget.getName();

        } else if (!lastTickTargetName.equals(closestTarget.getName())) {
            lastTickTargetName = closestTarget.getName();
            offsetStep = 0;
        }

        List<Vec3d> placeTargets = new ArrayList<>();

        if(mode.getValue().equalsIgnoreCase("Full")) {
            Collections.addAll(placeTargets, Offsets.FULL);
        }

        if(mode.getValue().equalsIgnoreCase("Feet")) {
            Collections.addAll(placeTargets, Offsets.FEET);
        }

        if(mode.getValue().equalsIgnoreCase("Body")) {
            Collections.addAll(placeTargets, Offsets.BODY);
        }

        // TODO: dont use static bridging in offset but calculate them on the fly
        //  based on view direction (or relative direction of target to player)
        //  (add full/half n/e/s/w patterns to append dynamically)

        // TODO: sort offsetList by optimal caging success factor ->
        // sort them by pos y up AND start building behind target

        int blocksPlaced = 0;

        while (blocksPlaced < blocksPerTick.getValue()) {

            if (offsetStep >= placeTargets.size()) {
                offsetStep = 0;
                break;
            }

            BlockPos offsetPos = new BlockPos(placeTargets.get(offsetStep));
            BlockPos targetPos = new BlockPos(closestTarget.getPositionVector()).down().add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());

            if (placeBlockInRange(targetPos, range.getValue())) {
                blocksPlaced++;
            }

            offsetStep++;

        }

        if (blocksPlaced > 0) {

            if (lastHotbarSlot != playerHotbarSlot && playerHotbarSlot != -1) {
                mc.player.inventory.currentItem = playerHotbarSlot;
                lastHotbarSlot = playerHotbarSlot;
            }

            if (isSneaking) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                isSneaking = false;
            }

        }

    }

    private boolean placeBlockInRange(BlockPos pos, double range) {

        // check if block is already placed
        Block block = mc.world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
            return false;
        }

        EnumFacing side = BlockInteractionHelper.getPlaceableSide(pos);

        // check if we have a block adjacent to blockpos to click at
        if (side == null) {
            return false;
        }

        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();

        // check if neighbor can be right clicked
        if (!canBeClicked(neighbour)) {
            return false;
        }

        Vec3d hitVec = new Vec3d(neighbour).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
        Block neighbourBlock = mc.world.getBlockState(neighbour).getBlock();

        if (mc.player.getPositionVector().distanceTo(hitVec) > range) {
            return false;
        }

        int obiSlot = findObiInHotbar();

        if (obiSlot == -1) {
            this.disable();
        }

        if (lastHotbarSlot != obiSlot) {
            mc.player.inventory.currentItem = obiSlot;
            lastHotbarSlot = obiSlot;
        }

        if (!isSneaking && BlockInteractionHelper.blackList.contains(neighbourBlock) || BlockInteractionHelper.shulkerList.contains(neighbourBlock)) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
            isSneaking = true;
        }

        if (rotate.getValue()) {
            faceVectorPacketInstant(hitVec);
        }

        mc.playerController.processRightClickBlock(mc.player, mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        mc.player.swingArm(EnumHand.MAIN_HAND);

        return true;

    }

    private int findObiInHotbar() {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block instanceof BlockWeb) {
                slot = i;
                break;
            }

        }

        return slot;

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

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";

    }


    private static class Offsets {

        private static final Vec3d[] FULL = {
                new Vec3d(0, 2, 0),
                new Vec3d(0, 1, 0),

        };

        private static final Vec3d[] FEET = {
                new Vec3d(0, 1, 0),
        };

        private static final Vec3d[] BODY = {
                new Vec3d(0, 2, 0),
        };

    }

}