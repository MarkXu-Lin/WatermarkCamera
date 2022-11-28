package com.mark.watermarkcamera.module.ad

import android.os.CountDownTimer
import androidx.lifecycle.*
import kotlinx.coroutines.NonCancellable.start

class AdvertisingManage(millisInFuture: Long = 4000) : DefaultLifecycleObserver {
    var advertisingManageListener: AdvertisingManageListener? = null
    private val countDownTimer = object : CountDownTimer(millisInFuture, 1000){
        override fun onTick(millisUntilFinished: Long) {
            advertisingManageListener?.timing((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            advertisingManageListener?.enterMainActivity()
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        start()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel()
    }

    /**
     * 开始计时
     */
    private fun start(){
        countDownTimer.start()
    }

    /**
     * 停止计时
     */
    private fun cancel(){
        countDownTimer.cancel()
    }

    /**
     *广告管理接口
     */
    interface AdvertisingManageListener {
        /**
         * 计时
         * @param second秒
         */
        fun timing(second: Int)
        /**
         * 计时结束， 进入主页面
         */
        fun enterMainActivity()
    }

}