package com.aafiyahtech.ventilator.config

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

        @GET(".")
        fun testAlive(@Query("id") id: Int): retrofit2.Call<ResponseBody>


        @GET(".")
        fun getGroup1A(@Query("req") req: String): retrofit2.Call<ResponseBody>

        @GET(".")
        fun setGroup1A(@Query("req") req: String, @Query("data") data: String ): retrofit2.Call<ResponseBody>

}
