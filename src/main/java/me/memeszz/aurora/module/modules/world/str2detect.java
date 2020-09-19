package me.memeszz.aurora.module.modules.world;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;


public class str2detect extends Module {
    public str2detect() {
        super("StrengthDetect", Category.World, "Tells you in chat when someone has str 2/1");
    }
    private final Set<EntityPlayer> str = Collections.newSetFromMap(new WeakHashMap());
    public static final Minecraft mc = Minecraft.getMinecraft();

    @Listener
    public void onUpdate(UpdateEvent event) {
        for (EntityPlayer player : mc.world.playerEntities) {
            if (player.equals(mc.player)) continue;
            if (player.isPotionActive(MobEffects.STRENGTH) && !this.str.contains(player)) {
                Wrapper.sendClientMessage(player.getDisplayNameString() + " Has Strength");
                this.str.add(player);
            }
            if (!this.str.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
            Wrapper.sendClientMessage(player.getDisplayNameString() + " Has Ran Out Of Strength");
            this.str.remove(player);
        }
    }
}