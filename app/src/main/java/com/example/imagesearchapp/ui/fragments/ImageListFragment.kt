package com.example.imagesearchapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearchapp.core.CoreEvent
import com.example.imagesearchapp.core.CoreLib
import com.example.imagesearchapp.core.Event
import com.example.imagesearchapp.core.EventListner
import com.example.imagesearchapp.Constants
import com.example.imagesearchapp.databinding.FragmentImageListBinding
import com.example.imagesearchapp.ui.ImageListViewModel
import com.example.imagesearchapp.model.Result
import com.example.imagesearchapp.ui.ImageListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [ImageListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ImageListFragment : Fragment(), EventListner {
 private val viewModel : ImageListViewModel by activityViewModels()
    lateinit var viewBinding: FragmentImageListBinding
    lateinit var imageAdapter : ImageListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentImageListBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setHasOptionsMenu(true)
        viewBinding.errorView.button.setOnClickListener {
            viewBinding.errorView.root.visibility =View.GONE
            CoreLib.getDispatcher()?.dispatchEvent(CoreEvent(Constants.PROGRESS_START,null))
            viewModel.getImagesData()
        }
        setUpObserver()
    }

    fun setUpRecyclerView(){
        imageAdapter = ImageListAdapter()
        viewBinding.recyclerView.adapter = imageAdapter
        viewBinding.recyclerView.layoutManager =LinearLayoutManager(requireContext())
    }

    override fun onResume() {
        super.onResume()
        CoreLib.getDispatcher()?.addEventListener(Constants.SEARCH_QUERY,this)
    }

    override fun onPause() {
        super.onPause()
        CoreLib.getDispatcher()?.removeEventListener(Constants.SEARCH_QUERY,this)
    }

    fun  setUpObserver(){
        viewModel.result.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success ->{
                    imageAdapter.setData(it.data.body()!!.data)
                    CoreLib.getDispatcher()?.dispatchEvent(CoreEvent(Constants.PROGRESS_STOP,null))
                }
                is Result.Error ->{
                    imageAdapter.setData(null)
                    CoreLib.getDispatcher()?.dispatchEvent(CoreEvent(Constants.PROGRESS_STOP,null))
                    viewBinding.errorView.root.visibility =View.VISIBLE
                }
            }
        }
    }

    override fun onEvent(event: Event?) {
        if (event?.type == Constants.SEARCH_QUERY){
            val coreEvent = event as CoreEvent
            val query = coreEvent.getmExtra()?.get(Constants.ARGUMENTS) as String
            viewModel.getImagesData(query)
        }
    }

}