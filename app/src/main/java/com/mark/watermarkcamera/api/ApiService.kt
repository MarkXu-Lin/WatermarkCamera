package com.mark.watermarkcamera.api

import com.mark.watermarkcamera.bean.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun queryData(): List<User>
}