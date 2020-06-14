package ir.asandiag.pageindicatorview.draw.controller;

import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.asandiag.pageindicatorview.animation.data.Value;
import ir.asandiag.pageindicatorview.animation.type.AnimationType;
import ir.asandiag.pageindicatorview.draw.data.Indicator;
import ir.asandiag.pageindicatorview.draw.drawer.Drawer;
import ir.asandiag.pageindicatorview.utils.CoordinatesUtils;


public class DrawController {

    @NonNull
    private final Drawer drawer;
    @NonNull
    private final Indicator indicator;
    @Nullable
    private Value value;
    @Nullable
    private ClickListener listener;

    public interface ClickListener {

        void onIndicatorClicked(int position);
    }

    public DrawController(@NonNull Indicator indicator) {
        this.indicator = indicator;
        this.drawer = new Drawer(indicator);
    }

    public void updateValue(@Nullable Value value) {
        this.value = value;
    }

    public void setClickListener(@Nullable ClickListener listener) {
        this.listener = listener;
    }

    public void touch(@Nullable MotionEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                onIndicatorTouched(event.getX(), event.getY());
                break;
            default:
        }
    }

    private void onIndicatorTouched(float x, float y) {
        if (listener != null) {
            int position = CoordinatesUtils.getPosition(indicator, x, y);
            if (position >= 0) {
                listener.onIndicatorClicked(position);
            }
        }
    }

    public void draw(@NonNull Canvas canvas) {
        int count = indicator.getCount();

        for (int position = 0; position < count; position++) {
            int coordinateX = CoordinatesUtils.getXCoordinate(indicator, position);
            int coordinateY = CoordinatesUtils.getYCoordinate(indicator, position);
            drawIndicator(canvas, position, coordinateX, coordinateY);
        }
    }

    private void drawIndicator(
            @NonNull Canvas canvas,
            int position,
            int coordinateX,
            int coordinateY) {

        boolean interactiveAnimation = indicator.isInteractiveAnimation();
        int selectedPosition = indicator.getSelectedPosition();
        int selectingPosition = indicator.getSelectingPosition();
        int lastSelectedPosition = indicator.getLastSelectedPosition();

        boolean selectedItem = !interactiveAnimation && (position == selectedPosition || position == lastSelectedPosition);
        boolean selectingItem = interactiveAnimation && (position == selectedPosition || position == selectingPosition);
        boolean isSelectedItem = selectedItem | selectingItem;
        drawer.setup(position, coordinateX, coordinateY);

        if (value != null && isSelectedItem) {
            drawWithAnimation(canvas, isSelectedItem);
        } else {
//            drawer.drawBasic(canvas, isSelectedItem);
            drawWithAnimation(canvas, isSelectedItem);

        }
    }

    private void drawWithAnimation(@NonNull Canvas canvas, boolean isSelectedItem) {
        AnimationType animationType = indicator.getAnimationType();
        switch (animationType) {
            case NONE:
                drawer.drawBasic(canvas, true);
                break;

            case COLOR:
                drawer.drawColor(canvas, value);
                break;

            case SCALE:
                drawer.drawScale(canvas, value);
                break;

            case WORM:
                drawer.drawWorm(canvas, value);
                break;

            case SLIDE:
                drawer.drawSlide(canvas, value);
                break;

            case FILL:
                drawer.drawFill(canvas, value);
                break;

            case THIN_WORM:
                drawer.drawThinWorm(canvas, value);
                break;

            case DROP:
                drawer.drawDrop(canvas, value, isSelectedItem);
                break;

            case SWAP:
                drawer.drawSwap(canvas, value);
                break;

            case SCALE_DOWN:
                drawer.drawScaleDown(canvas, value);
                break;
        }
    }
}
