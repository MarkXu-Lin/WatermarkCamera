package com.mark.watermarkcamera.module.ad

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdvertisingViewModel: ViewModel() {
    var millisInFuture: Long = 4000
    var timingResult = MutableLiveData<Long>()

    fun setTimingResult(millisInFuture: Long){
        timingResult.value = millisInFuture
    }
}