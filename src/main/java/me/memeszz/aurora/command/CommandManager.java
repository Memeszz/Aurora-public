package me.memeszz.aurora.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.command.commands.*;
import me.memeszz.aurora.util.Wrapper;

import java.util.ArrayList;

public class CommandManager {
    private static ArrayList<Command> commands;
    boolean b;

    public static void initCommands(){
        commands = new ArrayList<>();
        addCommand(new CoordsCommand());
        addCommand(new BindCommand());
        addCommand(new ToggleCommand());
        addCommand(new DrawnCommand());
        addCommand(new SetCommand());
        addCommand(new CmdsCommand());
        addCommand(new ModsCommand());
        addCommand(new PrefixCommand());
        addCommand(new FriendCommand());
        addCommand(new MacroCommand());
        addCommand(new ConfigCommand());
        addCommand(new OpenFolderCommand());
        addCommand(new MiddleXCommand());
        addCommand(new EnemyCommand());
        addCommand(new ClientnameCommand());
    }

    public static void addCommand(Command c){
        commands.add(c);
    }

    public static ArrayList<Command> getCommands(){
        return commands;
    }

    public void callCommand(String input){
        String[] split = input.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); 
        String command = split[0];
        String args = input.substring(command.length()).trim();
        b = false;
        commands.forEach(c ->{
            for(String s : c.getAlias()) {
                if (s.equalsIgnoreCase(command)) {
                    b = true;
                    try {
                        c.onCommand(args, args.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));
                    } catch (Exception e) {
                        Wrapper.sendClientMessage(ChatFormatting.RED + c.getSyntax());
                    }
                }
            }
        });
        if(!b) Wrapper.sendClientMessage(ChatFormatting.RED + "Unknown command!");
    }

}
