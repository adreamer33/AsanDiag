package ir.asandiag.neumorphism.widgetMode.internal.shape;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import ir.asandiag.neumorphism.widgetMode.NeumorphShapeDrawable;

public interface Shape {
    void setDrawableState(NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState);

    void draw(Canvas canvas, Path outlinePath);

    void updateShadowBitmap(Rect bounds);
}
