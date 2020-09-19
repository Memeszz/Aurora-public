package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.event.events.GuiScreenDisplayedEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.GuiGameOver;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.Misc, "Respawn when you die");
    }

    Setting.b coords;

    public void setup() {
        coords = this.registerB("DeathCoords", "DeathCoords",true);
    }

    @Listener
    public void onUpdate(GuiScreenDisplayedEvent event) {
        if(event.getScreen() instanceof GuiGameOver) {
            if(coords.getValue())
                Wrapper.sendClientMessage(String.format("You died at x%d y%d z%d", (int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ));
            if(mc.player != null)
                mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
