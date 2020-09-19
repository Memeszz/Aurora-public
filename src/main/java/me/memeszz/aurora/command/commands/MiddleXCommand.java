package me.memeszz.aurora.command.commands;

import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class MiddleXCommand extends Command {
    @Override
    public String[] getAlias() {
        return new String[]{
                "getmiddlex", "middlex", "getmiddle"
        };
    }

    @Override
    public String getSyntax() {
        return getAlias()[0];
    }

    @Override
    public void onCommand(String command, String[] args) throws Exception {
        Wrapper.sendClientMessage(new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2 + "");
    }
}
