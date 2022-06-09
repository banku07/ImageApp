package com.example.imagesearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchapp.core.CoreEvent
import com.example.imagesearchapp.core.CoreLib
import com.example.imagesearchapp.Constants
import com.example.imagesearchapp.databinding.ImageCardLayoutBinding
import com.example.imagesearchapp.loadCircularImage
import com.example.imagesearchapp.loadImage
import com.example.imagesearchapp.model.ImageData


class ImageListAdapter() : RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    lateinit var binding: ImageCardLayoutBinding
    var recyclerViewItem: List<ImageData>? = emptyList()

    class ImageViewHolder(var data: ImageCardLayoutBinding) : RecyclerView.ViewHolder(data.root) {

        fun update(imageDate: ImageData) {
            loadImage(data.root.context,data.flowerImage,imageDate.ImageUrl)
            loadCircularImage(data.root.context,data.userImage,imageDate.userImage)
            data.userName.text=imageDate.user
            data.heart.likes.text=imageDate.likes.toString()
            data.cardView.setOnClickListener {
                val data = Bundle()
                data.putParcelable(Constants.ARGUMENTS,imageDate)
                CoreLib.getDispatcher()?.dispatchEvent(CoreEvent(Constants.ADAPTER_CLICK, data))

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ImageCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        with(recyclerViewItem!!.get(position)) {
            holder.update(this)
        }
    }

    override fun getItemCount(): Int {
        return if (recyclerViewItem!=null){ recyclerViewItem!!.size} else{-1}
    }

    fun setData(listData: List<ImageData>?) {
        recyclerViewItem = listData
        notifyDataSetChanged()
    }
}