package me.memeszz.aurora.mixin.accessor;

import net.minecraft.util.EnumFacing;

public interface ICPacketPlayerTryUseItemOnBlock {
    void setPlacedBlockDirection(EnumFacing facing);

}
