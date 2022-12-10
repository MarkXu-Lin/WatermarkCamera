package com.mark.watermarkcamera.api.wanandroid

import com.mark.watermarkcamera.net.BaseResponse
import com.mark.watermarkcamera.net.wanandroid.WanAndroidResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiWanAndroid {
    @GET("project/list/{pageNum}/json?cid=294")
    suspend fun loadDataByPage(@Path("pageNum") pageNum: Int, @Query("page_size") pageSize: Int): BaseResponse<WanAndroidResponse>
}