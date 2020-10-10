package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.mixin.accessor.ICPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketUseEntity.class)
public abstract class MixinCPacketUseEntity implements ICPacketUseEntity {

    @Shadow
    protected CPacketUseEntity.Action action;

    @Shadow
    protected int entityId;
}
