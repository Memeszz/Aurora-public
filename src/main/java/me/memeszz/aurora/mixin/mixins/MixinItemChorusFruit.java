package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.EventChorusTeleport;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemChorusFruit.class, priority = 29999)
public abstract class MixinItemChorusFruit {

    @Inject(method = "onItemUseFinish", at = @At("HEAD"), cancellable = true)
    public void onUpdate(ItemStack stack, World worldIn, EntityLivingBase entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        EventChorusTeleport event = new EventChorusTeleport();
        Aurora.getInstance().getEventManager().dispatchEvent(event);
    }
}
