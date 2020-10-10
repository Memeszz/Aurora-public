package me.memeszz.aurora.event;

/**
 * Author Seth
 * 4/6/2019 @ 1:27 AM.
 */

public class EventCancellable extends EventStageable
{

    private boolean canceled;

    public EventCancellable()
    {
    }

    public EventCancellable(EventStage stage)
    {
        super(stage);
    }

    public boolean isCanceled()
    {
        return canceled;
    }

    public void setCanceled(boolean canceled)
    {
        this.canceled = canceled;
    }
}