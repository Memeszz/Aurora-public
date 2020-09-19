package me.memeszz.aurora.module.modules.player;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.mixin.accessor.IPlayerControllerMP;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse", Category.Player, "Sets right click / block break delay to 0");
    }

    Setting.b xp;
    Setting.b crystals;
    Setting.b all;
    Setting.b breakS;
    Setting.b fastBow;
//ez
    public void setup(){
        xp = this.registerB( "EXP", "EXP",true);
        crystals = this.registerB("Crystals", "Crystals",false);
        all = this.registerB("Everything", "Everything",false);
        breakS = this.registerB("FastBreak", "FastBreak", true);
        fastBow = this.registerB("FastBow", "FastBow", false);
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        if (xp.getValue()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || mc.player.getHeldItemOffhand().getItem() == Items.EXPERIENCE_BOTTLE)) {
                ((IMinecraft) mc).setRightClickDelayTimer(0);
            }
        }

        if (crystals.getValue()) {
            if (mc.player != null && (mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                ((IMinecraft) mc).setRightClickDelayTimer(0);
            }
        }

        if (all.getValue()) {
            ((IMinecraft) mc).setRightClickDelayTimer(0);
        }

        if (breakS.getValue()){
            ((IPlayerControllerMP) mc.playerController).setBlockHitDelay(0);
        }

        if (fastBow.getValue()) {
            if (mc.player.inventory.getCurrentItem().getItem() instanceof net.minecraft.item.ItemBow && mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                mc.player.stopActiveHand();
            }
        }
    }
}
