package me.memeszz.aurora.util.math;

public final class TimerUtil {

    private long time;

    public TimerUtil() {
        time = -1;
    }

    public boolean passed(double ms) {
        return System.currentTimeMillis() - this.time >= ms;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

}