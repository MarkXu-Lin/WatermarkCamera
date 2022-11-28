package com.mark.watermarkcamera.activity

import android.app.RecoverableSecurityException
import android.content.ContentUris
import android.content.ContentValues
import android.content.IntentSender
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.content.Intent
import android.hardware.Camera
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.viewbinding.BuildConfig
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.widget.camera.CameraPreview
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

@RuntimePermissions
abstract class CameraActivity<T: ViewBinding> : BaseActivity<T>() {
    private val TAG = this.javaClass.name

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    private val mPicture = Camera.PictureCallback{data, _ ->
        try {
            val pictureFile: File = getOutputMediaFile(MEDIA_TYPE_IMAGE) ?: run {
                Log.d(TAG, ("Error creating media file, check storage permissions"))
                return@PictureCallback
            }
            val fos = FileOutputStream(pictureFile)
            fos.write(data)
            fos.close()
            releaseCamera()
        }catch (e: FileNotFoundException) {
            Log.d(TAG, "File not found: ${e.message}")
        } catch (e: IOException){
            Log.d(TAG, e.message + "")
        }

    }

    override fun initView() {
        initCameraWithPermissionCheck()
    }

    /** Create a File for saving an image or video */
    private fun getOutputMediaFile(type: Int): File?{
        // Create a media file name
        val timeStamp  = SimpleDateFormat("yyyyMMdd_Hhmmss", Locale.getDefault()).format(Date())
        val fileName = when(type){
            MEDIA_TYPE_VIDEO -> {
               "VID_$timeStamp.mp4"
            }
            else -> {
               "IMG_$timeStamp.jpg"
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            return null
        } else{
            // To be safe, you should check that the SDCard is mounted
            // using Environment.getExternalStorageState() before doing this.
            val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp")
            val inputStream = FileInputStream(mediaStorageDir)
            inputStream.use {  }
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
            return when(type){
                MEDIA_TYPE_IMAGE -> {
                    File("${mediaStorageDir.path}${File.separator}${fileName}")
                }
                MEDIA_TYPE_VIDEO -> {
                    File("${mediaStorageDir.path}${File.separator}${fileName}")
                }
                else -> null
            }
        }
    }

    private fun saveImgFileWithMediaStore(){
        val imageMediaPath = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Demo").path
        }else{
            Environment.DIRECTORY_PICTURES + "/Demo"
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            try {
                val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
                //创建了一个红色的图片
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.RED)
                val fos = FileOutputStream(imageMediaPath)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
                fos.close()
            }catch (e: FileNotFoundException){
                Log.d(TAG, "创建失败：${e.message}")
            }catch (e: IOException){
                Log.d(TAG, "创建失败：${e.message}")
            }
        }else{
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "red_image.png")
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is a Image")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(MediaStore.Images.Media.RELATIVE_PATH, imageMediaPath)
            val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val insertUri = contentResolver.insert(external, values)
            var os: OutputStream? = null
            try {
                if (insertUri != null){
                    os = contentResolver.openOutputStream(insertUri)
                }
                if (os != null){
                    val bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
                    //创建了一个红色的图片
                    val canvas = Canvas(bitmap)
                    canvas.drawColor(Color.RED)
                    // 向os流写入数据
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, os)
                }
            }catch (e: IOException){
                Log.d(TAG, "创建失败：${e.message}")
            }finally {
                os?.close()
            }
        }
    }

    private fun deleteFile(){
    }

    abstract fun initPreview(preview: CameraPreview?)

    @NeedsPermission(android.Manifest.permission.CAMERA)
    fun initCamera(){
        try {
            mCamera = Camera.open()
            mCamera?.setDisplayOrientation(90)
            mPreview = mCamera?.let {
                CameraPreview(this, it)
            }
            mPreview?.fileStr = getOutputMediaFile(MEDIA_TYPE_VIDEO).toString()
            initPreview(mPreview)
        }catch (e: Exception){
            Log.d(TAG, "" + e.message)
        }
    }

    /**
     * 拍照
     */
    protected fun takePicture(){
        mCamera?.takePicture(null, null, mPicture)
    }

    /**
     * 开始拍摄
     */
    protected fun startRecord(){
        mPreview?.startRecord()
    }

    /**
     * 停止拍摄
     */
    protected fun stopRecord(){
        mPreview?.stopRecord()
    }

    protected fun releaseCamera(){
        mCamera?.release()
    }

    protected fun releaseMediaRecorder() {
        mPreview?.releaseMediaRecorder()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        onRequestPermissionsResult(requestCode, grantResults)
    }

    companion object{
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
    }
}