package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.math.TimerUtil;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class AutoArmour extends Module {
    public AutoArmour() {
        super("AutoArmour", Category.Combat, "Puts on armor");
        delay = this.registerI("Delay", "Delay",50, 0, 1000);
        curse = this.registerB("Cursed", "Cursed",false);
    }

    Setting.b curse;
    Setting.i delay;

    private final TimerUtil timer = new TimerUtil();


    @Listener
    public void onUpdate(UpdateEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiInventory) {
            return;
        }

        final ItemStack helm = mc.player.inventoryContainer.getSlot(5).getStack();

        if (helm.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.HEAD);

            if (slot != -1) {
                this.clickSlot(slot);
            }
        }

        final ItemStack chest = mc.player.inventoryContainer.getSlot(6).getStack();

        if (chest.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.CHEST);

            if (slot != -1) {
                this.clickSlot(slot);
            }
        }

        final ItemStack legging = mc.player.inventoryContainer.getSlot(7).getStack();

        if (legging.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.LEGS);

            if (slot != -1) {
                this.clickSlot(slot);
            }
        }

        final ItemStack feet = mc.player.inventoryContainer.getSlot(8).getStack();

        if (feet.getItem() == Items.AIR) {
            final int slot = this.findArmorSlot(EntityEquipmentSlot.FEET);

            if (slot != -1) {
                this.clickSlot(slot);
            }
        }
    }



    private void clickSlot(int slot) {
        if (this.timer.passed(this.delay.getValue())) {
            Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, slot, 0, ClickType.QUICK_MOVE, Minecraft.getMinecraft().player);
            this.timer.reset();
        }
    }

    private int findArmorSlot(EntityEquipmentSlot type) {
        int slot = -1;
        float damage = 0;

        for (int i = 9; i < 45; i++) {
            final ItemStack s = Minecraft.getMinecraft().player.inventoryContainer.getSlot(i).getStack();
            if (s.getItem() != Items.AIR) {

                if (s.getItem() instanceof ItemArmor) {
                    final ItemArmor armor = (ItemArmor) s.getItem();
                    if (armor.armorType == type) {
                        final float currentDamage = (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));

                        final boolean cursed = this.curse.getValue() && (EnchantmentHelper.hasBindingCurse(s));

                        if (currentDamage > damage && !cursed) {
                            damage = currentDamage;
                            slot = i;
                        }
                    }
                }
            }
        }

        return slot;
    }

}