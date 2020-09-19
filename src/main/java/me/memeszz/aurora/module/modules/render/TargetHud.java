package me.memeszz.aurora.module.modules.render;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.modules.combat.AutoCrystal;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.math.MathUtil;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class TargetHud extends Module {
    public TargetHud() {
        super ("TargetHud", Category.Render);
    }

    Setting.i x_;
    Setting.i y_;

    public void setup() {
        x_ = registerI("X", "X",2, 0, 500);
        y_ = registerI("Y","Y", 2, 0, 500);
    }

                /*mc.renderEngine.bindTexture(new ResourceLocation("minecraft", "textures/items/diamond_sword.png"));
            Gui.drawModalRectWithCustomSizedTexture(x + 38, y + 6, 0,0, 16, 16, 16, 16);*/

    public void onRender() {
        int x = x_.getValue();
        int y = y_.getValue();
        int width = 120;
        int height = 40;
        EntityPlayer target = AutoCrystal.target2;
        if (target != null) {
            RenderUtil.drawBorderedRect(x, y, x + width, y + height, 1.5, new Color(255, 255, 255, 100).getRGB(), new Color(255, 255, 255, 255).getRGB());
            final String playerName = target.getName();
            FontUtils.drawStringWithShadow(true, playerName, x + 36, y + 4, -1);
            FontUtils.drawStringWithShadow(true, "Ping : " + getPing(target), x + 36, y + 14, -1);
            FontUtils.drawStringWithShadow(true, "HP : " + EntityUtil.totalHealth(target), x + 36, y + 24, -1);
            try {
                AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(playerName), playerName).loadTexture(Minecraft.getMinecraft().getResourceManager());
                Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(playerName));
                GL11.glColor4f(1F, 1F, 1F, 1F);
                Gui.drawScaledCustomSizeModalRect(x + 4, y + 4, 8, 8, 8, 8, 28, 28, 64, 64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public int getPing(EntityPlayer player) {
        int ping = 0;
        try {
            ping = (int)MathUtil.clamp((float) Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1, 300.0f);
        }
        catch (NullPointerException ignored) {}
        return ping;
    }

}
