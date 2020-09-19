package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IItemRenderer;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.util.EnumHand;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class LowHand extends Module {
    public LowHand() {
        super ("LowHand", Category.Render);
    }

    Setting.d offhand;
    Setting.d mainhand;

    public void setup() {
        offhand = registerD("Offhand", "Offhand",1, 0, 1);
        mainhand = registerD("Mainhand", "Mainhand",1, 0, 1);
    }


    @Listener
    public void onUp(UpdateEvent event) {
        ((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressOffHand((float) offhand.getValue());
        ((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressMainHand((float) mainhand.getValue());
        ((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackMainHand(mc.player.getHeldItem(EnumHand.MAIN_HAND));
    }
}
