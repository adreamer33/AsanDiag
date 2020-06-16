package ir.asandiag.neumorphism.widgetMode;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import ir.asandiag.neumorphism.widgetMode.internal.blur.BlurProvider;
import ir.asandiag.neumorphism.widgetMode.internal.shape.BasinShape;
import ir.asandiag.neumorphism.widgetMode.internal.shape.FlatShape;
import ir.asandiag.neumorphism.widgetMode.internal.shape.PressedShape;
import ir.asandiag.neumorphism.widgetMode.internal.shape.Shape;
import ir.asandiag.neumorphism.widgetMode.model.CornerFamily;
import ir.asandiag.neumorphism.widgetMode.model.NeumorphShapeAppearanceModel;
import ir.asandiag.neumorphism.widgetMode.model.ShapeType;

public class NeumorphShapeDrawable extends Drawable {
    private NeumorphShapeDrawableState drawableState;

    private boolean dirty = false;

    private Paint fillPaint;
    private Paint strokePaint;

    private final RectF rectF = new RectF();

    private final Path outlinePath = new Path();
    private Shape shadow;

    public NeumorphShapeDrawable(@NonNull Context context) {
        this(new NeumorphShapeAppearanceModel(), new BlurProvider(context));
        initPaints();
    }


    public NeumorphShapeDrawable(@NonNull NeumorphShapeDrawableState drawableState) {
        this.drawableState = drawableState;
        this.shadow = shadowOf(drawableState.shapeType, drawableState);
        initPaints();
    }

    public NeumorphShapeDrawable(@NotNull NeumorphShapeAppearanceModel shapeAppearanceModel,
                                 @NotNull BlurProvider blurProvider) {
        this(new NeumorphShapeDrawableState(shapeAppearanceModel, blurProvider));
    }

    public NeumorphShapeDrawable(@NonNull Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        this(NeumorphShapeAppearanceModel.builder(context, attrs, defStyle, defStyleRes).build(),
                new BlurProvider(context));
    }

