package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.GuiScreenDisplayedEvent;
import me.memeszz.aurora.module.Module;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class Brightness extends Module {
    public Brightness() {
        super("Brightness", Category.Render, "Lets you see shit when it's dark");
    }

    float old;

    public void onEnable(){
        old = mc.gameSettings.gammaSetting;
    }


    //made this ongui instead of onupdate because thats chinese
    @Listener
    public void onGui(GuiScreenDisplayedEvent event) {
        mc.gameSettings.gammaSetting = 666f;
    }

    public void onDisable(){
        mc.gameSettings.gammaSetting = old;
    }
}
