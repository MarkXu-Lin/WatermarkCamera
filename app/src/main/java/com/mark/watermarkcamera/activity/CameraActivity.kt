package com.mark.watermarkcamera.activity

import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.mark.watermarkcamera.base.BaseActivity

open class CameraActivity<T: ViewBinding> : BaseActivity<T>() {
    protected fun getCameraInstance(): Camera?{
        return try {
            Camera.open()
        }catch (e: Exception){
            null
        }
    }
}