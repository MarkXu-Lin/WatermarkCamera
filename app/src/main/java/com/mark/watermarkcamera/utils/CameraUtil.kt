package com.mark.watermarkcamera.utils

import android.content.Context
import android.content.pm.PackageManager

object CameraUtil {
    fun checkCameraHardware(context: Context): Boolean{
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }
}