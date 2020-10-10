package me.memeszz.aurora.event.events;

import me.memeszz.aurora.event.EventCancellable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class EventPlayerClickBlock extends EventCancellable
{
    public BlockPos Location;
    public EnumFacing Facing;

    public EventPlayerClickBlock(BlockPos loc, EnumFacing face)
    {
        Location = loc;
        Facing = face;
    }
}