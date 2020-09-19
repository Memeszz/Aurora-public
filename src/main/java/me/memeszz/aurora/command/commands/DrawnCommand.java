package me.memeszz.aurora.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.Wrapper;

public class DrawnCommand extends Command {
    boolean found;
    @Override
    public String[] getAlias() {
        return new String[]{"drawn", "visible", "d"};
    }

    @Override
    public String getSyntax() {
        return "drawn <Module>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        found = false;
        ModuleManager.getModules().forEach(m -> {
            if(m.getName().equalsIgnoreCase(args[0])){
                if(m.isDrawn()){
                    m.setDrawn(false);
                    found = true;
                    Wrapper.sendClientMessage(m.getName() + ChatFormatting.RED + " drawn false");
                } else if(!m.isDrawn()){
                    m.setDrawn(true);
                    found = true;
                    Wrapper.sendClientMessage(m.getName() + ChatFormatting.GREEN + " drawn true");
                }
            }
        });
        if(!found && args.length == 1) Wrapper.sendClientMessage(ChatFormatting.DARK_RED + "Module not found!");
    }
}
