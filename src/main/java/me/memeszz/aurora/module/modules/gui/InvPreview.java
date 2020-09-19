package me.memeszz.aurora.module.modules.gui;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class InvPreview extends Module {
    Setting.i xSetting;
    Setting.i ySetting;
    
    public InvPreview() {
        super("InventoryPreview", Category.Gui);
        xSetting = this.registerI("X","X",  784, 0, 1000);
        ySetting = this.registerI("Y", "Y",46, 0,1000);
    }

    public void onRender() {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        RenderUtil.drawBorderedRect(xSetting.getValue(), ySetting.getValue(), xSetting.getValue() + 145, ySetting.getValue() + 48, 1, 0x75101010, 0x90000000);
        for (int i = 0; i < 27; i++) {
            final ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);
            int offsetX = (xSetting.getValue() + (i % 9) * 16);
            int offsetY = (ySetting.getValue() + (i / 9) * 16);
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }
        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}