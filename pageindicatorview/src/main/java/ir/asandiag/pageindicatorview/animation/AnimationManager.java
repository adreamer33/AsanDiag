package ir.asandiag.pageindicatorview.animation;

import androidx.annotation.NonNull;

import ir.asandiag.pageindicatorview.animation.controller.AnimationController;
import ir.asandiag.pageindicatorview.animation.controller.ValueController;
import ir.asandiag.pageindicatorview.draw.data.Indicator;


public class AnimationManager {

    private AnimationController animationController;

    public AnimationManager(@NonNull Indicator indicator, @NonNull ValueController.UpdateListener listener) {
        this.animationController = new AnimationController(indicator, listener);
    }

    public void basic() {
        if (animationController != null) {
            animationController.end();
            animationController.basic();
        }
    }

    public void interactive(float progress) {
        if (animationController != null) {
            animationController.interactive(progress);
        }
    }

    public void end() {
        if (animationController != null) {
            animationController.end();
        }
    }
}
