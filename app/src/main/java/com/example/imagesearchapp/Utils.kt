package com.example.imagesearchapp

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun loadImage(context: Context,target:ImageView,url:String){
    Glide.with(context).load(url).into(target)
}

fun  loadCircularImage(context: Context,target:ImageView,url:String){
    Glide.with( context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(target)
}