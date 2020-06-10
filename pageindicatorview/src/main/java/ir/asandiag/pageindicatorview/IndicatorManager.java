package ir.asandiag.pageindicatorview;

import androidx.annotation.Nullable;

import ir.asandiag.pageindicatorview.animation.AnimationManager;
import ir.asandiag.pageindicatorview.animation.controller.ValueController;
import ir.asandiag.pageindicatorview.animation.data.Value;
import ir.asandiag.pageindicatorview.draw.DrawManager;
import ir.asandiag.pageindicatorview.draw.data.Indicator;


public class IndicatorManager implements ValueController.UpdateListener {

    private DrawManager drawManager;
    private AnimationManager animationManager;
    private Listener listener;

    interface Listener {
        void onIndicatorUpdated();
    }

    IndicatorManager(@Nullable Listener listener) {
        this.listener = listener;
        this.drawManager = new DrawManager();
        this.animationManager = new AnimationManager(drawManager.indicator(), this);
    }

    public AnimationManager animate() {
        return animationManager;
    }

    public Indicator indicator() {
        return drawManager.indicator();
    }

    public DrawManager drawer() {
        return drawManager;
    }

    @Override
    public void onValueUpdated(@Nullable Value value) {
        drawManager.updateValue(value);
        if (listener != null) {
            listener.onIndicatorUpdated();
        }
    }
}
