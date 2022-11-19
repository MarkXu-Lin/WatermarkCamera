package com.mark.watermarkcamera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mark.watermarkcamera.activity.CameraActivity
import com.mark.watermarkcamera.databinding.ActivityMainBinding
import com.mark.watermarkcamera.widget.camera.CameraPreview

class MainActivity : CameraActivity<ActivityMainBinding>() {

    override fun initPreview(preview: CameraPreview?) {
        if(preview != null){
            binding.cameraPreview.addView(preview)
        }
        binding.buttonCapture.setOnClickListener {
            takePicture()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCamera()
    }
}