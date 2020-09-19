package me.memeszz.aurora.event;

import com.google.common.collect.Maps;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.command.Command;
import me.memeszz.aurora.command.CommandManager;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.event.events.PlayerJoinEvent;
import me.memeszz.aurora.event.events.PlayerLeaveEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.config.Stopper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class EventProcessor {
    public static EventProcessor INSTANCE;
    Minecraft mc = Minecraft.getMinecraft();
    CommandManager commandManager = new CommandManager();

    public EventProcessor(){
        INSTANCE = this;
    }


    public void init(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onUnload() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public boolean isNull() {
        return (mc.player == null || mc.world == null);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (isNull()) return;
        ModuleManager.onTick();
    }

    //player disconnect
    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Stopper.saveConfig();
        System.out.println("Saved Aurora config!");
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled()) return;
        ModuleManager.onWorldRender(event);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post event) {
        if (isNull()) return;
        Aurora.getInstance().getEventManager().dispatchEvent(event);
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            ModuleManager.onRender();
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0 || Keyboard.getEventKey() == Keyboard.KEY_NONE) return;
            ModuleManager.onBind(Keyboard.getEventKey());
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event){
        if(Mouse.getEventButtonState())
            Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {

        if (event.getMessage().startsWith(Command.getPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                commandManager.callCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                Wrapper.sendClientMessage(ChatFormatting.DARK_RED + "Error: " + e.getMessage());
            }
            //event.setMessage("");
        }
    }

    @SubscribeEvent
    public void onRenderScreen(RenderGameOverlayEvent.Text event) {
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onRenderBlockOverlay(RenderBlockOverlayEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onLivingEntityUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        Aurora.getInstance().getEventManager().dispatchEvent(event);}


    @Listener
    public void onPacketRecieve(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            SPacketPlayerListItem packet = (SPacketPlayerListItem) event.getPacket();
            if (packet.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != ((IMinecraft) mc).getSession().getProfile().getId()) {
                        new Thread(() -> {
                            String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    Aurora.getInstance().getEventManager().dispatchEvent(new PlayerJoinEvent(name));
                            }
                        }).start();
                    }
                }
            }
            if (packet.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (SPacketPlayerListItem.AddPlayerData playerData : packet.getEntries()) {
                    if (playerData.getProfile().getId() != ((IMinecraft) mc).getSession().getProfile().getId()) {
                        new Thread(() -> {
                            final String name = resolveName(playerData.getProfile().getId().toString());
                            if (name != null) {
                                if (mc.player != null && mc.player.ticksExisted >= 1000)
                                    Aurora.getInstance().getEventManager().dispatchEvent(new PlayerLeaveEvent(name));
                            }
                        }).start();
                    }
                }
            }
        }
    }


    private final Map<String, String> uuidNameCache = Maps.newConcurrentMap();

    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (uuidNameCache.containsKey(uuid)) {
            return uuidNameCache.get(uuid);
        }

        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray) JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = (JSONObject) jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }

}
