package me.memeszz.aurora.mixin.mixins;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Render.class, priority = 2147483647)
public abstract class MixinRender<T extends Entity> {
    @Shadow
    protected abstract boolean bindEntityTexture(T entity);


}
