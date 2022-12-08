package com.mark.watermarkcamera.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.mark.watermarkcamera.BR
import javax.inject.Inject

class User @Inject constructor () {
    var id: Long = 0
    var username = ObservableField<String>()

    constructor(id: Long, username: String) : this() {
        this.username.set(username)
    }
}
