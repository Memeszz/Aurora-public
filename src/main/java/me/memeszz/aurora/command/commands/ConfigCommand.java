package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.config.Stopper;

public class ConfigCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"saveconfig", "savecfg"};
    }

    @Override
    public String getSyntax() {
        return "saveconfig";
    }

    @Override
    public void onCommand(String command, String[] args) {
        Stopper.saveConfig();
        Wrapper.sendClientMessage("Saved Aurora config!");
    }
}
