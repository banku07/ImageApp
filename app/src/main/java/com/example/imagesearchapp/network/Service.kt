package com.example.imagesearchapp.network

import com.example.imagesearchapp.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    companion object{
        const val BASE_URL = "https://pixabay.com/"
    }

    @GET("api/")
    suspend fun getData(@Query("key") apiKey:String,
                        @Query("q") searchString:String,
                        @Query("image_type") image_type:String,
                        @Query("pretty")boolean: Boolean):Response<ImageResponse?>

}