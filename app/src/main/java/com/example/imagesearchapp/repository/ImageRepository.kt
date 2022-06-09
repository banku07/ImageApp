package com.example.imagesearchapp.repository

import com.example.imagesearchapp.Constants
import com.example.imagesearchapp.model.Result
import com.example.imagesearchapp.network.Service
import java.io.IOException

class ImageRepository(var retroService: Service) {


    /**
     * function fetch the result from api
     */
   suspend fun fetchData( query:String) : Result {
        return try {
            val response = retroService.getData(
                Constants.API_KEY
                ,query,Constants.IMAGE_TYPE,true)
            if(response.isSuccessful && !response.body()?.data?.isEmpty()!!){
                Result.Success(response)
            }else{
                Result.Error(response.message(),true)
            }
        } catch (error: IOException) {
            Result.Error("OOOPS NOT ABLE TO FETCH THE DATA",true)
        }
    }


}