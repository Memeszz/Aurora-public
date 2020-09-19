package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.mixin.accessor.ICPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CPacketPlayerTryUseItemOnBlock.class)
public abstract class MixinCPacketPlayerTryUseItemOnBlock implements ICPacketPlayerTryUseItemOnBlock {

    @Accessor @Override public abstract void setPlacedBlockDirection(EnumFacing facing);

}
