package ir.asandiag.pageindicatorview.draw.drawer.type;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import ir.asandiag.pageindicatorview.animation.data.Value;
import ir.asandiag.pageindicatorview.animation.data.type.DropAnimationValue;
import ir.asandiag.pageindicatorview.draw.data.Indicator;
import ir.asandiag.pageindicatorview.draw.data.Orientation;
import ir.asandiag.pageindicatorview.utils.DensityUtils;


public class DropDrawer extends BaseDrawer {
    Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);

    public DropDrawer(@NonNull Paint paint, @NonNull Indicator indicator) {
        super(paint, indicator);
    }

    public void draw(
            @NonNull Canvas canvas,
            @NonNull Value value,
            int coordinateX,
            int coordinateY, boolean isSelectedItem) {

        if (!(value instanceof DropAnimationValue)) {
            return;
        }

        DropAnimationValue v = (DropAnimationValue) value;
        int unselectedColor = indicator.getUnselectedColor();
        int selectedColor = indicator.getSelectedColor();
        float radius = indicator.getRadius();


        if (isSelectedItem) {
            drawBorder(canvas, coordinateX, coordinateY, radius);
        }

        paint.setShadowLayer(DensityUtils.dpToPx(3), 2, 2, unselectedColor);
        paint.setColor(unselectedColor);
        canvas.drawCircle(coordinateX, coordinateY, radius, paint);


        paint.setShadowLayer(DensityUtils.dpToPx(3), 2, 2, selectedColor);
        paint.setColor(selectedColor);
        if (indicator.getOrientation() == Orientation.HORIZONTAL) {
            canvas.drawCircle(v.getWidth(), v.getHeight(), v.getRadius(), paint);
        } else {
            canvas.drawCircle(v.getHeight(), v.getWidth(), v.getRadius(), paint);
        }

        if (!isSelectedItem) {
            drawBorder(canvas, coordinateX, coordinateY, radius);
        }

    }

    private void drawBorder(@NonNull Canvas canvas, int coordinateX, int coordinateY, float radius) {
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(DensityUtils.dpToPx(1));
        border.setShader(new RadialGradient(0, 0, DensityUtils.dpToPx(10)
                , Color.parseColor("#CECEC1"), Color.parseColor("#ef242424"), Shader.TileMode.MIRROR));
        canvas.drawCircle(coordinateX, coordinateY, radius + 2, border);
    }
}
