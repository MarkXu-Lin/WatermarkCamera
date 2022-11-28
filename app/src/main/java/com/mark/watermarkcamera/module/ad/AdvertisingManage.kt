package com.mark.watermarkcamera.module.ad

import android.os.CountDownTimer

class AdvertisingManage {
    var advertisingManageListener: AdvertisingManageListener? = null
    private val countDownTimer = object : CountDownTimer(4000, 1000){
        override fun onTick(millisUntilFinished: Long) {
            advertisingManageListener?.timing((millisUntilFinished / 1000).toInt())
        }

        override fun onFinish() {
            advertisingManageListener?.enterMainActivity()
        }
    }

    /**
     * 开始计时
     */
    fun start(){
        countDownTimer.start()
    }

    /**
     * 停止计时
     */
    fun cancel(){
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