    private void initPaints() {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.TRANSPARENT);

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.TRANSPARENT);
    }

    @NonNull
    private Shape shadowOf(@ShapeType int shapeType, NeumorphShapeDrawableState drawableState) {
        switch (shapeType) {
            case ShapeType.FLAT:
                return new FlatShape(drawableState);
            case ShapeType.PRESSED:
                return new PressedShape(drawableState);
            case ShapeType.BASIN:
                return new BasinShape(drawableState);
            default:
                throw new IllegalArgumentException("ShapeType($shapeType) is invalid.");
        }
    }


    @Nullable
    public ConstantState getConstantState() {
        return drawableState;
    }

    @NotNull
    public Drawable mutate() {
        NeumorphShapeDrawable.NeumorphShapeDrawableState newDrawableState = new NeumorphShapeDrawable.NeumorphShapeDrawableState(this.drawableState);
        drawableState = newDrawableState;
        if (shadow != null) {
            shadow.setDrawableState(newDrawableState);
        }
        return this;
    }


    public final void setShapeAppearanceModel(@NotNull NeumorphShapeAppearanceModel shapeAppearanceModel) {
        drawableState.shapeAppearanceModel = shapeAppearanceModel;
        invalidateSelf();
    }

    @NotNull
    public final NeumorphShapeAppearanceModel getShapeAppearanceModel() {
        return drawableState.shapeAppearanceModel;
    }

    public final void setFillColor(@Nullable ColorStateList fillColor) {
        if (drawableState.fillColor != fillColor) {
            drawableState.fillColor = fillColor;
            onStateChange(getState());
        }
    }

    @Nullable
    public final ColorStateList getFillColor() {
        return this.drawableState.fillColor;
    }

    public final void setStrokeColor(@Nullable ColorStateList strokeColor) {
        if (drawableState.strokeColor != strokeColor) {
            drawableState.strokeColor = strokeColor;
            onStateChange(getState());
        }
    }

    @Nullable
    public final ColorStateList getStrokeColor() {
        return this.drawableState.strokeColor;
    }


    public final void setStroke(float strokeWidth, @ColorInt int strokeColor) {
        this.setStrokeWidth(strokeWidth);
        this.setStrokeColor(ColorStateList.valueOf(strokeColor));
    }

    public final void setStroke(float strokeWidth, @org.jetbrains.annotations.Nullable ColorStateList strokeColor) {
        this.setStrokeWidth(strokeWidth);
        this.setStrokeColor(strokeColor);
    }

    public final float getStrokeWidth() {
        return this.drawableState.strokeWidth;
    }

    public final void setStrokeWidth(float strokeWidth) {
        this.drawableState.strokeWidth = strokeWidth;
        this.invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        if (this.drawableState.alpha != alpha) {
            this.drawableState.alpha = alpha;
            this.invalidateSelfIgnoreShape();
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @NonNull
    private Rect getBoundsInternal() {
        Rect padding = drawableState.padding;
        if (padding != null) {
            Rect bounds = super.getBounds();
            return new Rect(bounds.left + padding.left,
                    bounds.top + padding.top,
                    bounds.right - padding.right,
                    bounds.bottom - padding.bottom);
        } else {
            return super.getBounds();
        }
    }

    @NonNull
    private RectF getBoundsAsRectF() {
        rectF.set(getBoundsInternal());
        return rectF;
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        if (drawableState.padding == null) {
            drawableState.padding = new Rect();
        }
        drawableState.padding.set(left, top, right, bottom);
        invalidateSelf();
    }

    public final void setShapeType(@ShapeType int shapeType) {
        if (drawableState.shapeType != shapeType) {
            drawableState.shapeType = shapeType;
            shadow = this.shadowOf(shapeType, drawableState);
            invalidateSelf();
        }

    }

    @ShapeType
    public final int getShapeType() {
        return this.drawableState.shapeType;
    }

    public final void setShadowElevation(float shadowElevation) {
        if (this.drawableState.shadowElevation != shadowElevation) {
            this.drawableState.shadowElevation = shadowElevation;
            this.invalidateSelf();
        }

    }

    public final float getShadowElevation() {
        return this.drawableState.shadowElevation;
    }

    public final void setShadowColorLight(@ColorInt int shadowColor) {
        if (this.drawableState.shadowColorLight != shadowColor) {
            this.drawableState.shadowColorLight = shadowColor;
            this.invalidateSelf();
        }

    }

    public final void setShadowColorDark(@ColorInt int shadowColor) {
        if (this.drawableState.shadowColorDark != shadowColor) {
            this.drawableState.shadowColorDark = shadowColor;
            this.invalidateSelf();
        }

    }

    public final float getTranslationZ() {
        return drawableState.translationZ;
    }

    public final void setTranslationZ(float translationZ) {
        if (this.drawableState.translationZ != translationZ) {
            this.drawableState.translationZ = translationZ;
            this.invalidateSelfIgnoreShape();
        }
    }

    public final float getZ() {
        return this.getShadowElevation() + this.getTranslationZ();
    }

    public final void setZ(float z) {
        this.setTranslationZ(z - this.getShadowElevation());
    }

    @Override
    public void invalidateSelf() {
        dirty = true;
        super.invalidateSelf();
    }

    private void invalidateSelfIgnoreShape() {
        super.invalidateSelf();
    }

    public Paint.Style getPaintStyle() {
        return drawableState.paintStyle;
    }

    public void setPaintStyle(Paint.Style paintStyle) {
        drawableState.paintStyle = paintStyle;
        invalidateSelfIgnoreShape();
    }

    private boolean hasFill() {
        return (drawableState.paintStyle == Paint.Style.FILL_AND_STROKE
                || drawableState.paintStyle == Paint.Style.FILL);
    }

    private boolean hasStroke() {
        return ((drawableState.paintStyle == Paint.Style.FILL_AND_STROKE
                || drawableState.paintStyle == Paint.Style.STROKE)
                && strokePaint.getStrokeWidth() > 0);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        dirty = true;
        super.onBoundsChange(bounds);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int prevAlpha = fillPaint.getAlpha();
        fillPaint.setAlpha(modulateAlpha(prevAlpha, drawableState.alpha));

        strokePaint.setStrokeWidth(drawableState.strokeWidth);
        int prevStrokeAlpha = strokePaint.getAlpha();
        strokePaint.setAlpha(modulateAlpha(prevStrokeAlpha, drawableState.alpha));

        if (dirty) {
            calculateOutlinePath(getBoundsAsRectF(), outlinePath);
            if (shadow != null) {
                try {
                    shadow.updateShadowBitmap(getBoundsInternal());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dirty = false;
        }

        if (hasFill()) {
            drawFillShape(canvas);
        }

        if (shadow != null) {
            try {
                shadow.draw(canvas, outlinePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (hasStroke()) {
            drawStrokeShape(canvas);
        }

        fillPaint.setAlpha(prevAlpha);
//        Shader textShader = new LinearGradient(1, 1, 1, 1, new int[] {
//                Color.WHITE, Color.CYAN,
//                Color.GRAY,Color.BLACK},
//                        new float[] {  0.25f,0.50f,0.75f,1 }, Shader.TileMode.CLAMP);
//        fillPaint.setShader(textShader);
        strokePaint.setAlpha(prevStrokeAlpha);
    }

    private void drawFillShape(@NonNull Canvas canvas) {
        canvas.drawPath(outlinePath, fillPaint);
    }

    private void drawStrokeShape(@NonNull Canvas canvas) {
        canvas.drawPath(outlinePath, strokePaint);
    }

    @NonNull
    public Path getOutlinePath() {
        return outlinePath;
    }

    private final RectF rectF1 = new RectF();

    private void calculateOutlinePath(@NonNull RectF bounds, @NonNull Path path) {
        if (drawableState == null || drawableState.padding == null) {
            return;
        }
        float left = (float) drawableState.padding.left;
        float top = (float) drawableState.padding.top;
        float right = left + bounds.width();
        float bottom = top + bounds.height();
        path.reset();

        rectF1.set(left, top, right, bottom);
        switch (drawableState.shapeAppearanceModel.getCornerFamily()) {
            case CornerFamily.OVAL:
                path.addOval(rectF1, Path.Direction.CW);
                break;
            case CornerFamily.ROUNDED:
                float cornerSize = drawableState.shapeAppearanceModel.getCornerSize();
                path.addRoundRect(
                        rectF1,
                        cornerSize, cornerSize,
                        Path.Direction.CW
                );
                break;
        }
        path.close();

    }

    @Override
    public void getOutline(@NonNull Outline outline) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (drawableState.shapeAppearanceModel.getCornerFamily()) {
                case CornerFamily.OVAL:
                    outline.setOval(getBoundsInternal());
                    break;
                case CornerFamily.ROUNDED:
                    float cornerSize = drawableState.shapeAppearanceModel.getCornerSize();
                    outline.setRoundRect(getBoundsInternal(), cornerSize);
                    break;
            }
        }
    }

    @Override
    public boolean isStateful() {
        if (drawableState != null && drawableState.fillColor != null) {
            return (super.isStateful() || drawableState.fillColor.isStateful());
        } else {
            return super.isStateful();
        }
    }

    @Override
    protected boolean onStateChange(int[] state) {
        boolean invalidateSelf = updateColorsForState(state);
        if (invalidateSelf) {
            invalidateSelf();
        }
        return invalidateSelf;
    }

    private boolean updateColorsForState(int[] state) {
        boolean invalidateSelf = false;

        int previousFillColor = fillPaint.getColor();
        int newFillColor = drawableState.fillColor.getColorForState(state, previousFillColor);
        if (previousFillColor != newFillColor) {
            fillPaint.setColor(newFillColor);
            invalidateSelf = true;
        }

        int previousStrokeColor = strokePaint.getColor();
        if (drawableState.strokeColor != null) {
            int newStrokeColor = drawableState.strokeColor.getColorForState(state, previousStrokeColor);
            if (previousStrokeColor != newStrokeColor) {
                strokePaint.setColor(newStrokeColor);
                invalidateSelf = true;
            }
        }
        return invalidateSelf;
    }

    public void setInEditMode(boolean inEditMode) {
        drawableState.inEditMode = inEditMode;
    }

    public static final class NeumorphShapeDrawableState extends ConstantState {
        public NeumorphShapeAppearanceModel shapeAppearanceModel;
        public BlurProvider blurProvider;
        public boolean inEditMode;

        public Rect padding;
        @Nullable
        public ColorStateList fillColor;
        @Nullable
        public ColorStateList strokeColor;
        public float strokeWidth = 0f;

        int alpha = 255;

        @ShapeType
        public int shapeType = ShapeType.DEFAULT;
        public float shadowElevation = 0f;
        public int shadowColorLight = Color.WHITE;
        public int shadowColorDark = Color.BLACK;
        public float translationZ = 0f;

        Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;

        public NeumorphShapeDrawableState(NeumorphShapeAppearanceModel shapeAppearanceModel, BlurProvider blurProvider) {
            this.shapeAppearanceModel = shapeAppearanceModel;
            this.blurProvider = blurProvider;
        }

        public NeumorphShapeDrawableState(@NonNull NeumorphShapeDrawableState orig) {
            shapeAppearanceModel = orig.shapeAppearanceModel;
            blurProvider = orig.blurProvider;
            inEditMode = orig.inEditMode;
            alpha = orig.alpha;
            shapeType = orig.shapeType;
            shadowElevation = orig.shadowElevation;
            shadowColorLight = orig.shadowColorLight;
            shadowColorDark = orig.shadowColorDark;
            fillColor = orig.fillColor;
            strokeColor = orig.strokeColor;
            strokeWidth = orig.strokeWidth;
            paintStyle = orig.paintStyle;
            translationZ = orig.translationZ;
            if (orig.padding != null) {
                padding = new Rect(orig.padding);
            }
        }


        @NonNull
        @Override
        public Drawable newDrawable() {
            NeumorphShapeDrawable temp = new NeumorphShapeDrawable(this);
            // Force the calculation of the path for the new drawable.
            temp.dirty = true;
            return temp;
        }

        @Override
        public int getChangingConfigurations() {
            return 0;
        }
    }

    private int modulateAlpha(int paintAlpha, int alpha) {
        int scale = alpha + (alpha >>> 7);
        return paintAlpha * scale >>> 8;
    }
}
