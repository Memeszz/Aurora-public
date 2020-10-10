package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;


public class CoordsCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{"Coord", "Coordinate", "Coords", "Coordinates"};
    }

    @Override
    public String getSyntax() {
        return "coords";
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        final DecimalFormat format = new DecimalFormat("#");
        final StringSelection contents = new StringSelection(format.format(Minecraft.getMinecraft().player.posX) + ", " + format.format(Minecraft.getMinecraft().player.posY) + ", " + format.format(Minecraft.getMinecraft().player.posZ));
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        Wrapper.sendClientMessage("Saved Coordinates To Clipboard");
    }


}
