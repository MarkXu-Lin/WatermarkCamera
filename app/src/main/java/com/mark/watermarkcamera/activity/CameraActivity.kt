package com.mark.watermarkcamera.activity

import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.viewbinding.ViewBinding
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.widget.camera.CameraPreview
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

@RuntimePermissions
abstract class CameraActivity<T: ViewBinding> : BaseActivity<T>() {
    private val TAG = this.javaClass.name

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val MEDIA_TYPE_IMAGE = 1
    private val MEDIA_TYPE_VIDEO = 2

    private val mPicture = Camera.PictureCallback{data, _ ->
        val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
            Log.d(TAG, ("Error creating media file, check storage permissions"))
            return@PictureCallback
        }
        try {
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
        }catch (e: FileNotFoundException) {
            Log.d(TAG, "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d(TAG, "Error accessing file: ${e.message}")
        }

    }

    override fun initView() {
        getCameraInstance()
        mPreview = mCamera?.let {
            CameraPreview(this, it)
        }
        initPreview(mPreview)
    }

    /** Create a File for saving an image or video */
    private fun getOutputMediaFile(type: Int): File?{
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp")
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()){
                if (!mkdirs()){
                    Log.d(TAG, "failed to create directory")
                    return null
                }
            }
        }
        // Create a media file name
        val timeStamp  = SimpleDateFormat("yyyyMMdd_Hhmmss", Locale.getDefault()).format(Date())
        return when(type){
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            MEDIA_TYPE_VIDEO -> {
                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
            }
            else -> null
        }
    }

    abstract fun initPreview(preview: CameraPreview?)

    @NeedsPermission(android.Manifest.permission.CAMERA)
    fun getCameraInstance(){
        try {
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d(TAG, "" + e.message)
        }
    }

    protected fun takePicture(){
        mCamera?.takePicture(null, null, mPicture)
    }

    protected fun releaseCamera(){
        mCamera?.release()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }
}