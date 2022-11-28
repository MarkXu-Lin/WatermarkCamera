package com.mark.watermarkcamera.activity.ad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.mark.watermarkcamera.MainActivity
import com.mark.watermarkcamera.R
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.databinding.ActivityAdvertisingBinding
import com.mark.watermarkcamera.module.ad.AdvertisingManage
import com.mark.watermarkcamera.module.ad.AdvertisingViewModel
import kotlin.math.log

//class AdvertisingActivity : BaseActivity<ActivityAdvertisingBinding>() {
class AdvertisingActivity : AppCompatActivity() {
    private var advertisingManage: AdvertisingManage? = null
    private lateinit var advertisingViewModel: AdvertisingViewModel
    private lateinit var binding: ActivityAdvertisingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdvertisingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        advertisingViewModel = ViewModelProvider(this)[AdvertisingViewModel::class.java]
        Log.d("xlk", advertisingViewModel.millisInFuture.toString())
        advertisingManage = AdvertisingManage(advertisingViewModel.millisInFuture).apply {
            advertisingManageListener = object : AdvertisingManage.AdvertisingManageListener {
                override fun timing(second: Int) {
                    binding.tvCountdown.text = getString(R.string.countDownTimeText, second)
                }

                override fun enterMainActivity() {
                    this@AdvertisingActivity.enterMainActivity()
                }

            }
        }
        lifecycle.addObserver(advertisingManage!!)
        binding.tvCountdown.setOnClickListener {
            enterMainActivity()
        }
    }

//    override fun initView() {
//        advertisingViewModel = ViewModelProvider(this)[AdvertisingViewModel::class.java]
//        Log.d("xlk", advertisingViewModel.millisInFuture.toString())
//        advertisingManage = AdvertisingManage(advertisingViewModel.millisInFuture).apply {
//            advertisingManageListener = object : AdvertisingManage.AdvertisingManageListener {
//                override fun timing(second: Int) {
//                    binding.tvCountdown.text = getString(R.string.countDownTimeText, second)
//                }
//
//                override fun enterMainActivity() {
//                    this@AdvertisingActivity.enterMainActivity()
//                }
//
//            }
//        }
//        lifecycle.addObserver(advertisingManage!!)
//        binding.tvCountdown.setOnClickListener {
//            enterMainActivity()
//        }
//    }
//
//    override fun isFullscreen() = true

    private fun enterMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}