package me.memeszz.aurora.module.modules.gui;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.colour.ColourHolder;
import me.memeszz.aurora.util.colour.RainbowUtil;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.misc.TickRate;
import me.memeszz.aurora.util.render.Animation;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;


/**
 * @author Memeszz & hollow
 */
public class Hud extends Module {
    int y;

    public Hud() {
        super("Hud", Category.Gui, "Attacks nearby players");
        setDrawn(false);
    }


    Setting.b welcomer;
    Setting.b server;
    Setting.b ping;
    Setting.b ArmorHud;
    Setting.b fps;
    Setting.b watermark;
    Setting.b arraylistOutline;
    Setting.b coordinates;
    Setting.b tps;
    Setting.b time1;
    Setting.b arraylist;
    Setting.i animS;
    Setting.i rainbowSpeed;
    Setting.i thing;
    public static Setting.b rainbow;
    public static Setting.i red;
    public static Setting.i green;
    public static Setting.i blue;
    static final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
    Setting.mode mode;
    String coords;
    final String time = new SimpleDateFormat("h:mm a").format(new Date());

    public void setup() {
        watermark = this.registerB("Watermark", "Watermark",true);
        welcomer = this.registerB("Welcomer","Welcomer",  true);
        server = this.registerB("Server", "Server",true);
        ping = this.registerB("Ping","Ping",  true);
        time1 = registerB("Time", "Time",true);
        tps = this.registerB("Tps", "Tps",true);
        fps = this.registerB("Fps", "Fps", true);
        coordinates = this.registerB("Coords", "Coords", true);
        ArmorHud = this.registerB("ArmorHud","ArmorHud", true);
        arraylist = this.registerB("ArrayList","ArrayList", true);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Top");
        mode = this.registerMode("Mode","Mode", modes, "Bottom");
        arraylistOutline = this.registerB("ArraylistOutline", "ArraylistOutline",true);
        animS = registerI("AnimSpeed", "AnimSpeed",1, 0, 10);
        red = this.registerI("Red", "Red",255, 0, 255);
        green = this.registerI("Green", "Green", 255, 0, 255);
        blue = this.registerI("Blue", "Blue",255, 0, 255);
        rainbow = this.registerB("Rainbow","Rainbow",  false);
        rainbowSpeed = this.registerI("RainbowSpeed", "RainbowSpeed",1, 1, 25);
        thing = registerI("Thing", "1", 1, 1, 3000);
    }


