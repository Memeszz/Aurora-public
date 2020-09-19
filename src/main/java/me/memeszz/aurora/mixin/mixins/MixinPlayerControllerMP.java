package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.DestroyBlockEvent;
import me.memeszz.aurora.event.events.EventPlayerClickBlock;
import me.memeszz.aurora.event.events.EventPlayerDamageBlock;
import me.memeszz.aurora.event.events.EventPlayerResetBlockRemoving;
import me.memeszz.aurora.mixin.accessor.IPlayerControllerMP;
import me.memeszz.aurora.module.ModuleManager;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements IPlayerControllerMP {

    @Accessor @Override public abstract void setBlockHitDelay(int delay);
    @Accessor @Override public abstract void setIsHittingBlock(boolean hittingBlock);
    @Accessor @Override public abstract float getCurBlockDamageMP();
    @Accessor @Override public abstract void setCurBlockDamageMP(float blockDamageMP);

    @Inject(method = "onPlayerDestroyBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playEvent(ILnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onPlayerDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        Aurora.getInstance().getEventManager().dispatchEvent(new DestroyBlockEvent(pos));
    }

    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    public void resetBlockRemoving(CallbackInfo p_Info) {
        EventPlayerResetBlockRemoving l_Event = new EventPlayerResetBlockRemoving();

        Aurora.getInstance().getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled() || ModuleManager.isModuleEnabled("MultiTask")) {
            p_Info.cancel();
        }
    }

    @Inject(method = "clickBlock", at = @At("HEAD"), cancellable = true)
    public void clickBlock(BlockPos loc, EnumFacing face, CallbackInfoReturnable<Boolean> callback) {
        EventPlayerClickBlock l_Event = new EventPlayerClickBlock(loc, face);

        Aurora.getInstance().getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled()) {
            callback.setReturnValue(false);
            callback.cancel();
        }
    }

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing, CallbackInfoReturnable<Boolean> p_Info) {
        EventPlayerDamageBlock l_Event = new EventPlayerDamageBlock(posBlock, directionFacing);
        Aurora.getInstance().getEventManager().dispatchEvent(l_Event);
        if (l_Event.isCanceled()) {
            p_Info.setReturnValue(false);
            p_Info.cancel();
        }
    }
}