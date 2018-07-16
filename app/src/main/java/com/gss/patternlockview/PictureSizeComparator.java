package com.gss.patternlockview;

import android.hardware.Camera;

import java.util.Comparator;

/**
 * Created by geniee4 on 23/6/18.
 */

class PictureSizeComparator implements Comparator<Camera.Size> {
    // Used for sorting in ascending order of
    // roll name
    public int compare(Camera.Size a, Camera.Size b) {
        return (b.height * b.width) - (a.height * a.width);
    }
}