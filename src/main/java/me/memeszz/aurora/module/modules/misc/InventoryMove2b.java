package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.inventory.GuiInventory;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class InventoryMove2b extends Module {

    Setting.b sprint;
    Setting.b sneak;

    public InventoryMove2b() {
        super("InventoryMove", Category.Misc);
        sprint = registerB("Sprint", "Sprint", false);
        sneak = this.registerB("Sneak", "Sneak",true);

    }

    public void setup() {

    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        if (mc.currentScreen instanceof GuiInventory) {
            if (sprint.getValue()) mc.player.setSprinting(false);
            if (sneak.getValue()) mc.player.setSneaking(false);
                /*if (mc.player.motionX > 0 || mc.player.motionZ > 0 || mc.player.motionX < 0 || mc.player.motionZ < 0) {
                    for (int i = 0; i < 45; i++) {
                        if (((GuiInventory) mc.currentScreen).inventorySlots.slotClick(i, 0, ClickType.PICKUP, mc.player).isEmpty())
                            Wrapper.sendClientMessage("You Clicked Ur Inventory");

                    }

                } */
        }
    }
}
