package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.module.modules.render.ESP;
import me.memeszz.aurora.util.friends.Friends;
import me.memeszz.aurora.util.render.OutlineUtils;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;


@Mixin(RenderLivingBase.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> extends Render<T> {

    @Shadow
    protected ModelBase mainModel;

    protected MixinRendererLivingEntity() {
        super(null);
    }



    /**
     * @author
     */
    @Overwrite
    protected void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor) {
        boolean isPlayer = entitylivingbaseIn instanceof EntityPlayer;

        if (!bindEntityTexture(entitylivingbaseIn)) {
            return;
        }


        Minecraft mc = Minecraft.getMinecraft();
        boolean fancyGraphics = mc.gameSettings.fancyGraphics;
        mc.gameSettings.fancyGraphics = false;

        float gamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100000F;
        if (ModuleManager.isModuleEnabled("ESP")) {
            switch (ESP.mode.getValue()) {
                case "WireFrame":
                    if (isPlayer) {
                        GL11.glPushMatrix();
                        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                        GL11.glEnable(GL11.GL_LINE_SMOOTH);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        Color n = new Color(255, 0, 0);
                        if (Friends.isFriend(entitylivingbaseIn.getName())) {
                            n = new Color(5, 218, 255, 255);
                        }
                        RenderUtil.color(n.getRGB());
                        GL11.glLineWidth((float) ESP.width.getValue());
                        mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        GL11.glPopAttrib();
                        GL11.glPopMatrix();
                        break;
                    }

                case "OutLine":
                    boolean player = entitylivingbaseIn instanceof EntityPlayer && entitylivingbaseIn != Minecraft.getMinecraft().player;
                    if (player) {
                        Color n = new Color(255, 0, 0);
                        if (Friends.isFriend(entitylivingbaseIn.getName())) {
                            n = new Color(5, 218, 255, 255);
                        }
                        OutlineUtils.setColor(n);
                        mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        OutlineUtils.renderOne((float) ESP.width.getValue());
                        mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        OutlineUtils.renderTwo();
                        mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        OutlineUtils.renderThree();
                        OutlineUtils.renderFour();
                        OutlineUtils.setColor(n);
                        mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        OutlineUtils.renderFive();
                        OutlineUtils.setColor(Color.WHITE);
                        break;

                    }
            }
        }


        mc.gameSettings.fancyGraphics = fancyGraphics;
        mc.gameSettings.gammaSetting = gamma;


        if (!ESP.mode.getValue().equalsIgnoreCase("Wireframe") || !ModuleManager.isModuleEnabled("ESP") || !isPlayer) {
            this.mainModel.render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
        }

    }
}




    /*/**
     * @author auto / pharmacies
     * auto made these for hummingbird
     */


    /*
    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo info) {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);
        if (flag || flag1) {
            if (!this.bindEntityTexture((T) entitylivingbaseIn)) {
                return;
            }

            if (flag1) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }


            if (ModuleManager.isModuleEnabled("CsgoChams") &&  entitylivingbaseIn != Minecraft.getMinecraft().player) {
                glEnable(GL_LINE_SMOOTH);
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthMask(false);
                GL11.glLineWidth(CsgoChams.width.getValue());
                GL11.glEnable(2960);
                GL11.glClear(1024);
                GL11.glClearStencil(15);
                GL11.glStencilFunc(512, 1, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1028, 6913);
                GL11.glStencilFunc(512, 0, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1028, 6914);
                GL11.glStencilFunc(514, 1, 15);
                GL11.glStencilOp(7680, 7680, 7680);
                GL11.glPolygonMode(1028, 6913);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GL11.glEnable(10754);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1, 0, 0, 1);
                if (Friends.isFriend(entitylivingbaseIn.getName())) {
                    GL11.glColor4d(((float) 0 / 255), ((float) 255 / 255), ((float) 0 / 255), ((float) 72 / 255));
                } else {
                    GL11.glColor4d(((float) CsgoChams.red.getValue() / 255), ((float) CsgoChams.green.getValue() / 255), ((float) CsgoChams.blue.getValue() / 255), ((float) 72 / 255));
                }
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
                glDisable(GL_LINE_SMOOTH);
                info.cancel();
            } else {
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }

            if (flag1) {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }

    }*/
