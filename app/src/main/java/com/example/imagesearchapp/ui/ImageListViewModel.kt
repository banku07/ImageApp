package com.example.imagesearchapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchapp.Constants
import com.example.imagesearchapp.model.ImageData
import com.example.imagesearchapp.model.Result
import com.example.imagesearchapp.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(val repository: ImageRepository):ViewModel() {
     val result :MutableLiveData<Result> = MutableLiveData()
     val imageData:MutableLiveData<ImageData> = MutableLiveData()

    init {
     getImagesData()
    }


    fun getImagesData(query:String = Constants.DEFAULT_SEARCH){
        viewModelScope.launch(Dispatchers.IO){
            result.postValue(repository.fetchData( query))
        }
    }
}