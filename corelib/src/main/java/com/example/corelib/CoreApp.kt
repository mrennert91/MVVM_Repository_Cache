package com.example.corelib

import android.app.Application
import com.dropbox.android.external.store4.Store
import com.example.corelib.cache.OpenStore
import com.example.corelib.models.Album
import com.example.corelib.models.Photo
import com.example.corelib.models.Post
import com.example.corelib.models.Todo
import com.example.corelib.service.CloudService

open class CoreApp : Application() {

    var cloudService: CloudService? = null
    var photoStore: Store<String, ArrayList<Photo>>? = null
    var albumStore: Store<String, ArrayList<Album>>? = null
    var postStore: Store<String, ArrayList<Post>>? = null
    var todoStore: Store<String, ArrayList<Todo>>? = null

    override fun onCreate() {
        super.onCreate()

        cloudService = CloudService()
        OpenStore.cloudService = cloudService
        photoStore = OpenStore.getPhoto()
        albumStore = OpenStore.getAlbum()
        postStore = OpenStore.getPost()
        todoStore = OpenStore.getTodo()
    }

    fun getPhotoAlbumStore(albumId: Int, key: String): Store<String, ArrayList<Photo>> {
        return OpenStore.getAlbumPhoto(albumId, key);
    }
}