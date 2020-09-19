package me.memeszz.aurora.util.colour;

import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by 086 on 22/01/2018.
 */
public class HueCycler {

    int index = 0;
    int[] cycles;

    public HueCycler(int cycles) {
        if (cycles<=0) throw new IllegalArgumentException("cycles <= 0");
        this.cycles = new int[cycles];
        double hue = 0;
        double add = 1/(double)cycles;
        for (int i = 0; i < cycles; i++) {
            this.cycles[i] = Color.HSBtoRGB((float) hue, 1,1);
            hue += add;
        }
    }

    public void reset() {
        index = 0;
    }

    public void reset(int index) {
        this.index = index;
    }

    public void setNext() {
    }

    public void set() {
        int rgb = cycles[index];
        float red = ((rgb >> 16) & 0xFF)/255f;
        float green = ((rgb >> 8) & 0xFF)/255f;
        float blue = (rgb & 0xFF)/255f;
        GL11.glColor3f(red, green, blue);
    }

}