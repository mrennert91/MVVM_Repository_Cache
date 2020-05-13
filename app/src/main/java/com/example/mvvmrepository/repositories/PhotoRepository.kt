package com.example.mvvmrepository.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.*
import com.example.mvvmrepository.MyApp
import com.example.mvvmrepository.cache.OpenStore
import com.example.mvvmrepository.models.Photo
import com.example.mvvmrepository.service.AbstractServiceCaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import kotlin.time.ExperimentalTime

class PhotoRepository(private val myApp: MyApp) {

    private val photosList: MutableLiveData<ArrayList<Photo>> = MutableLiveData()

    private fun getPhotos(): MutableLiveData<ArrayList<Photo>> {
        myApp.cloudSevice.photos.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Photo>> {
            override fun onError() {
                photosList.value = null
            }

            override fun onOkResult(result: ArrayList<Photo>?) {
                photosList.value = result
            }
        })
        return photosList;
    }

    @ExperimentalTime
    fun getPhotos(cache: Boolean): MutableLiveData<ArrayList<Photo>> {
        if (cache) {
            CoroutineScope(Dispatchers.Main).launch {
                photosList.value = withContext(Dispatchers.IO) { myApp.photoStore.get(OpenStore.PHOTO_STORE_KEY) }
            }
        } else {
            return getPhotos()
        }
        return photosList
    }

    fun getFresh() {
        CoroutineScope(Dispatchers.Main).launch {
            photosList.value = withContext(Dispatchers.IO) { myApp.photoStore.fresh(OpenStore.PHOTO_STORE_KEY) }
        }
    }
}