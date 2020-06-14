package ir.asandiag.pageindicatorview.animation.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.asandiag.pageindicatorview.animation.data.Value;
import ir.asandiag.pageindicatorview.animation.type.ColorAnimation;
import ir.asandiag.pageindicatorview.animation.type.DropAnimation;
import ir.asandiag.pageindicatorview.animation.type.FillAnimation;
import ir.asandiag.pageindicatorview.animation.type.ScaleAnimation;
import ir.asandiag.pageindicatorview.animation.type.ScaleDownAnimation;
import ir.asandiag.pageindicatorview.animation.type.SlideAnimation;
import ir.asandiag.pageindicatorview.animation.type.SwapAnimation;
import ir.asandiag.pageindicatorview.animation.type.ThinWormAnimation;
import ir.asandiag.pageindicatorview.animation.type.WormAnimation;


public class ValueController {

    @Nullable
    private final UpdateListener updateListener;
    @Nullable
    private ColorAnimation colorAnimation;
    @Nullable
    private ScaleAnimation scaleAnimation;
    @Nullable
    private WormAnimation wormAnimation;
    @Nullable
    private SlideAnimation slideAnimation;
    @Nullable
    private FillAnimation fillAnimation;
    @Nullable
    private ThinWormAnimation thinWormAnimation;
    @Nullable
    private DropAnimation dropAnimation;
    @Nullable
    private SwapAnimation swapAnimation;
    @Nullable
    private ScaleDownAnimation scaleDownAnimation;

    public interface UpdateListener {
        void onValueUpdated(@Nullable Value value);
    }

    public ValueController(@Nullable UpdateListener listener) {
        updateListener = listener;
    }

    @NonNull
    public ColorAnimation color() {
        if (colorAnimation == null) {
            colorAnimation = new ColorAnimation(updateListener);
        }

        return colorAnimation;
    }

    @NonNull
    public ScaleAnimation scale() {
        if (scaleAnimation == null) {
            scaleAnimation = new ScaleAnimation(updateListener);
        }

        return scaleAnimation;
    }

    @NonNull
    public WormAnimation worm() {
        if (wormAnimation == null) {
            wormAnimation = new WormAnimation(updateListener);
        }

        return wormAnimation;
    }

    @NonNull
    public SlideAnimation slide() {
        if (slideAnimation == null) {
            slideAnimation = new SlideAnimation(updateListener);
        }

        return slideAnimation;
    }

    @NonNull
    public FillAnimation fill() {
        if (fillAnimation == null) {
            fillAnimation = new FillAnimation(updateListener);
        }

        return fillAnimation;
    }

    @NonNull
    public ThinWormAnimation thinWorm() {
        if (thinWormAnimation == null) {
            thinWormAnimation = new ThinWormAnimation(updateListener);
        }

        return thinWormAnimation;
    }

    @NonNull
    public DropAnimation drop() {
        if (dropAnimation == null) {
            dropAnimation = new DropAnimation(updateListener);
        }

        return dropAnimation;
    }

    @NonNull
    public SwapAnimation swap() {
        if (swapAnimation == null) {
            swapAnimation = new SwapAnimation(updateListener);
        }

        return swapAnimation;
    }

    @NonNull
    public ScaleDownAnimation scaleDown() {
        if (scaleDownAnimation == null) {
            scaleDownAnimation = new ScaleDownAnimation(updateListener);
        }

        return scaleDownAnimation;
    }
}
