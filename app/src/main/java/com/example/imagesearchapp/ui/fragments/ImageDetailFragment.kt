package com.example.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.ImageDetailLayoutBinding
import com.example.imagesearchapp.loadCircularImage
import com.example.imagesearchapp.loadImage
import com.example.imagesearchapp.ui.ImageListViewModel
import com.example.imagesearchapp.model.ImageData

/**
 * A simple [Fragment] subclass.
 * Use the [ImageDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageDetailFragment : Fragment() {

    lateinit var binding: ImageDetailLayoutBinding
    val viewModel : ImageListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImageDetailLayoutBinding.inflate(LayoutInflater.from(requireContext()),container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.imageData.observe(viewLifecycleOwner){
            setUpUI(it)
        }
    }

    /**
     * function setup the UI data for Detail Screen
     */
    fun setUpUI( imageData: ImageData){
        loadImage(binding.root.context,binding.imageDetail.flowerImage,imageData.ImageUrl)
        binding.imageDetail.userImage.visibility = View.GONE
        binding.imageDetail.heart.root.visibility =View.GONE
        loadCircularImage(binding.root.context,binding.user,imageData.userImage)
        binding.layout1.tags.text = getString(R.string.uploaded_by)
        binding.layout2.tags.text = getString(R.string.image_tags)
        binding.layout3.tags.text = getString(R.string.user_id)
        binding.layout1.textName.text = imageData.user
        binding.layout2.textName.text = imageData.tags
        binding.layout3.textName.text = imageData.user_id.toString()
        binding.layout4.tags.text = getString(R.string.dowmnloads)
        binding.layout4.textName.text = imageData.downloads.toString()
        binding.layout5.tags.text = getString(R.string.image_resolution)
        binding.layout5.textName.text = "${imageData.imageWidth} * ${imageData.imageHeight} "
        binding.heratLayout.likes.text = imageData.likes.toString()
    }
}