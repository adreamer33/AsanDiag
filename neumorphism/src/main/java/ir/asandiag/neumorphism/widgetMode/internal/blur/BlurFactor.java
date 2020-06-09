package ir.asandiag.neumorphism.widgetMode.internal.blur;

import android.graphics.Color;

public class BlurFactor {
    public static final int DEFAULT_RADIUS = 25;
    public static final int DEFAULT_SAMPLING = 1;

    public int width;
    public int height;
    public int radius = DEFAULT_RADIUS;
    public int sampling = DEFAULT_SAMPLING;
    public int color = Color.TRANSPARENT;

    public BlurFactor(int width, int height, int radius, int sampling, int color) {
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.sampling = sampling;
        this.color = color;
    }

    public BlurFactor(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BlurFactor(int width, int height, int color) {
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public BlurFactor(int width, int height, int radius, int sampling) {
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.sampling = sampling;
    }
}
