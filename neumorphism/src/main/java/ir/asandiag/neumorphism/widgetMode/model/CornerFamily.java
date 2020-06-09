package ir.asandiag.neumorphism.widgetMode.model;

import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@IntDef({CornerFamily.ROUNDED, CornerFamily.OVAL})
@Retention(RetentionPolicy.SOURCE)
public @interface CornerFamily {
    int ROUNDED = 0;
    int OVAL = 1;
}
