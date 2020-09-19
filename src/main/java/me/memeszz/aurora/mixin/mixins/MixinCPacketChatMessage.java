package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.mixin.accessor.ICPacketChatMessage;
import net.minecraft.network.play.client.CPacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketChatMessage.class)
public abstract class MixinCPacketChatMessage implements ICPacketChatMessage {

    @Accessor @Override public abstract void setMessage(String message);
}
