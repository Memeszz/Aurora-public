package me.memeszz.aurora.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.RenderEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.mixin.accessor.IRenderManager;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.modules.gui.ClickGuiModule;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.colour.RainbowUtil;
import me.memeszz.aurora.util.enemy.Enemies;
import me.memeszz.aurora.util.entity.EntityUtil;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.math.MathUtil;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.launch.GlobalProperties;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class NameTags extends Module {
    private GlobalProperties popList;
    private boolean customfont;

    public NameTags() {
        super("NameTags", Category.Render);

    }

    Setting.d scale;
    Setting.b rainbowOutline;
    Setting.b ping;
    Setting.b gamemode;
    Setting.b health;
    Setting.b totemPops;
    Setting.b sneak;
    private Map<EntityPlayer, Integer> poplist;

    public void setup(){
        this.poplist = new ConcurrentHashMap<>();
        scale = this.registerD("Scale", "Scale",3.6, 0.1, 10.0);
        gamemode = this.registerB("Gamemode", "Gamemode",true);
        ping = this.registerB("Ping","Ping", true);
        health = this.registerB("Health","Health", true);
        sneak = this.registerB("SneakColor", "SneakColor",true);
        rainbowOutline = this.registerB("RainbowOutline", "RainbowOutline",true);
        totemPops = this.registerB("TotemPops", "TotemPops", true);
    }


    public void onWorldRender(RenderEvent event) {
        for (EntityPlayer p : mc.world.playerEntities) {
            if ((p != mc.getRenderViewEntity()) && (p.isEntityAlive())) {
                double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * ((IMinecraft) mc).getTimer().renderPartialTicks
                        - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * ((IMinecraft) mc).getTimer().renderPartialTicks
                        - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * ((IMinecraft) mc).getTimer().renderPartialTicks
                        - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
                if (!p.getName().startsWith("Body #")) {
                    renderNametag(p, pX, pY, pZ);
                }
            }
        }

    }

    private void renderNametag(EntityPlayer player, double x, double y, double z) {
        GL11.glPushMatrix();

        String nameS;
        String nameColor = "\u00A7f";
        if (Friends.isFriend(player.getName())) {
            nameColor = "\u00A7b";
        }

        if(Enemies.isEnemy(player.getName())) {
            nameColor = "\u00A7c";
        }

        if (sneak.getValue()) {
            if (player.isSneaking()) {
                nameColor = "\u00A75";
            }
        }

        nameS = (nameColor) + player.getName();
        String gamemodeS = "";
        if (gamemode.getValue()) {
            gamemodeS += "" + ChatFormatting.WHITE + getGMText(player) + "\u00A7f";
        }

        String pingS = "";
        if (ping.getValue()) {
            pingS += " " + ChatFormatting.WHITE + getPing(player) + "ms";
        }

        String healthS = " ";
        if (health.getValue()) {
            final float health = EntityUtil.totalHealth(player);
            String healthColor;
            if (health > 18.0f) {
                healthColor = "\u00A7a";
            }
            else if (health > 16.0f) {
                healthColor = "\u00A72";
            }
            else if (health > 12.0f) {
                healthColor = "\u00A7e";
            }
            else if (health > 8.0f) {
                healthColor = "\u00A76";
            }
            else if (health > 5.0f) {
                healthColor = "\u00A7c";
            }
            else {
                healthColor = "\u00A74";
            }
            healthS += healthColor + (MathHelper.ceil(player.getHealth() + player.getAbsorptionAmount()));
        }


        float var14 = 0.016666668F * getNametagSize(player);
        GL11.glTranslated((float) x, (float) y + 2.5D, (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-var14, -var14, var14);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GL11.glDisable(2929);

        customfont = ClickGuiModule.customFont.getValue();
        int width = FontUtils.getStringWidth(customfont, nameS + pingS + gamemodeS + healthS) / 2;
        if(customfont) {
            RenderUtil.drawBorderedRect(-width - 3, 8, width + 2.5, 21, 1.2, 0x75000000, rainbowOutline.getValue() ? RainbowUtil.rainbow(0) : new Color(0, 0, 0, 140).getRGB());
        } else {
            RenderUtil.drawBorderedRect(-width - 3, 8, width + 2, 21, 1.2, 0x75000000, rainbowOutline.getValue() ? RainbowUtil.rainbow(0) : new Color(0, 0, 0, 140).getRGB());
        }

        if (customfont) {
            Aurora.fontRenderer.drawStringWithShadow(nameS + pingS + gamemodeS + healthS, -width, 11.5, new Color(255,255,255,255).getRGB());

        } else {
            Wrapper.getMinecraft().fontRenderer.drawStringWithShadow(nameS + pingS + gamemodeS + healthS, -width, 10, new Color(255,255,255,255).getRGB());
        }

        int xOffset = 0;
        for (ItemStack armourStack : player.inventory.armorInventory) {
            if (armourStack != null) {
                xOffset -= 8;
            }
        }

        ItemStack renderStack;
        player.getHeldItemMainhand();
        xOffset -= 8;
        renderStack = player.getHeldItemMainhand().copy();
        renderItem(renderStack, xOffset, -10);
        xOffset += 16;
        for (int index = 3; index >= 0; --index) {
            ItemStack armourStack = player.inventory.armorInventory.get(index);
            ItemStack renderStack1 = armourStack.copy();

            renderItem(renderStack1, xOffset, -10);
            xOffset += 16;
        }

        ItemStack renderOffhand;
        player.getHeldItemOffhand();
        renderOffhand = player.getHeldItemOffhand().copy();
        renderItem(renderOffhand, xOffset, -10);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }



    private float getNametagSize(EntityLivingBase player) {
        ScaledResolution scaledRes = new ScaledResolution(mc);
        double twoDscale = scaledRes.getScaleFactor() / Math.pow(scaledRes.getScaleFactor(), 0.0D + scale.getValue());
        return (float) ((float) twoDscale + (mc.player.getDistance(player) / (0.8f * scale.getValue())));
    }



    public String getGMText(final EntityPlayer player) {
        if (player.isCreative()) {
            return " [C]";
        }
        if (player.isSpectator()) {
            return " [I]";
        }
        if (!player.isAllowEdit() && !player.isSpectator()) {
            return " [A]";
        }
        if (!player.isCreative() && !player.isSpectator() && player.isAllowEdit()) {
            return " [S]";
        }
        return "";
    }

    private void renderItem(ItemStack stack, int x, int y) {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        mc.getRenderItem().zLevel = -100.0F;
        GlStateManager.scale(1, 1, 0.01f);
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, (y / 2) - 12);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, (y / 2) - 12);
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.scale(1, 1, 1);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        renderEnchantText(stack, x, y - 18);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    public int getPing(final EntityPlayer player) {
        int ping = 0;
        try {
            ping = (int)MathUtil.clamp((float) Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1, 300.0f);
        }
        catch (NullPointerException ignored) {}
        return ping;
    }


    private void renderEnchantText(ItemStack stack, int x, int y) {
        int encY = y - 24;
        int yCount = encY - -5;
        if (stack.getItem() instanceof ItemArmor || stack.getItem() instanceof ItemTool) {
            float green = ((float) stack.getMaxDamage() - (float) stack.getItemDamage()) / (float) stack.getMaxDamage();
            float red = 1 - green;
            int dmg = 100 - (int) (red * 100);
            FontUtils.drawStringWithShadow(customfont,dmg + "%", x * 2 + 8, y + 26, new Color((int) (red * 255), (int) (green * 255), 0).getRGB());
        }

        NBTTagList enchants = stack.getEnchantmentTagList();
        for (int index = 0; index < enchants.tagCount(); ++index) {
            short id = enchants.getCompoundTagAt(index).getShort("id");
            short level = enchants.getCompoundTagAt(index).getShort("lvl");
            Enchantment enc = Enchantment.getEnchantmentByID(id);
            if (enc != null) {
                String encName = enc.isCurse()
                        ? TextFormatting.WHITE
                        + enc.getTranslatedName(level).substring(11).substring(0, 1).toLowerCase()
                        : enc.getTranslatedName(level).substring(0, 1).toLowerCase();
                encName = encName + level;
                GL11.glPushMatrix();
                GL11.glScalef(0.9f, 0.9f, 0);
                FontUtils.drawStringWithShadow(customfont, encName, x * 2 + 13, yCount, -1);
                GL11.glScalef(2f, 2f, 2);
                GL11.glPopMatrix();
                encY += 8;
                yCount -= 10;
            }
        }
    }
}