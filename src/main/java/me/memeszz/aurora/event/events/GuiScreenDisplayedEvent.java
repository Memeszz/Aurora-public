package me.memeszz.aurora.event.events;

import net.minecraft.client.gui.GuiScreen;

public class GuiScreenDisplayedEvent {

    private final GuiScreen screen;

    public GuiScreenDisplayedEvent(GuiScreen screen) {
        this.screen = screen;
    }

    public GuiScreen getScreen() {
        return screen;
    }

}