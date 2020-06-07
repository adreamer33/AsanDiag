package ir.asandiag.neumorphism.model;


import androidx.annotation.IntDef;
import androidx.annotation.RestrictTo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@IntDef({ShapeType.FLAT, ShapeType.PRESSED, ShapeType.BASIN})
@Retention(RetentionPolicy.SOURCE)
public @interface ShapeType {
    int FLAT = 0;
    int PRESSED = 1;
    int BASIN = 2;

    int DEFAULT = FLAT;
}
