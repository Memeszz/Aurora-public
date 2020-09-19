package me.memeszz.aurora.util.colour;

import java.awt.*;

public class RainbowUtil {

    public static int y;
    /**
     *@author superblaubeere27
     *https://www.youtube.com/watch?v=fkYmNfvNtvgy
     */


    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.70f, 1f).getRGB();
    }

    public static Color rainbowColor(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f);
    }

    public int color(int index, int count) {
        float[] hsb = new float[3];

        Color.RGBtoHSB(255, 255, 255, hsb);

        float brightness = Math.abs(((getOffset() + (index / (float) count) * 2) % 2) - 1);
        brightness = 0.5f + (0.4f * brightness);

        hsb[2] = brightness % 1f;
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    private float getOffset() {
        return (System.currentTimeMillis() % 2000) / 1000f;
    }

}
