package com.mark.watermarkcamera.test

import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mark.watermarkcamera.module.ad.AdvertisingManage

class DemoActivity: Activity(), LifecycleOwner {
    lateinit var lifecycleRegistry: LifecycleRegistry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}