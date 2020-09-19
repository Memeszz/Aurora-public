package me.memeszz.aurora.mixin.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;

public interface ICPacketUseEntity {

    void getEntityId(int entityId);

    void getEntityAction(CPacketUseEntity.Action action);
}
