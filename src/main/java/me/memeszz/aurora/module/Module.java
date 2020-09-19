package me.memeszz.aurora.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.List;
//ez
public class Module {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    String name, description;
    Category category;
    int bind;
    boolean enabled, drawn;
    public float animPos = -1;

    public Module(String n, Category c) {
        name = n;
        category = c;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;
        description = "No description";
        setup();
    }

    public Module(String n, Category c, String desc) {
        name = n;
        category = c;
        bind = Keyboard.KEY_NONE;
        enabled = false;
        drawn = true;
        description = desc;
        setup();
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category c){
        category = c;
    }

    public int getBind(){
        return bind;
    }

    public void setBind(int b){
        bind = b;
    }

    protected void onEnable(){
    }
    protected void onDisable(){
    }
    public void onRender(){}
    public void onUpdate(){}
    public void onTick(){}
    public void onWorldRender(RenderEvent event) {}

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean e){
        enabled = e;
    }

    public void enable(){
        Aurora.getInstance().getEventManager().addEventListener(this);
        setEnabled(true);
        animPos = -1;
        if(ModuleManager.isModuleEnabled("ToggleMsgs") && !getName().equalsIgnoreCase("ClickGUI")) Wrapper.sendClientMessage(ChatFormatting.YELLOW + getName() + ChatFormatting.GREEN + " Enabled.");
        onEnable();
    }

    public void disable(){
        Aurora.getInstance().getEventManager().removeEventListener(this);
        setEnabled(false);
        if(ModuleManager.isModuleEnabled("ToggleMsgs") && !getName().equalsIgnoreCase("ClickGUI")) Wrapper.sendClientMessage(ChatFormatting.YELLOW + getName() + ChatFormatting.RED + " Disabled.");
        onDisable();
    }

    public void toggle(){
        if(isEnabled()) {
            disable();
        } else if(!isEnabled()){
            enable();
        }
    }


    protected Setting.i registerI(final String name, final String configname, final int value, final int min, final int max) {
        final Setting.i s = new Setting.i(name, configname, this, getCategory(), value, min, max);
        Aurora.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.d registerD(final String name, final String configname, final double value, final double min, final double max) {
        final Setting.d s = new Setting.d(name, configname, this, getCategory(), value, min, max);
        Aurora.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.b registerB(final String name, final String configname, final boolean value) {
        final Setting.b s = new Setting.b(name, configname, this, getCategory(), value);
        Aurora.getInstance().settingsManager.addSetting(s);
        return s;
    }

    protected Setting.mode registerMode(final String name, final String configname, final List<String> modes, final String value) {
        final Setting.mode s = new Setting.mode(name, configname, this, getCategory(), modes, value);
        Aurora.getInstance().settingsManager.addSetting(s);
        return s;
    }

    public String getHudInfo(){
        return "";
    }

    public void setup(){}

    public boolean isDrawn(){
        return drawn;
    }

    public void setDrawn(boolean d){
        drawn = d;
    }

    public String getDescription(){
        return description;
    }


    public enum Category {
        Combat,
        Player,
        Movement,
        Misc,
        World,
        Render,
        Gui
    }

}
