package me.memeszz.aurora.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.ArrayList;
import java.util.List;

public class VisualRange extends Module {
    public VisualRange() {
        super("VisualRange", Category.World, "Sends a client side message when someone enters your render distance");
    }
    List<Entity> knownPlayers = new ArrayList<>();
    List<Entity> players;

    @Listener
    public void onUpdate(UpdateEvent event) {
        if(mc.player == null) return;
        players = new ArrayList<>(mc.world.playerEntities);
        try {
            for (Entity e : players) {
                if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                    if (!knownPlayers.contains(e)) {
                        knownPlayers.add(e);
                        Command.sendClientMessage(ChatFormatting.GREEN+e.getName() + ChatFormatting.RED+ " entered visual range.");
                    }
                }
            }
        } catch(Exception e){} // ez no crasherino
            try {
                for (Entity e : knownPlayers) {
                    if (e instanceof EntityPlayer && !e.getName().equalsIgnoreCase(mc.player.getName())) {
                        if (!players.contains(e)) {
                            knownPlayers.remove(e);
                        }
                    }
                }
            } catch(Exception e){} // ez no crasherino pt.2
    }

    public void onDisable(){
        knownPlayers.clear();
    }
}
