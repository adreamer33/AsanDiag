package ir.asandiag.neumorphism.widgetMode.internal.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;

import ir.asandiag.neumorphism.widgetMode.NeumorphShapeDrawable;
import ir.asandiag.neumorphism.widgetMode.model.CornerFamily;

public class PressedShape implements Shape {
    private Bitmap shadowBitmap;
    private final GradientDrawable lightShadowDrawable = new GradientDrawable();
    private final GradientDrawable darkShadowDrawable = new GradientDrawable();
    private NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState;

    public PressedShape(NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState) {
        this.drawableState = drawableState;
    }

    @Override
    public void setDrawableState(NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState) {
        this.drawableState = newDrawableState;
    }

    @Override
    public void draw(Canvas canvas, Path outlinePath) {
        int checkpoint = canvas.save();
        canvas.clipPath(outlinePath);

        try {
            float left;
            float top;
            Rect padding = drawableState.padding;
            if (padding != null) {
                left = (float) padding.left;
                top = (float) padding.top;
            } else {
                left = 0f;
                top = 0f;
            }
            if (shadowBitmap!=null) {
                canvas.drawBitmap(shadowBitmap, left, top, null);
            }
        } finally {
            canvas.restoreToCount(checkpoint);
        }
    }

    @Override
    public void updateShadowBitmap(Rect bounds) {
        int shadowElevation = (int) this.drawableState.shadowElevation;
        int w = bounds.width();
        int h = bounds.height();
        int width = w + shadowElevation;
        int height = h + shadowElevation;

        lightShadowDrawable.setSize(width, height);
        lightShadowDrawable.setStroke(shadowElevation, drawableState.shadowColorLight);
        switch (drawableState.shapeAppearanceModel.getCornerFamily()) {
            case CornerFamily.OVAL:
                lightShadowDrawable.setShape(GradientDrawable.OVAL);
                break;
            case CornerFamily.ROUNDED:
                float cornerSize = Math.min(Math.min(w / 2f, h / 2f), drawableState.shapeAppearanceModel.getCornerSize());
                lightShadowDrawable.setShape(GradientDrawable.RECTANGLE);
                float array[] = new float[]{0f, 0f, 0f, 0f, cornerSize, cornerSize, 0f, 0f};
                lightShadowDrawable.setCornerRadii(array);
                break;
        }

        darkShadowDrawable.setSize(width, height);
        darkShadowDrawable.setStroke(shadowElevation, drawableState.shadowColorDark);
        switch (drawableState.shapeAppearanceModel.getCornerFamily()) {
            case CornerFamily.OVAL:
                darkShadowDrawable.setShape(GradientDrawable.OVAL);
                break;
            case CornerFamily.ROUNDED:
                float cornerSize = Math.min(Math.min(w / 2f, h / 2f), drawableState.shapeAppearanceModel.getCornerSize());
                darkShadowDrawable.setShape(GradientDrawable.RECTANGLE);
                float array[] = new float[]{cornerSize, cornerSize, 0f, 0f, 0f, 0f, 0f, 0f};
                darkShadowDrawable.setCornerRadii(array);
                break;
        }

        lightShadowDrawable.setSize(width, height);
        lightShadowDrawable.setBounds(0, 0, width, height);
        darkShadowDrawable.setSize(width, height);
        darkShadowDrawable.setBounds(0, 0, width, height);
        shadowBitmap = generateShadowBitmap(w, h);
    }

    private Bitmap generateShadowBitmap(int w, int h) {
        float shadowElevation = drawableState.shadowElevation;
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        int checkpoint = canvas.save();
        try {
            int checkpoint1 = canvas.save();
            try {
                canvas.translate(-shadowElevation, -shadowElevation);
                lightShadowDrawable.draw(canvas);
            } finally {
                canvas.restoreToCount(checkpoint1);
            }
            darkShadowDrawable.draw(canvas);
        } finally {
            canvas.restoreToCount(checkpoint);
        }

        bitmap = blurred(bitmap);
        return bitmap;
    }

    public Bitmap blurred(Bitmap bitmap) {
        if (drawableState.inEditMode) {
            return bitmap;
        }
        return drawableState.blurProvider.blur(bitmap, -1, -1);
    }
}
