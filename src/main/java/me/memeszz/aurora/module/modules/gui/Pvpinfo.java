package me.memeszz.aurora.module.modules.gui;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.render.RenderUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/**
 * @author hollow/h0lloww
 * for aurora on 24/07/2020
 */


public class Pvpinfo extends Module {
    public Pvpinfo() {
        super("PvPInfo", Category.Gui);
        setDrawn(false);
    }

    static Setting.i x;
    static Setting.i y;
    Setting.b customFont;

    public void setup() {
        x = this.registerI("X", "X",900, 0, 1920);
        y = this.registerI("Y","Y",  500, 0, 1080);
        customFont = this.registerB("CustomFont","CustomFont", true);
    }

    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

    public String getTotemsStr() {
        return String.valueOf(getItems(Items.TOTEM_OF_UNDYING));
    }

    public int getTotems() {
        return getItems(Items.TOTEM_OF_UNDYING);
    }




    public void onRender() {
        if (mc.player == null || mc.world == null) return;
        int totemColor;
        sexyRainbow();
        if (getTotems() < 1) {
            totemColor = 16711680;
            } else {
            totemColor = 65280;
        }

        FontUtils.drawStringWithShadow(customFont.getValue(), getTotemsStr(), x.getValue(), y.getValue(), totemColor);


        int enabledC = 65280;
        int disabledC = 16711680;


        if (ModuleManager.getModuleByName("AutoCrystal").isEnabled()) {
            FontUtils.drawStringWithShadow(customFont.getValue(),"CA", x.getValue(), y.getValue() + 10, enabledC);
        } else {
            FontUtils.drawStringWithShadow(customFont.getValue(),"CA", x.getValue(), y.getValue() + 10, disabledC);
        }

        if (ModuleManager.getModuleByName("AutoTrap").isEnabled()) {
            FontUtils.drawStringWithShadow(customFont.getValue(), "AT", x.getValue(), y.getValue() + 20, enabledC);
        } else {
            FontUtils.drawStringWithShadow(customFont.getValue(), "AT", x.getValue(), y.getValue() + 20, disabledC);
        }

        if (ModuleManager.getModuleByName("Surround").isEnabled()) {
            FontUtils.drawStringWithShadow(customFont.getValue(),"SD", x.getValue(), y.getValue() - 10, enabledC);
        } else {
            FontUtils.drawStringWithShadow(customFont.getValue(), "SD", x.getValue(), y.getValue() - 10, disabledC);
        }


        if (ModuleManager.getModuleByName("KillAura").isEnabled()) {
            FontUtils.drawStringWithShadow(customFont.getValue(), "KA", x.getValue(), y.getValue() - 20, enabledC);
        } else {
            FontUtils.drawStringWithShadow(customFont.getValue(), "KA", x.getValue(), y.getValue() - 20, disabledC);
        }

    }

    public static void sexyRainbow() {
        String watermark2 = "aurora";
        int x1 = 0;

        for (int i = 0; i < watermark2.length(); i++) {
            char ch = watermark2.charAt(i);
            FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), String.valueOf(ch), x.getValue() + x1, y.getValue() - 30, RenderUtil.generateRainbowFadingColor(watermark2.length() - 1 - i, true));
            x1 += FontUtils.getStringWidth(ClickGuiModule.customFont.getValue(), String.valueOf(ch));
        }
    }
}