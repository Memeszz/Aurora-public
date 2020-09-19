package me.memeszz.aurora.module.modules.movement;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.init.Blocks;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class IceSpeed extends Module {
   public IceSpeed() {
      super("IceSpeed", Category.Movement, "SPEED");
   }

   Setting.d speed;
      public void setup(){
            speed = this.registerD("Speed", "Speed", 0.4, 0, 1.0);

   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      Blocks.ICE.slipperiness = (float) this.speed.getValue();
      Blocks.PACKED_ICE.slipperiness = (float) this.speed.getValue();
      Blocks.FROSTED_ICE.slipperiness = (float) this.speed.getValue();
   }

   public void onDisable() {
      Blocks.ICE.slipperiness = 0.98F;
      Blocks.PACKED_ICE.slipperiness = 0.98F;
      Blocks.FROSTED_ICE.slipperiness = 0.98F;
   }
}
