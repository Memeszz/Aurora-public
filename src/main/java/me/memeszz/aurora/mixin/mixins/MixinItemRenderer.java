package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.mixin.accessor.IItemRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer implements IItemRenderer {

    @Accessor @Override public abstract float getPrevEquippedProgressMainHand();
    @Accessor @Override public abstract void setEquippedProgressMainHand(float progress);
    @Accessor @Override public abstract float getPrevEquippedProgressOffHand();
    @Accessor @Override public abstract void setEquippedProgressOffHand(float progress);
    @Accessor @Override public abstract void setItemStackMainHand(ItemStack stack);
    @Accessor @Override public abstract void setItemStackOffHand(ItemStack stack);




}
