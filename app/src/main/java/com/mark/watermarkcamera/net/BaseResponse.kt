package com.mark.watermarkcamera.net

class BaseResponse<T> {
    var data: T? = null
    var errorCode: Int = 0
    lateinit var errorMsg: String
}