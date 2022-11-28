package com.mark.watermarkcamera.activity.ad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mark.watermarkcamera.MainActivity
import com.mark.watermarkcamera.R
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.databinding.ActivityAdvertisingBinding
import com.mark.watermarkcamera.module.ad.AdvertisingManage
import kotlin.math.log

class AdvertisingActivity : BaseActivity<ActivityAdvertisingBinding>() {
    private var advertisingManage: AdvertisingManage? = null

    override fun initView() {
        advertisingManage = AdvertisingManage().apply {
            advertisingManageListener = object : AdvertisingManage.AdvertisingManageListener {
                override fun timing(second: Int) {
                    binding.tvCountdown.text = getString(R.string.countDownTimeText, second)
                }

                override fun enterMainActivity() {
                    this@AdvertisingActivity.enterMainActivity()
                }

            }
            start()
        }
        binding.tvCountdown.setOnClickListener {
            enterMainActivity()
        }
    }

    override fun isFullscreen() = true

    private fun enterMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        advertisingManage?.cancel()
    }
}