package com.abdelmageed.nagwaandroidtask.data.remote

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    suspend fun download(@Url fileUrl: String): ResponseBody

}