    public void onRender() {
        int modCount = 0;
        int[] counter1 = {1};
        ScaledResolution resolution = new ScaledResolution(mc);

        if (arraylist.getValue()) {
            int[] counter = {1};
            ArrayList<Module> modules = new ArrayList<>(ModuleManager.getModules());
            modules.sort(Comparator.comparing(m -> -FontUtils.getStringWidth(ClickGuiModule.customFont.getValue(), m.getName() + m.getHudInfo())));
            for (int i = 0; i < modules.size(); i++) {
                Module module = modules.get(i);
                if (module.isEnabled() && module.isDrawn()) {
                    int x = resolution.getScaledWidth();
                    int y = 3 + (modCount * 10);
                    int lWidth = FontUtils.getStringWidth(ClickGuiModule.customFont.getValue(), module.getName() + ChatFormatting.GRAY + module.getHudInfo());
                    if (module.animPos < lWidth && module.isEnabled()) {
                        module.animPos = Animation.moveTowards(module.animPos, lWidth + 1, 0.01f + animS.getValue() / 30, 0.1f);
                    } else if (module.animPos > 1.5f && !module.isEnabled()) {
                        module.animPos = Animation.moveTowards(module.animPos, -1.5f, 0.01f + animS.getValue() / 30, 0.1f);
                    } else if (module.animPos <= 1.5f && !module.isEnabled()) {
                        module.animPos = -1f;
                    }
                    if (module.animPos > lWidth && module.isEnabled()) {
                        module.animPos = lWidth;
                    }

                    x -= module.animPos;
                    if (arraylistOutline.getValue()) {
                        RenderUtil.drawBorderedRect(x - 6, y - 3, resolution.getScaledWidth(), y + FontUtils.getFontHeight(false), 1, new Color(20, 20, 20, 200).getRGB(), 0);
                        RenderUtil.drawGradient(resolution.getScaledWidth() - 2, y - 4, resolution.getScaledWidth(), y + FontUtils.getFontHeight(false), rainbow.getValue() ? RainbowUtil.rainbow(counter[0] * 100) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB(), rainbow.getValue() ? RainbowUtil.rainbow(counter[0] * 100) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
                    }

                    FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), module.getName() + ChatFormatting.GRAY + module.getHudInfo(), x - 3, y, rainbow.getValue() ? RainbowUtil.rainbow(counter[0] * 100) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
                    modCount++;
                    counter[0]++;
                }
            }
        }


        if (ArmorHud.getValue()) {
            GlStateManager.enableTexture2D();
            int i = resolution.getScaledWidth() / 2;
            int iteration = 0;
            int y = resolution.getScaledHeight() - 55 - (mc.player.isInWater() ? 10 : 0);
            for (ItemStack is : mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) continue;
                int x = i - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                itemRender.zLevel = 200.0f;
                itemRender.renderItemAndEffectIntoGUI(is, x, y);
                itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
                itemRender.zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                String s = is.getCount() > 1 ? is.getCount() + "" : "";
                FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), s, x + 19 - 2 - Aurora.fontRenderer.getStringWidth(s), y + 9, 16777215);
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1.0f - green;
                int dmg = 100 - (int) (red * 100.0f);
                FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), dmg + "", x + 8 - Aurora.fontRenderer.getStringWidth(dmg + "") / 2, y - 11, ColourHolder.toHex((int) (red * 255.0f), (int) (green * 255.0f), 0));
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }

        int posY = 2;

        if (watermark.getValue()) {
            final String text = Aurora.MODNAME + " " + Aurora.MODVER;
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), text, 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
            posY += 10;
        }

        if (server.getValue()) {
            if (mc.player != null) {
                if (!mc.isSingleplayer()) {
                    FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), "\u00A7rServer \u00A7f" + ((IMinecraft) mc).getCurrentServerData().serverIP + "", 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
                    counter1[0]++;
                    posY += 10;
                }
            }
        }

        if (ping.getValue()) {
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), "\u00A7rPing \u00A7f" + getPing() + "ms", 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
            posY += 10;
        }

        if (time1.getValue()) {
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), time, 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
            posY += 10;
        }

        if (tps.getValue()) {
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), "\u00A7rTPS \u00A7f" + TickRate.TPS + "", 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
            posY += 10;
        }


        if (fps.getValue()) {
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), "\u00A7rFPS \u00A7f" + Minecraft.getDebugFPS() + "", 2, posY, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
        }


        if (coordinates.getValue()) {
            y = ((mc.currentScreen instanceof GuiChat) ? 15 : 2);
            if (mc.player.dimension == -1) {
                coords = ChatFormatting.GRAY + "XYZ " + ChatFormatting.WHITE + mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ() +
                        ChatFormatting.GRAY + " [" + ChatFormatting.WHITE + mc.player.getPosition().getX() * 8 + ", " + mc.player.getPosition().getZ() * 8 + ChatFormatting.GRAY + "]";
            } else {
                coords = ChatFormatting.GRAY + "XYZ " + ChatFormatting.WHITE + mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + Math.floor(mc.player.getPosition().getZ()) +
                        ChatFormatting.GRAY + " [" + ChatFormatting.WHITE + mc.player.getPosition().getX() / 8 + ", " + mc.player.getPosition().getZ() / 8 + ChatFormatting.GRAY + "]";
            }
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), coords, 0, resolution.getScaledHeight() - y - Aurora.fontRenderer.getHeight(), new Color(255, 255, 255, 255).getRGB());
        }

        if (welcomer.getValue()) {
            final String text = "Welcome " + mc.player.getName() + " :^)";
            drawCentredString(text, resolution.getScaledWidth() / 2, rainbow.getValue() ? RainbowUtil.rainbow(counter1[0] * RainbowOffset.offset.getValue()) : new Color(red.getValue(), green.getValue(), blue.getValue(), 255).getRGB());
            counter1[0]++;
        }
    }

    public int getPing() {
        int p;
        if (mc.player == null || mc.getConnection() == null || mc.getConnection().getPlayerInfo(mc.player.getName()) == null) {
            p = -1;
        } else {
            mc.player.getName();
            p = Objects.requireNonNull(mc.getConnection().getPlayerInfo(mc.player.getName())).getResponseTime();
        }
        return p;
    }

    private void drawCentredString(String text, int x, int color) {
        FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), text, x - FontUtils.getStringWidth(ClickGuiModule.customFont.getValue(), text) / 2, 2, color);
    }
}