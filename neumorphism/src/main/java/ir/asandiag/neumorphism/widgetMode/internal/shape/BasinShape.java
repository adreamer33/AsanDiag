package ir.asandiag.neumorphism.widgetMode.internal.shape;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

import ir.asandiag.neumorphism.widgetMode.NeumorphShapeDrawable;

public class BasinShape implements Shape {
    private NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState;
    private final List<Shape> shadows = new ArrayList<>();

    public BasinShape(NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState) {
        shadows.add(new FlatShape(drawableState));
        shadows.add(new PressedShape(drawableState));
    }

    @Override
    public void setDrawableState(NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState) {
        for (Shape shape : shadows) {
            shape.setDrawableState(newDrawableState);
        }
    }

    @Override
    public void draw(Canvas canvas, Path outlinePath) {
        for (Shape shape : shadows) {
            shape.draw(canvas, outlinePath);
        }
    }

    @Override
    public void updateShadowBitmap(Rect bounds) {
        for (Shape shape : shadows) {
            shape.updateShadowBitmap(bounds);
        }
    }
}
