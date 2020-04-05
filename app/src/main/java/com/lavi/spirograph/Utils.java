package com.lavi.spirograph;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
    private DisplayMetrics displayMetrics = new DisplayMetrics();

    public Utils(Context context) {
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
    }


    public int getScreenWidth() {
        return displayMetrics.widthPixels;
    }

    public int getScreenHeight() {
        return displayMetrics.heightPixels / 2;
    }
}
