package me.memeszz.aurora.mixin.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.memeszz.aurora.Aurora;
import me.memeszz.aurora.event.events.PacketEvent;
import me.memeszz.aurora.util.misc.TickRate;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.util.text.ITextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        PacketEvent.Send event = new PacketEvent.Send(packet);
        Aurora.getInstance().getEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void onChannelRead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        TickRate.update(packet);
        PacketEvent.Receive event = new PacketEvent.Receive(packet);
        Aurora.getInstance().getEventManager().dispatchEvent(event);
        if (event.isCanceled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "closeChannel", at = @At("HEAD"))
    public void preCloseChannel(ITextComponent message, CallbackInfo callbackInfo) {
        TickRate.reset();
    }
}
