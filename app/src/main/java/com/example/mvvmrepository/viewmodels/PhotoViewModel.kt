package com.example.mvvmrepository.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmrepository.MyApp
import com.example.mvvmrepository.models.Photo
import com.example.mvvmrepository.repositories.PhotoRepository
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PhotoViewModel : ViewModel() {

    private var photos: MutableLiveData<ArrayList<Photo>>? = null
    private var photoRepository: PhotoRepository? = null

    fun init(myApp: MyApp) {
        photoRepository = PhotoRepository(myApp)
    }

    fun getPhotos(): LiveData<ArrayList<Photo>>? {
        return photos
    }

    fun fetchPhotos(refresh: Boolean) {
        if (refresh) {
            photoRepository?.getFresh()
        } else {
            photos = photoRepository?.getPhotos(true)
        }
    }
}