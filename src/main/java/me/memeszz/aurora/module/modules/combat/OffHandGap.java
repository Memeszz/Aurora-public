package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class OffHandGap extends Module {
    public OffHandGap() {
        super("OffHandGap", Category.Combat, "Attacks nearby players");
    }
    Setting.b crystalCheck;
    Setting.b totemdisable;
    Setting.d health;
    int gapples;
    public void setup() {
        totemdisable = this.registerB("TotemDisable", "TotemDisable",true);
        health = this.registerD("Health","Health", 13, 1, 36);
        crystalCheck = this.registerB("CrystalCheck", "CrystalCheck",false);
    }

    public void onEnable() {
        if (totemdisable.getValue()) {
            ModuleManager.getModuleByName("AutoTotem").disable();
        }
    }


    public void onDisable() {
        if (totemdisable.getValue()) {
            ModuleManager.getModuleByName("AutoTotem").enable();
        }
    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        gapples = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();

        if (mc.currentScreen instanceof GuiContainer || mc.world == null || mc.player == null)
            return;
        if (!shouldTotem()) {
            if (!(mc.player.getHeldItemOffhand() != ItemStack.EMPTY && mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE)) {
                final int slot = getGapSlot() < 9 ? getGapSlot() + 36 : getGapSlot();
                if (getGapSlot() != -1) {
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
        if (mc.player != null) {
            return (mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA  || mc.player.getHealth() + mc.player.getAbsorptionAmount() <= health.getValue() || (crystalCheck.getValue() && isGapplesAABBEmpty()));
        }
        return (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= health.getValue() || mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA || (crystalCheck.getValue() && isGapplesAABBEmpty());
    }

    private boolean isEmpty(BlockPos pos){
        return mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream().noneMatch(e -> e instanceof EntityEnderCrystal);
    }

    private boolean isGapplesAABBEmpty(){
        return !isEmpty(mc.player.getPosition().add(1, 0, 0)) || !isEmpty(mc.player.getPosition().add(-1, 0, 0)) || !isEmpty(mc.player.getPosition().add(0, 0, 1)) || !isEmpty(mc.player.getPosition().add(0, 0, -1)) || !isEmpty(mc.player.getPosition());
    }

    int getGapSlot() {
        int gapSlot = -1;
        for (int i = 45; i > 0; i--) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
                gapSlot = i;
                break;
            }
        }
        return gapSlot;
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
        return "\u00A77[\u00A7f" + gapples + "\u00A77]";
    }
}
