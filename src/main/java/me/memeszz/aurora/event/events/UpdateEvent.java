package me.memeszz.aurora.event.events;

import me.memeszz.aurora.event.EventCancellable;

public class UpdateEvent extends EventCancellable
{
    public float yaw, pitch;
    private double x, y, z;
    private boolean onGround;

    public UpdateEvent(EventStage stage, float yaw, float pitch, double x, double y, double z, boolean onGround)
    {
        super(stage);
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }

    public UpdateEvent()
    {

    }

    public boolean isOnGround()
    {
        return onGround;
    }

    public void setOnGround(boolean onGround)
    {
        this.onGround = onGround;
    }

    public float getYaw()
    {
        return yaw;
    }

    public void setYaw(float yaw)
    {
        this.yaw = yaw;
    }

    public float getPitch()
    {
        return pitch;
    }

    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

}