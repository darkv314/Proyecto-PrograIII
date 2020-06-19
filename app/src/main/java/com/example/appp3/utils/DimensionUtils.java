package com.example.appp3.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

public class DimensionUtils {
    public static int getPixelsFromDPs(Activity activity, int dps) {
        Resources r = activity.getResources();
        int px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
}
