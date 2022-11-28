package com.mark.watermarkcamera

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.mark.watermarkcamera.activity.CameraActivity
import com.mark.watermarkcamera.databinding.ActivityMainBinding
import com.mark.watermarkcamera.widget.camera.CameraPreview
import kotlin.math.log

class MainActivity : CameraActivity<ActivityMainBinding>() {
    var isRecording = false

    override fun initPreview(preview: CameraPreview?) {
        if(preview != null){
            binding.cameraPreview.addView(preview)
        }
        binding.buttonCapture.setOnClickListener {
            takePicture()
        }
        binding.btnSetting.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }catch (e: Exception){
                Log.d("mark007", e.message + "")
            }
        }
        binding.ivOperate.setOnClickListener {
            if (isRecording){
                stopRecord()
                releaseMediaRecorder()
                isRecording = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
    }
}