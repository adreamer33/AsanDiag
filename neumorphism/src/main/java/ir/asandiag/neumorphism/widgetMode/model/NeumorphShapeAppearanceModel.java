package ir.asandiag.neumorphism.widgetMode.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import ir.asandiag.neumorphism.R;

public class NeumorphShapeAppearanceModel {
    @CornerFamily
    private int cornerFamily = CornerFamily.ROUNDED;
    private float cornerSize = 0f;


    public NeumorphShapeAppearanceModel(@NonNull Builder builder) {
        cornerFamily = builder.cornerFamily;
        cornerSize = builder.cornerSize;
    }

    public NeumorphShapeAppearanceModel() {
        cornerFamily = CornerFamily.ROUNDED;
        cornerSize = 0f;
    }


    @CornerFamily
    public int getCornerFamily() {
        return cornerFamily;
    }

    public float getCornerSize() {
        return cornerSize;
    }

    @NonNull
    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs,
                                  @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        return builder(context, attrs, defStyleAttr, defStyleRes, 0f);
    }

    @NonNull
    public static Builder builder(@NonNull Context context, AttributeSet attrs,
                                  @AttrRes int defStyleAttr, @StyleRes int defStyleRes, float defaultCornerSize) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.NeumorphShape, defStyleAttr, defStyleRes
        );
        int shapeAppearanceResId = a.getResourceId(R.styleable.NeumorphShape_neumorph_shapeAppearance, 0);
        a.recycle();
        return builder(context, shapeAppearanceResId, defaultCornerSize);
    }

    @NonNull
    private static Builder builder(@NonNull Context context, @StyleRes int shapeAppearanceResId, float defaultCornerSize) {
        final TypedArray a = context.obtainStyledAttributes(shapeAppearanceResId, R.styleable.NeumorphShapeAppearance);


        try {
            int cornerFamily = a.getInt(
                    R.styleable.NeumorphShapeAppearance_neumorph_cornerFamily,
                    CornerFamily.ROUNDED
            );
            float cornerSize = getCornerSize(a,
                    R.styleable.NeumorphShapeAppearance_neumorph_cornerSize,
                    defaultCornerSize
            );
            return new Builder().setAllCorners(cornerFamily, cornerSize);
        } finally {
            a.recycle();
        }
    }

    private static float getCornerSize(@NonNull TypedArray a, int index, float defaultValue) {
        TypedValue value;
        value = a.peekValue(index);
        if (value.type == TypedValue.TYPE_DIMENSION) {
            return TypedValue.complexToDimensionPixelSize(
                    value.data, a.getResources().getDisplayMetrics()
            );
        } else {
            return defaultValue;
        }
    }

    public static class Builder {
        @CornerFamily
        int cornerFamily = CornerFamily.ROUNDED;
        float cornerSize = 0f;

        @NonNull
        public Builder setAllCorners(@CornerFamily int cornerFamily, @Dimension float cornerSize) {
            return setAllCorners(cornerFamily).setAllCornerSizes(cornerSize);
        }

        @NonNull
        public Builder setAllCorners(@CornerFamily int cornerFamily) {
            this.cornerFamily = cornerFamily;
            return this;
        }

        @NonNull
        public Builder setAllCornerSizes(float cornerSize) {
            this.cornerSize = cornerSize;
            return this;
        }

        @NonNull
        public NeumorphShapeAppearanceModel build() {
            return new NeumorphShapeAppearanceModel(this);
        }

    }


}
