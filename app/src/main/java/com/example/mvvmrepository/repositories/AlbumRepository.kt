package com.example.mvvmrepository.repositories

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.get
import com.example.mvvmrepository.MyApp
import com.example.mvvmrepository.cache.OpenStore
import com.example.mvvmrepository.models.Album
import com.example.mvvmrepository.models.Photo
import com.example.mvvmrepository.service.AbstractServiceCaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class AlbumRepository(private val myApp: MyApp) {

    private val albumsList: MutableLiveData<ArrayList<Album>> = MutableLiveData()
    private val photoAlbumsList: MutableLiveData<ArrayList<Photo>> = MutableLiveData()

    private fun getAlbums(): MutableLiveData<ArrayList<Album>> {
        myApp.cloudSevice.albums.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Album>> {
            override fun onError() {
                albumsList.value = null
            }

            override fun onOkResult(result: ArrayList<Album>?) {
                albumsList.value = result
            }
        })
        return albumsList;
    }

    @ExperimentalTime
    fun getAlbums(cache: Boolean): MutableLiveData<ArrayList<Album>> {
        if (cache) {
            CoroutineScope(Dispatchers.Main).launch { albumsList.value = myApp.albumStore.get(OpenStore.ALBUM_STORE_KEY) }
        } else {
            return getAlbums()
        }
        return albumsList
    }

    private fun getPhotoAlbums(albumId: Int): MutableLiveData<ArrayList<Photo>> {
        myApp.cloudSevice.getPhotosFrom(albumId).callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Photo>> {
            override fun onError() {
                photoAlbumsList.value = null
            }

            override fun onOkResult(result: ArrayList<Photo>?) {
                photoAlbumsList.value = result
            }
        })
        return photoAlbumsList;
    }

    @ExperimentalTime
    fun getPhotoAlbums(albumId: Int, key: String?, cache: Boolean): MutableLiveData<ArrayList<Photo>> {
        if (cache) {
            if (albumId >= 0 && !key.isNullOrBlank())
                CoroutineScope(Dispatchers.Main).launch { photoAlbumsList.value = myApp.getPhotoAlbumStore(albumId, key).get(key) }
        } else {
            return getPhotoAlbums(albumId)
        }
        return photoAlbumsList
    }
}