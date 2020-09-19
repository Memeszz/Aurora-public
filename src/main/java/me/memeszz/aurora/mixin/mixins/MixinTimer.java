package me.memeszz.aurora.mixin.mixins;

import me.memeszz.aurora.mixin.accessor.ITimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public abstract class MixinTimer implements ITimer {

    @Accessor @Override public abstract void setTickLength(float tickLength);
    @Accessor @Override public abstract float getTickLength();
}
