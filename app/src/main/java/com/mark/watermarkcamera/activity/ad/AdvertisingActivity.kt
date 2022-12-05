package com.mark.watermarkcamera.activity.ad

import android.content.Intent
import androidx.activity.viewModels
import com.mark.watermarkcamera.R
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.databinding.ActivityAdvertisingBinding
import com.mark.watermarkcamera.module.ad.AdvertisingManage
import com.mark.watermarkcamera.module.ad.AdvertisingViewModel
import com.mark.watermarkcamera.activity.my.MyActivity

class AdvertisingActivity : BaseActivity<ActivityAdvertisingBinding>() {
    private var advertisingManage: AdvertisingManage? = null
    private val advertisingViewModel by viewModels<AdvertisingViewModel>()

    override fun initView() {
        advertisingManage = AdvertisingManage(advertisingViewModel).apply {
            advertisingManageListener = object : AdvertisingManage.AdvertisingManageListener {
                override fun timing(second: Int) {
                    advertisingViewModel.millisInFuture = second * 1000L
                    binding.tvCountdown.text = getString(R.string.countDownTimeText, second)
                }

                override fun enterMainActivity() {
                    this@AdvertisingActivity.enterMainActivity()
                }

            }
        }
        lifecycle.addObserver(advertisingManage!!)
        advertisingViewModel.timingResult.observe(this) {
            if (it == 0L){
                this@AdvertisingActivity.enterMainActivity()
            }else{
                binding.tvCountdown.text = getString(R.string.countDownTimeText, it / 1000)
            }
        }
        binding.tvCountdown.setOnClickListener {
            enterMainActivity()
        }
    }

    override fun isFullscreen() = true

    private fun enterMainActivity(){
        startActivity(Intent(this, MyActivity::class.java))
        finish()
    }
}