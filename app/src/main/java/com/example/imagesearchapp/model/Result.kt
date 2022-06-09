package com.example.imagesearchapp.model

import retrofit2.Response

sealed class Result {
    data class Success(val data: Response<ImageResponse?>): Result()
    data class Error(val error:String, val boolean: Boolean): Result()
}


