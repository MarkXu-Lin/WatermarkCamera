package com.mark.watermarkcamera.widget.camera

import android.content.Context
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.mark.watermarkcamera.activity.CameraActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraPreview(context: Context, private val mCamera: Camera): SurfaceView(context), SurfaceHolder.Callback {
    private val TAG = "Mark007"
    lateinit var fileStr: String
    private var mediaRecorder: MediaRecorder? = null
    private val mHolder: SurfaceHolder = holder.apply {
        addCallback(this@CameraPreview)
        // deprecated setting, but required on Android versions prior to 3.0
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        startPreview()
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
        startPreview()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    /**
     * 开始拍摄
     */
    fun startRecord(){
        mediaRecorder?.start()
    }

    /**
     * 停止拍摄
     */
    fun stopRecord(){
        mediaRecorder?.stop()
    }
    private fun prepareVideoRecorder(): Boolean{
        mediaRecorder = MediaRecorder()
        mCamera.let {
            // Step 1: Unlock and set camera to MediaRecorder
            it.unlock()
            mediaRecorder?.run {
                setCamera(it)
                setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                setVideoSource(MediaRecorder.VideoSource.CAMERA)
                setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
                setOutputFile(fileStr)
                setPreviewDisplay(mHolder.surface)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT)
                // Step 6: Prepare configured MediaRecorder
                return try {
                    prepare()
                    true
                } catch (e: IllegalStateException) {
                    Log.d(TAG, "IllegalStateException preparing MediaRecorder: ${e.message}")
                    releaseMediaRecorder()
                    false
                } catch (e: IOException) {
                    Log.d(TAG, "IOException preparing MediaRecorder: ${e.message}")
                    releaseMediaRecorder()
                    false
                }
            }
        }
        return false
    }

    fun releaseMediaRecorder() {
        mediaRecorder?.reset() // clear recorder configuration
        mediaRecorder?.release() // release the recorder object
        mCamera.lock() // lock camera for later use
    }

    private fun startPreview(){
        mCamera.apply {
            try {
                setPreviewDisplay(mHolder)
                startPreview()
            }catch (e: Exception){
                Log.d(TAG, "Error starting camera preview: ${e.message}")
            }
        }
    }
}
