package com.example.corelib.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import com.example.corelib.CoreApp
import com.example.corelib.cache.OpenStore
import com.example.corelib.models.Photo
import com.example.corelib.service.AbstractServiceCaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import kotlin.time.ExperimentalTime

class PhotoRepository(private val myApp: CoreApp) {

    private val photosList: MutableLiveData<ArrayList<Photo>> = MutableLiveData()

    private fun getPhotos(): MutableLiveData<ArrayList<Photo>> {
        myApp.cloudService!!.photos.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Photo>> {
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