package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;

public class SetCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"set"};
    }

    @Override
    public String getSyntax() {
        return "set <Module> <Setting> <Value>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        for(Module m : ModuleManager.getModules()) {
            if(m.getName().equalsIgnoreCase(args[0])) {
                System.out.println("no");
            }
        }
    }
}
