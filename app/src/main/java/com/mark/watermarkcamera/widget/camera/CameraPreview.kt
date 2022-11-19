package com.mark.watermarkcamera.widget.camera

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

class CameraPreview(context: Context, private val mCamera: Camera): SurfaceView(context), SurfaceHolder.Callback {
    private val TAG = "Mark007"
    private val mHolder: SurfaceHolder = holder.apply {
        addCallback(this@CameraPreview)
        // deprecated setting, but required on Android versions prior to 3.0
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mCamera.apply {
            try {
                setPreviewDisplay(holder)
                startPreview()
            }catch (e: IOException){
                Log.d(TAG, "Error setting camera preview: ${e.message}")
            }
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (mHolder.surface == null){
            return
        }
        try {
            mCamera.stopPreview()
        }catch (e: Exception){
            Log.d(TAG, "Error surfaceChanged: ${e.message}")
        }
        mCamera.apply {
            try {
                setPreviewDisplay(mHolder)
                startPreview()
            }catch (e: Exception){
                Log.d(TAG, "Error starting camera preview: ${e.message}")
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }
}
