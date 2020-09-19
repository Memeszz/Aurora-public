package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.Wrapper;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"bind", "b"};
    }

    @Override
    public String getSyntax() {
        return "bind <Module> <Key>";
    }

    @Override
    public void onCommand(String command, String[] args) {
        int key = Keyboard.getKeyIndex(args[1].toUpperCase());
        ModuleManager.getModules().forEach(m ->{
            if(args[0].equalsIgnoreCase(m.getName())){
                m.setBind(key);
                Wrapper.sendClientMessage(args[0] + " bound to " + args[1].toUpperCase());
            }
        });
    }
}
