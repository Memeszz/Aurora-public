package me.memeszz.aurora.util.math;

public class StopwatchUtil {
    private static long previousMS;

    public StopwatchUtil() {
        reset();
    }

    public static boolean hasCompleted(long milliseconds) {
        return (getCurrentMS() - previousMS >= milliseconds);
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void reset() {
        previousMS = getCurrentMS();
    }
}
