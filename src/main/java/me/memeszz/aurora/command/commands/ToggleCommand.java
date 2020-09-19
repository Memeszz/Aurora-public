package me.memeszz.aurora.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.ModuleManager;
public class ToggleCommand extends Command {
    boolean found;
    @Override
    public String[] getAlias() {
        return new String[]{"toggle", "t"};
    }

    @Override
    public String getSyntax() {
        return "toggle <Module>";
    }

    @Override
    public void onCommand(String command, String[] args) {
        found = false;
        ModuleManager.getModules().forEach(m -> {
            if(m.getName().equalsIgnoreCase(args[0])){
                if(m.isEnabled()){
                    m.disable();
                    found = true;
                } else if(!m.isEnabled()){
                    m.enable();
                    found = true;
                }
            }
        });
        if(!found && args.length == 1) Command.sendClientMessage(ChatFormatting.DARK_RED + "Module not found!");
    }
}