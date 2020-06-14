package ir.asandiag.neumorphism.widgetMode.internal.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.asandiag.neumorphism.widgetMode.NeumorphShapeDrawable;
import ir.asandiag.neumorphism.widgetMode.internal.util.CanvasCompact;
import ir.asandiag.neumorphism.widgetMode.model.CornerFamily;
import ir.asandiag.neumorphism.widgetMode.model.NeumorphShapeAppearanceModel;

public class FlatShape implements Shape {
    @Nullable
    private Bitmap lightShadowBitmap;
    @Nullable
    private Bitmap darkShadowBitmap;
    private final GradientDrawable lightShadowDrawable = new GradientDrawable();
    private final GradientDrawable darkShadowDrawable = new GradientDrawable();
    private NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState;


    public FlatShape(NeumorphShapeDrawable.NeumorphShapeDrawableState drawableState) {
        this.drawableState = drawableState;
    }

    @Override
    public void setDrawableState(NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState) {
        this.drawableState = newDrawableState;
    }

    @Override
    public void draw(@NonNull Canvas canvas, @NonNull Path outlinePath) {
        int checkpoint = canvas.save();
        CanvasCompact.clipOutPath(canvas, outlinePath);

        try {
            float elevation = drawableState.shadowElevation;
            float z = this.drawableState.shadowElevation + this.drawableState.translationZ;
            float left = 0.0F;
            float top = 0.0F;
            Rect padding = this.drawableState.padding;
            if (padding != null) {
                left = (float) padding.left;
                top = (float) padding.top;
            } else {
                left = 0.0F;
                top = 0.0F;
            }

            float offset = -elevation - z;
            canvas.drawBitmap(lightShadowBitmap, offset + left, offset + top, null);

            offset = -elevation + z;
            canvas.drawBitmap(darkShadowBitmap, offset + left, offset + top, null);
        } finally {
            canvas.restoreToCount(checkpoint);
        }

    }

    @Override
    public void updateShadowBitmap(@NonNull Rect bounds) {
        lightShadowDrawable.setColor(drawableState.shadowColorLight);
        setCornerShape(lightShadowDrawable, drawableState.shapeAppearanceModel);

        darkShadowDrawable.setColor(drawableState.shadowColorDark);
        setCornerShape(darkShadowDrawable, drawableState.shapeAppearanceModel);


        int w = bounds.width();
        int h = bounds.height();
        this.lightShadowDrawable.setSize(w, h);
        this.lightShadowDrawable.setBounds(0, 0, w, h);
        this.darkShadowDrawable.setSize(w, h);
        this.darkShadowDrawable.setBounds(0, 0, w, h);
        this.lightShadowBitmap = toBlurredBitmap(lightShadowDrawable, w, h);
        this.darkShadowBitmap = toBlurredBitmap(darkShadowDrawable, w, h);
    }

    @Nullable
    public Bitmap toBlurredBitmap(@NonNull Drawable drawable, int w, int h) {
        float shadowElevation = drawableState.shadowElevation;
        int width = Math.round((float) w + shadowElevation * (float) 2);
        int height = Math.round((float) h + shadowElevation * (float) 2);

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int checkpoint = canvas.save();
        canvas.translate(shadowElevation, shadowElevation);

        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.RED);

//        canvas.drawCircle(50, 50, 100, fillPaint);
//        canvas.drawColor(Color.RED);
        try {
            drawable.draw(canvas);
        } finally {
            canvas.restoreToCount(checkpoint);
        }
        Bitmap bitmap1 = blurred(bitmap);
        return bitmap1;
    }

    public void setCornerShape(@NonNull GradientDrawable gradientDrawable, @NonNull NeumorphShapeAppearanceModel shapeAppearanceModel) {
        switch (shapeAppearanceModel.getCornerFamily()) {
            case CornerFamily.OVAL:
                gradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            case CornerFamily.ROUNDED:
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                float it = shapeAppearanceModel.getCornerSize();
//                float it = 14;
                float[] array = new float[]{it, it, it, it, it, it, it, it};
                gradientDrawable.setCornerRadii(array);
                break;
        }
    }


    @Nullable
    public Bitmap blurred(@NonNull Bitmap bitmap) {
        if (drawableState.inEditMode) {
            return bitmap;
        }
        return drawableState.blurProvider.blur(bitmap, -1, -1);
    }
}
