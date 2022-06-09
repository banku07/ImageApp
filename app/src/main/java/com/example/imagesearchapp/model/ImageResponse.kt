package com.example.imagesearchapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ImageResponse(
    @SerializedName("hits")
    val  data :List<ImageData>
)
@Parcelize
data class ImageData(
    val id:Int,
    val tags : String,
    @SerializedName("largeImageURL")
    val ImageUrl:String,
    val views : Int,
    val downloads:Int,
    val likes:Int,
    val user:String,
    @SerializedName("userImageURL")
    val userImage:String,
    val user_id:Int,
    val imageWidth :Int,
    val imageHeight:Int,


) : Parcelable
