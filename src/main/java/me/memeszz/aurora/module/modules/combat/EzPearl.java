package me.memeszz.aurora.module.modules.combat;

import me.memeszz.aurora.event.events.UpdateEvent;
import me.memeszz.aurora.mixin.accessor.IMinecraft;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.util.Wrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

public class EzPearl extends Module {
    public EzPearl() {
        super("EasyPearl", Category.Combat, "Throws a pearl");
    }
    private int playerHotbarSlot = -1;
    private int lastHotbarSlot = -1;
    private int delay;
    public void onEnable() {
        this.playerHotbarSlot = EzPearl.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
    }
    public void onDisable() {
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            AutoTrap.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        delay = 0;

    }

    @Listener
    public void onUpdate(UpdateEvent event) {
        delay++;
        int pearlSlot = this.findPearlInHotbar();
        if (pearlSlot == -1) {
            this.disable();
        }
        if (this.lastHotbarSlot != pearlSlot) {
            EzPearl.mc.player.inventory.currentItem = pearlSlot;
            this.lastHotbarSlot = pearlSlot;
        }
        if (delay > 1) {
           ((IMinecraft) mc).clickMouse();
            delay = 0;
            this.toggle();
        }
    }

    private int findPearlInHotbar() {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            Item item;
            ItemStack stack = Wrapper.getPlayer().inventory.getStackInSlot(i);
            if (stack == ItemStack.EMPTY || !((item = stack.getItem()) instanceof ItemEnderPearl)) continue;
            slot = i;
            break;
        }
        return slot;
    }
}
