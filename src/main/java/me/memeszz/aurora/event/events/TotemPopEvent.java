package me.memeszz.aurora.event.events;

import net.minecraft.entity.Entity;


public class TotemPopEvent {

    private final Entity entity;

    public TotemPopEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

}