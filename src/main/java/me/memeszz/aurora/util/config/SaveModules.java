package me.memeszz.aurora.util.config;


import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.setting.Setting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;


public class    SaveModules {

    public void saveModules(){
        saveCombat();
        saveWorld();
        saveGui();
        saveMisc();
        savePlayer();
        saveMovement();
        saveRender();
    }

    public void saveCombat(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Combat).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Combat).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Combat.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Combat).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves World-related modules
    public void saveWorld(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.World.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.World).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.World.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.World).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.World.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.World).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves Gui-related modules
    public void saveGui(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Gui.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Gui).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Gui.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Gui).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Gui.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Gui).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves misc-related modules
    public void saveMisc(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Misc).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Misc).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Misc.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Misc).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    public void savePlayer(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Player).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Player).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Player.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Player).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves movement-related modules
    public void saveMovement(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Movement).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Movement).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Movement.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Movement).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }

    //saves render-related modules
    public void saveRender(){
        File file;
        BufferedWriter out;
        Iterator var3;
        Setting i;
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Value.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Render).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.D) {
                    out.write(i.getConfigName() + ":" +((Setting.d) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
                if (i.getType() == Setting.Type.I) {
                    out.write(i.getConfigName() + ":" +((Setting.i) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var7) {
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "Boolean.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Render).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.B) {
                    out.write(i.getConfigName() + ":" + ((Setting.b) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var6) {
        }
        try {
            file = new File(SaveConfiguration.Render.getAbsolutePath(), "String.json");
            out = new BufferedWriter(new FileWriter(file));
            var3 = Aurora.getInstance().settingsManager.getSettingsByCategory(Module.Category.Render).iterator();
            while(var3.hasNext()) {
                i = (Setting)var3.next();
                if (i.getType() == Setting.Type.M) {
                    out.write(i.getConfigName() + ":" + ((Setting.mode) i).getValue() + ":" + i.getParent().getName() + "\r\n");
                }
            }
            out.close();
        } catch (Exception var5) {
        }
    }
}