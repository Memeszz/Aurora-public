package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.Wrapper;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OffHandCrystal extends Module {
    public OffHandCrystal() {
        super("OffHandCrystal", Category.Combat, "Attacks nearby players");
    }


    Setting.b crystalCheck;
    Setting.b totemdisable;
    Setting.d health;
    Setting.b announceUsage2;
    public int crystals;

    public void setup() {
        announceUsage2 = this.registerB("Chat", "Chat",true);
        totemdisable = this.registerB("TotemDisable", "TotemDisable",false);
        health = this.registerD("Health","Health", 13, 1, 36);
        crystalCheck = this.registerB("CrystalCheck","CrystalCheck", false);
    }

    public void onEnable() {
        if (totemdisable.getValue()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
        if (announceUsage2.getValue()) {
            Wrapper.sendClientMessage("\u00A7aOffHandCrystal Enabled");
        }
    }

    public void onDisable() {
        if (totemdisable.getValue()) {
            ModuleManager.getModuleByName("AutoTotem").enable();
        }
        if (announceUsage2.getValue()) {
            Wrapper.sendClientMessage("\u00A7cOffHandCrystal Disabled");
        }
    }

    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }


    public void onTick() {
        if (mc.currentScreen instanceof GuiContainer) return;
        if (!shouldTotem() || getItems(Items.TOTEM_OF_UNDYING) == 0)  {
            if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)) {
                final int slot = getCrystalSlot() < 9 ? getCrystalSlot() + 36 : getCrystalSlot();
                if (getCrystalSlot() != -1) {
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                }
            }
        } else if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING)) {
            final int slot = getTotemSlot() < 9 ? getTotemSlot() + 36 : getTotemSlot();
            if (getTotemSlot() != -1) {
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
            }
        }
    }


    private boolean shouldTotem() {
        return (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= health.getValue() || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA || mc.player.fallDistance >= 3;
    }

    int getCrystalSlot() {
        int crystalSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                crystalSlot = i;
                break;
            }
        }
        return crystalSlot;
    }

    int getTotemSlot() {
        int totemSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                totemSlot = i;
                break;
            }
        }
        return totemSlot;
    }

    @Override
    public String getHudInfo() {
        return "\u00A77[\u00A7f" + crystals + "\u00A77]";
    }
}

