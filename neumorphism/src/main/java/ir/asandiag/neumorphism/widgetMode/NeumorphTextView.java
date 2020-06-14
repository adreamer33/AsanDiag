package ir.asandiag.neumorphism.widgetMode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import ir.asandiag.neumorphism.R;

public class NeumorphTextView extends AppCompatTextView {
    private float shadowElevation;
    private int shadowColorLight;
    private int shadowColorDark;


    private Paint shadowPaint;
    private Bitmap lastTextCache;
    private Bitmap lastShadowLight;
    private Bitmap lastShadowDark;

    public NeumorphTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public NeumorphTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public NeumorphTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NeumorphTextView, defStyleAttr, 0);
        shadowElevation = a.getDimension(R.styleable.NeumorphTextView_neumorph_shadowElevation, 0.0F);
        shadowColorLight = a.getColor(R.styleable.NeumorphTextView_neumorph_shadowColorLight,
                ContextCompat.getColor(getContext(), R.color.design_dark_default_color_shadow_light));

        shadowColorDark = a.getColor(R.styleable.NeumorphTextView_neumorph_shadowColorDark,
                ContextCompat.getColor(getContext(), R.color.design_dark_default_color_shadow_dark));

        a.recycle();
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        lastTextCache = buildTextCache(w, h);
        if (this.lastShadowLight == null) {
            this.lastShadowLight = generateBitmapShadowCache(lastTextCache, w, h, this.shadowColorLight, 5f);
        }

        if (this.lastShadowDark == null) {
            this.lastShadowDark = generateBitmapShadowCache(lastTextCache, w, h, this.shadowColorDark, 5f);
        }

    }

    private Bitmap generateBitmapShadowCache(@NotNull Bitmap inputBitmap, int w, int h, int color, float radius) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Call mutate, so that the pixel allocation by the underlying vector drawable is cleared.
        canvas.save();
        canvas.drawBitmap(inputBitmap, 0.0F, 0.0F, null);
        canvas.restore();

        // Draws the shadow from original drawable
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        paint.setColor(color);
        paint.setMaskFilter(new BlurMaskFilter(Math.max(1f, radius), BlurMaskFilter.Blur.NORMAL));
        int[] offset = new int[2];
        Bitmap shadow = bitmap.extractAlpha(paint, offset);
        paint.setMaskFilter(null);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas.drawBitmap(shadow, (float) offset[0], (float) offset[1], paint);
        return bitmap;
    }

    @Override
    public void draw(@NotNull Canvas canvas) {
        if (lastShadowLight != null) {
            canvas.drawBitmap(lastShadowLight, -shadowElevation, -shadowElevation, shadowPaint);
        }

        if (lastShadowDark != null) {
            canvas.drawBitmap(lastShadowDark, shadowElevation, shadowElevation, shadowPaint);
        }
        super.draw(canvas);
    }

    private Bitmap buildTextCache(int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        TextPaint tp = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        tp.setColor(Color.BLACK);
        tp.setTextSize(getTextSize());
        tp.setTypeface(getTypeface());
        if (!isInEditMode()) {
            // layout preview is NOT supported in now.
            CharSequence var10001 = this.getText();
            staticLayout(var10001, tp).draw(new Canvas(bitmap));
        }
        return bitmap;
    }

    private StaticLayout staticLayout(CharSequence text, TextPaint textPaint) {
        StaticLayout var10000;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return StaticLayout.Builder.obtain(text, 0, text.length(), textPaint, Integer.MAX_VALUE).build();
        } else {
            return new StaticLayout(text, textPaint, Integer.MAX_VALUE, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        }
    }
}
