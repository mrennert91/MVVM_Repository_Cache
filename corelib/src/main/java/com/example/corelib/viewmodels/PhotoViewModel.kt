package com.example.corelib.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corelib.CoreApp
import com.example.corelib.models.Photo
import com.example.corelib.repositories.PhotoRepository
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class PhotoViewModel : ViewModel() {

    private var photos: MutableLiveData<ArrayList<Photo>>? = null
    private var photoRepository: PhotoRepository? = null

    fun init(myApp: CoreApp) {
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