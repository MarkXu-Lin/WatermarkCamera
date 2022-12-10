package com.mark.watermarkcamera.net.wanandroid

class WanAndroidResponse {
    var curPage: Int = 0
    lateinit var datas: List<ProjectResponse>
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
}