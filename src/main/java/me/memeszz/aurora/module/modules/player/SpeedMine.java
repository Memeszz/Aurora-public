package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.EventPlayerClickBlock;
import me.memeszz.aurora.event.events.EventPlayerDamageBlock;
import me.memeszz.aurora.event.events.EventPlayerResetBlockRemoving;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IPlayerControllerMP;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class SpeedMine extends Module {
    public SpeedMine() {
        super("SpeedMine", Category.Player, "Mine blocks faster");
    }

    Setting.mode mode;

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + mode.getValue() + "\u00A77]";
    }

    Setting.b reset;
    Setting.b FastFall;
    Setting.b doubleBreak;

    public void setup() {
        reset = this.registerB("Reset", "Reset",false);
        FastFall = this.registerB("FastFall", "FastFall",false);
        doubleBreak = this.registerB("DoubleBreak", "DoubleBreak",false);

        ArrayList<String> modes = new ArrayList<>();
        modes.add("Packet");
        modes.add("Damage");
        modes.add("Instant");
        mode = this.registerMode("Mode","Mode", modes, "Packet");

    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        ((IPlayerControllerMP) mc.playerController).setBlockHitDelay(0);
            if (this.reset.getValue() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
                ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(false);
            }
            if (FastFall.getValue()) {
                if (mc.player.onGround)
                    --mc.player.motionY;
            }
        }


    @Listener
    public void setReset(EventPlayerResetBlockRemoving event) {
        if (this.reset.getValue()) {
            event.setCanceled(true);
        }
    }


    @Listener
    public void clickBlock(EventPlayerClickBlock event) {
             if (this.reset.getValue()) {
                if (((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() > 0.1f) {
                    ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(true);
                }
            }
        }


    @Listener
    public void damageBlock(EventPlayerDamageBlock event) {
            if (canBreak(event.getPos())) {
                if (this.reset.getValue()) {
                    ((IPlayerControllerMP) mc.playerController).setIsHittingBlock(false);
                }

                if (mode.getValue().equalsIgnoreCase("Instant")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(
                            CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                            event.getPos(), event.getDirection()));
                    mc.playerController.onPlayerDestroyBlock(event.getPos());
                    mc.world.setBlockToAir(event.getPos());
                }
                if ((mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {

                if (mode.getValue().equalsIgnoreCase("Packet")) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(
                            CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                            event.getPos(), event.getDirection()));
                    event.setCanceled(true);
                }
                if (mode.getValue().equalsIgnoreCase("Damage")) {
                    if (((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() >= 0.7f) {
                        ((IPlayerControllerMP) mc.playerController).setCurBlockDamageMP(1);
                    }
                }

            }


            if (this.doubleBreak.getValue()) {
                final BlockPos above = event.getPos().add(0, 1, 0);

                if (canBreak(above) && mc.player.getDistance(above.getX(), above.getY(), above.getZ()) <= 5f) {
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(
                            CPacketPlayerDigging.Action.START_DESTROY_BLOCK, above, event.getDirection()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                            above, event.getDirection()));
                    mc.playerController.onPlayerDestroyBlock(above);
                    mc.world.setBlockToAir(above);
                }
            }
        }
    }

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, Minecraft.getMinecraft().world, pos) != -1;
    }
}
