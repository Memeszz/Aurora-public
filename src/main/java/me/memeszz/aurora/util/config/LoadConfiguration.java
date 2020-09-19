package me.memeszz.aurora.util.config;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.module.modules.world.AutoGG;
import me.memeszz.aurora.util.enemy.Enemies;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.macro.Macro;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.Iterator;



public class LoadConfiguration {

    public LoadConfiguration(){

        //loads functions on client startup
        loadAutoGG();
        loadBinds();
        loadDrawn();
        loadEnabled();
        loadEnemies();
        loadFriends();
        loadMacros();
        loadMessages();
        loadPrefix();
        loadClientname();
    }

    public void loadMacros(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ClientMacros.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String bind = curLine.split(":")[0];
                String value = curLine.split(":")[1];
                Aurora.getInstance().macroManager.addMacro(new Macro(Keyboard.getKeyIndex(bind), value.replace("_", " ")));
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveMacros();
        }
    }

    public void loadClientname(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Clientname.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                me.memeszz.aurora.Aurora.MODNAME = line;            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveMacros();
        }
    }

    //loads friends
    public void loadFriends(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Friends.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Friends.friends.clear();
            String line;
            while((line = br.readLine()) != null) {
                Aurora.getInstance().friends.addFriend(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveFriends();
        }
    }

    //loads enemies
    public void loadEnemies(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "Enemies.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            Enemies.enemies.clear();
            String line;
            while((line = br.readLine()) != null) {
                Enemies.addEnemy(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveEnemies();
        }
    }

    //loads client command prefix
    public void loadPrefix(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "CommandPrefix.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                Command.setPrefix(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.savePrefix();
        }
    }


    //loads AutoGG message
    public void loadAutoGG(){
        try {
            File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "AutoGG.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                AutoGG.addAutoGgMessage(line);
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveAutoGG();
        }
    }

    //loads client messages such as the watermark
    public void loadMessages(){
        try {
            File file = new File(SaveConfiguration.Messages.getAbsolutePath(), "ClientMessages.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String watermark = curLine.split(",")[0];
                String color = curLine.split(",")[1];
                boolean w = Boolean.parseBoolean(watermark);
                ChatFormatting c = ChatFormatting.getByName(color);
                Command.cf = c;
                Command.MsgWaterMark = w;
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveMessages();
        }
    }

    //loads drawn modules
    public void loadDrawn(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "DrawnModules.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String isOn = curLine.split(":")[1];
                boolean drawn = Boolean.parseBoolean(isOn);
                for(Module m : ModuleManager.getModules()) {
                    if (m.getName().equalsIgnoreCase(name)) {
                        m.setDrawn(drawn);
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveDrawn();
        }
    }

    //loads enabled/disabled modules
    public void loadEnabled(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "EnabledModules.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                Iterator var6 = ModuleManager.getModules().iterator();
                while(var6.hasNext()) {
                    Module m = (Module)var6.next();
                    if (m.getName().equals(line)) {
                        m.enable();
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveEnabled();
        }
    }

    //loads module binds
    public void loadBinds(){
        try {
            File file = new File(SaveConfiguration.Miscellaneous.getAbsolutePath(), "ModuleBinds.json");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String bind = curLine.split(":")[1];
                for(Module m : ModuleManager.getModules()) {
                    if (m != null && m.getName().equalsIgnoreCase(name)) {
                        m.setBind(Keyboard.getKeyIndex(bind));
                    }
                }
            }
            br.close();
        }
        catch (Exception var6){
            var6.printStackTrace();
            SaveConfiguration.saveBinds();
        }
    }
}