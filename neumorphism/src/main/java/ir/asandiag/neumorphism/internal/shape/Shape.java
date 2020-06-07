package ir.asandiag.neumorphism.internal.shape;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import ir.asandiag.neumorphism.NeumorphShapeDrawable;

public interface Shape {
    void setDrawableState(NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState);

    void draw(Canvas canvas, Path outlinePath);

    void updateShadowBitmap(Rect bounds);
}
