package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;

public class PrefixCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"prefix", "setprefix"};
    }

    @Override
    public String getSyntax() {
        return "prefix <character>";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Command.setPrefix(args[0]);
        Wrapper.sendClientMessage("Command prefix set to " + Command.getPrefix());
    }
}
