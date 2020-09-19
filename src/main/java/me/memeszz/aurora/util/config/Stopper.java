package me.memeszz.aurora.util.config;


import me.memeszz.aurora.Aurora;

public class Stopper extends Thread {

    @Override
    public void run(){
        saveConfig();
    }

    public static void saveConfig(){

        Aurora.getInstance().saveModules.saveModules();
        SaveConfiguration.saveAutoGG();
        SaveConfiguration.saveBinds();
        SaveConfiguration.saveDrawn();
        SaveConfiguration.saveEnabled();
        SaveConfiguration.saveEnemies();
        SaveConfiguration.saveFriends();
        SaveConfiguration.saveMacros();
        SaveConfiguration.saveMessages();
        SaveConfiguration.savePrefix();
        SaveConfiguration.saveClientname();
    }
}
