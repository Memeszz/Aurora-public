package me.memeszz.aurora.util.config;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.module.modules.world.AutoGG;
import me.memeszz.aurora.util.enemy.Enemies;
import me.memeszz.aurora.util.enemy.Enemy;
import me.memeszz.aurora.util.friends.Friend;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.macro.Macro;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;


public class SaveConfiguration {

    Minecraft mc = Minecraft.getMinecraft();

    //File Structure
    public static File Aurora;
        //Main file, %appdata%/.minecraft
    public static File Modules;
        //Inside main file, houses settings for modules
    public static File Messages;
        //Inside main file, houses settings for client messages such as AutoGG
    public static File Miscellaneous;
        //Inside main file, houses settings for client settings such as Font
    public static File Clientname;

    public static File Combat;
    public static File World;
    public static File Gui;
    public static File Misc;
    public static File Player;
    public static File Movement;
    public static File Render;
        //Files inside the modules folder, houses module configs per category

    public SaveConfiguration(){

        Aurora = new File(mc.gameDir + File.separator + "Aurora");
        if (!Aurora.exists()) {
            Aurora.mkdirs();
        }

        Modules = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules");
        if (!Modules.exists()) {
            Modules.mkdirs();
        }

        Messages = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Messages");
        if (!Messages.exists()) {
            Messages.mkdirs();
        }

        Miscellaneous = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Miscellaneous");
        if (!Miscellaneous.exists()) {
            Miscellaneous.mkdirs();
        }

        Combat = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Combat");
        if (!Combat.exists()) {
            Combat.mkdirs();
        }

        World = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "World");
        if (!World.exists()) {
            World.mkdirs();
        }

        Gui = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Gui");
        if (!Gui.exists()){
            Gui.mkdirs();
        }

        Misc = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Misc");
        if (!Misc.exists()){
            Misc.mkdirs();
        }

        Player = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Player");
        if (!Player.exists()){
            Player.mkdirs();
        }

        Movement = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Movement");
        if (!Movement.exists()){
            Movement.mkdirs();
        }

        Render = new File(mc.gameDir + File.separator + "Aurora" + File.separator + "Modules" + File.separator + "Render");
        if (!Render.exists()){
            Render.mkdirs();
        }
    }



    //saves macros
    public static void saveMacros(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "ClientMacros.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = me.memeszz.aurora.Aurora.getInstance().macroManager.getMacros().iterator();
            while(var3.hasNext()) {
                Macro m = (Macro) var3.next();
                out.write(Keyboard.getKeyName(m.getKey()) + ":" + m.getValue().replace(" ", "_"));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }
    public static void saveClientname() {
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "Clientname.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(me.memeszz.aurora.Aurora.MODNAME);
            //out.write("\r\n");
            out.close();
        } catch (Exception var3) {
        }

    }

    //saves friends
    public static void saveFriends(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "Friends.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = Friends.getFriends().iterator();
            while(var3.hasNext()) {
                Friend f = (Friend)var3.next();
                out.write(f.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves enemies
    public static void saveEnemies(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "Enemies.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = Enemies.getEnemies().iterator();
            while(var3.hasNext()) {
                Enemy e = (Enemy)var3.next();
                out.write(e.getName());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves prefix
    public static void savePrefix(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "CommandPrefix.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.getPrefix());
            out.write("\r\n");
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves AutoGG
    public static void saveAutoGG(){
        try {
            File file = new File(Messages.getAbsolutePath(), "AutoGG.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(String s : AutoGG.getAutoGgMessages()) {
                out.write(s);
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves client messages such as the watermark
    public static void saveMessages(){
        try {
            File file = new File(Messages.getAbsolutePath(), "ClientMessages.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(Command.MsgWaterMark + "");
            out.write(",");
            out.write(Command.cf.getName());
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves drawn modules
    public static void saveDrawn(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "DrawnModules.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = ModuleManager.getModules().iterator();
            while(var3.hasNext()) {
                Module module = (Module) var3.next();
                out.write(module.getName() + ":" + module.isDrawn());
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves enabled/disabled modules
    public static void saveEnabled(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "EnabledModules.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = ModuleManager.getModules().iterator();
            while(var3.hasNext()) {
                Module module = (Module)var3.next();
                if (module.isEnabled()) {
                    out.write(module.getName());
                    out.write("\r\n");
                }
            }
            out.close();
        }
        catch (Exception var5){
        }
    }

    //saves module binds
    public static void saveBinds(){
        try {
            File file = new File(Miscellaneous.getAbsolutePath(), "ModuleBinds.json");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            Iterator var3 = ModuleManager.getModules().iterator();
            while(var3.hasNext()) {
                Module module = (Module)var3.next();
                out.write(module.getName() + ":" + Keyboard.getKeyName(module.getBind()));
                out.write("\r\n");
            }
            out.close();
        }
        catch (Exception var5){
        }
    }
}