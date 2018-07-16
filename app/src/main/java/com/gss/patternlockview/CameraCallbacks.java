package com.gss.patternlockview;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by geniee4 on 23/6/18.
 */

interface CameraCallbacks {

    void onImageCapture(@NonNull File imageFile);

    void onCameraError(@CameraError.CameraErrorCodes int errorCode);
}
