package ir.asandiag.compat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.graphics.drawable.DrawableCompat;

public class TextViewCompatTint extends AppCompatTextView {
    private int tintColor;

    public TextViewCompatTint(Context context) {
        this(context, null);
    }

    public TextViewCompatTint(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TextViewCompatTint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewCompatTint, defStyleAttr, 0);

        if (typedArray.hasValue(R.styleable.TextViewCompatTint_drawableTintCompat)) {
            tintColor = typedArray.getColor(R.styleable.TextViewCompatTint_drawableTintCompat, 0);

            Drawable[] drawables = getCompoundDrawablesRelative();

            for (Drawable drawable : drawables) {
                if (drawable == null) continue;
                DrawableCompat.setTint(DrawableCompat.wrap(drawable).mutate(), tintColor);
            }
        }

        typedArray.recycle();
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        if (left != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(left).mutate(), tintColor);
        }
        if (right != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(right).mutate(), tintColor);
        }
        if (top != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(top).mutate(), tintColor);
        }
        if (bottom != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(bottom).mutate(), tintColor);
        }
    }
}
