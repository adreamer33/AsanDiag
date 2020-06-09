package ir.asandiag.neumorphism.widgetMode;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ir.asandiag.neumorphism.R;
import ir.asandiag.neumorphism.widgetMode.model.NeumorphShapeAppearanceModel;
import ir.asandiag.neumorphism.widgetMode.model.ShapeType;

/**
 * TODO: document your custom view class.
 */
public class NeumorphImageButton extends AppCompatImageButton {
    private boolean isInitialized = false;
    private NeumorphShapeDrawable shapeDrawable;

    private ColorStateList fillColor;
    private ColorStateList strokeColor;
    private float strokeWidth;
    private int shapeType;
    private float shadowElevation;
    private int shadowColorLight;
    private int shadowColorDark;

    public NeumorphImageButton(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public NeumorphImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public NeumorphImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, 0);
    }

    public NeumorphImageButton(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NeumorphImageButton, defStyle, 0);

        fillColor = a.getColorStateList(R.styleable.NeumorphImageButton_neumorph_backgroundColor);
        strokeColor = a.getColorStateList(R.styleable.NeumorphImageButton_neumorph_strokeColor);
        strokeWidth = a.getDimension(R.styleable.NeumorphImageButton_neumorph_strokeWidth, 0f);
        shapeType = a.getInt(R.styleable.NeumorphImageButton_neumorph_shapeType, ShapeType.DEFAULT);
        shadowElevation = a.getDimension(R.styleable.NeumorphImageButton_neumorph_shadowElevation, 0f);

        shadowColorLight=a.getColor(R.styleable.NeumorphImageButton_neumorph_shadowColorLight,
                ContextCompat.getColor(getContext(), R.color.design_dark_default_color_shadow_light));

        shadowColorDark=a.getColor(R.styleable.NeumorphImageButton_neumorph_shadowColorDark,
                ContextCompat.getColor(getContext(), R.color.design_dark_default_color_shadow_dark));

        a.recycle();

        shapeDrawable = new NeumorphShapeDrawable(getContext(), attrs, defStyle, defStyleRes);
        shapeDrawable.setInEditMode(isInEditMode());
        shapeDrawable.setShapeType(shapeType);
        shapeDrawable.setShadowElevation(shadowElevation);
        shapeDrawable.setShadowColorLight(shadowColorLight);
        shapeDrawable.setShadowColorDark(shadowColorDark);
        shapeDrawable.setFillColor(fillColor);
        shapeDrawable.setStroke(strokeWidth, strokeColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            shapeDrawable.setTranslationZ(getTranslationZ());
        }
        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();
        if (left > 0 || right > 0 || top > 0 || bottom > 0) {
            shapeDrawable.setPadding(left, top, right, bottom);
        }

        setBackgroundInternal(shapeDrawable);
        isInitialized = true;

    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        shapeDrawable.setPadding(left, top, right, bottom);
    }

    @Override
    public void setBackground(@Nullable Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override
    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        Log.i("NeumorphImageView", "Setting a custom background is not supported.");
    }


    private void setBackgroundInternal(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public final void setShapeAppearanceModel(@NotNull NeumorphShapeAppearanceModel shapeAppearanceModel) {
        shapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @NotNull
    public final NeumorphShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeDrawable.getShapeAppearanceModel();
    }

    public final void setBackgroundColor(@Nullable ColorStateList backgroundColor) {
        this.shapeDrawable.setFillColor(backgroundColor);
    }

    @Nullable
    public final ColorStateList getBackgroundColor() {
        return this.shapeDrawable.getFillColor();
    }

    public final void setStrokeColor(@Nullable ColorStateList strokeColor) {
        this.shapeDrawable.setStrokeColor(strokeColor);
    }

    @Nullable
    public final ColorStateList getStrokeColor() {
        return this.shapeDrawable.getStrokeColor();
    }

    public final void setStrokeWidth(float strokeWidth) {
        this.shapeDrawable.setStrokeWidth(strokeWidth);
    }

    public final float getStrokeWidth() {
        return this.shapeDrawable.getStrokeWidth();
    }

    public final void setShapeType(int shapeType) {
        this.shapeDrawable.setShapeType(shapeType);
    }

    @ShapeType
    public final int getShapeType() {
        return this.shapeDrawable.getShapeType();
    }

    public final void setShadowElevation(float shadowElevation) {
        this.shapeDrawable.setShadowElevation(shadowElevation);
    }

    public final float getShadowElevation() {
        return this.shapeDrawable.getShadowElevation();
    }

    public final void setShadowColorLight(@ColorInt int shadowColor) {
        this.shapeDrawable.setShadowColorLight(shadowColor);
    }

    public final void setShadowColorDark(@ColorInt int shadowColor) {
        this.shapeDrawable.setShadowColorDark(shadowColor);
    }

    @Override
    public void setTranslationZ(float translationZ) {
        super.setTranslationZ(translationZ);
        if (this.isInitialized) {
            this.shapeDrawable.setTranslationZ(translationZ);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
