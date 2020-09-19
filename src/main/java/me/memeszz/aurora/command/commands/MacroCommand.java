package me.memeszz.aurora.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.macro.Macro;
import org.lwjgl.input.Keyboard;

public class MacroCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"macro", "macros"};
    }

    @Override
    public String getSyntax() {
        return "macro <add | del> <key> <value>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        if(args[0].equalsIgnoreCase("add")){
            Aurora.getInstance().macroManager.delMacro(Aurora.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1])));
            Aurora.getInstance().macroManager.addMacro(new Macro(Keyboard.getKeyIndex(args[1].toUpperCase()), args[2].replace("_", " ")));
            Wrapper.sendClientMessage(ChatFormatting.GREEN + "Added" + ChatFormatting.GREEN + " macro for key \"" + args[1].toUpperCase() + "\" with value \"" + args[2].replace("_", " ") + "\".");
        }
        if(args[0].equalsIgnoreCase("del")){
            if(Aurora.getInstance().macroManager.getMacros().contains(Aurora.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase())))) {
                Aurora.getInstance().macroManager.delMacro(Aurora.getInstance().macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase())));
                Wrapper.sendClientMessage(ChatFormatting.RED + "Removed " + ChatFormatting.RED + "macro for key \"" + args[1].toUpperCase() + "\".");
            }else {
                Wrapper.sendClientMessage(ChatFormatting.RED + "That macro doesn't exist!");
            }
        }
    }
}
