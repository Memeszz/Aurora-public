package me.memeszz.aurora.module.modules.misc;

import me.memeszz.aurora.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class LogoutCoords extends Module {

    public LogoutCoords() {
        super("LogoutCoords", Category.Misc, "Saves your coords to the clipboard when logging out of a server");
    }

    @SubscribeEvent
    public void onPlayerLeaveEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) { if (!mc.isSingleplayer()) { if (!mc.getCurrentServerData().serverIP.equalsIgnoreCase("2b2tpvp.net") && mc.player.dimension != 1) { int x = (int) mc.player.posX;int y = (int) mc.player.posY;int z = (int) mc.player.posZ;String coords = "Logout Coords: X:" + x + " Y:" + y + " Z:" + z;StringSelection data = new StringSelection(coords);Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();cb.setContents(data, data); } } }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}