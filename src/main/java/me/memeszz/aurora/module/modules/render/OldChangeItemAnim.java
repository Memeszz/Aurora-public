package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IItemRenderer;
import me.memeszz.aurora.module.Module;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class OldChangeItemAnim extends Module {
    public OldChangeItemAnim() {
        super("ItemAnim", Category.Render);
    }


    @Listener
    public void onUpdate(UpdateEvent event) {
        if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressMainHand() >= 0.9) {
            ((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressMainHand(1.0f);
            ((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackMainHand(mc.player.getHeldItem(EnumHand.MAIN_HAND));
        }
        if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressOffHand() >= 0.9) {
            ((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressOffHand(1.0f);
            ((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackOffHand(mc.player.getHeldItem(EnumHand.OFF_HAND));
        }

    }
}
