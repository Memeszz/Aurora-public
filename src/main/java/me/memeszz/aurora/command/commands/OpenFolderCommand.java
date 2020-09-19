package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;

import java.awt.*;
import java.io.File;

public class OpenFolderCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"openfolder", "folder"};
    }

    @Override
    public String getSyntax() {
        return "openfolder";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        try {
            Desktop.getDesktop().open(new File("Aurora"));
        } catch(Exception e){sendClientMessage("Error: " + e.getMessage());}
    }
}
