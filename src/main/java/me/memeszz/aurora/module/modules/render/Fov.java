package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.event.events.GuiScreenDisplayedEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;

public class Fov extends Module {

    Setting.i fovSlider;
    Setting.mode mode;

    public Fov() {
        super("FOV", Category.Render, "Change Fov and Make it lower/higher than vanilla");
        fovSlider = this.registerI("Fov", "Fov", 120, 0, 180);

        ArrayList<String> modes = new ArrayList<>();
        modes.add("ViewModelChanger");
        modes.add("FovChanger");
        mode = this.registerMode("Mode","Mode", modes, "ViewModelChanger");


    }

    public float defaultFov;

    @SubscribeEvent
    public void fovOn(EntityViewRenderEvent.FOVModifier e) {
        if (mode.getValue().equals("ViewModelChanger")) {
            e.setFOV(fovSlider.getValue());

        }
    }

    @Listener
    public void gui(GuiScreenDisplayedEvent event) {
       if (mode.getValue().equals("FovChanger")) {
           mc.gameSettings.fovSetting = fovSlider.getValue();
       }
    }


    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
        defaultFov = mc.gameSettings.fovSetting;
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
        mc.gameSettings.fovSetting = defaultFov;
    }

}