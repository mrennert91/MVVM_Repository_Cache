package com.example.corelib

import android.app.Application
import com.dropbox.android.external.store4.Store
import com.example.corelib.cache.CacheStore
import com.example.corelib.cache.OpenStore
import com.example.corelib.models.Album
import com.example.corelib.models.Photo
import com.example.corelib.models.Post
import com.example.corelib.models.Todo
import com.example.corelib.service.CloudService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class CoreApp : Application() {

    lateinit var cloudService: CloudService
    lateinit var photoStore: Store<String, ArrayList<Photo>>
    lateinit var albumStore: Store<String, ArrayList<Album>>
    lateinit var postStore: Store<String, ArrayList<Post>>
    lateinit var todoStore: Store<String, ArrayList<Todo>>

    private var albumPhoto: Store<String, ArrayList<Photo>>? = null

    private var photoAlbumId: Int = -1

    override fun onCreate() {
        super.onCreate()

        photoStore = getPhoto()
        albumStore = getAlbum()
        postStore = getPost()
        todoStore = getTodo()
    }

    fun getPhotoAlbumStore(albumId: Int, key: String): Store<String, ArrayList<Photo>> {
        return getAlbumPhoto(albumId, key);
    }

    private fun getPhoto(): Store<String, ArrayList<Photo>> {
        return CacheStore<String, ArrayList<Photo>>().getStore(object : CacheStore.Listener<ArrayList<Photo>> {
            override suspend fun onExecute(): ArrayList<Photo> {
                val photos = withContext(Dispatchers.IO) { cloudService.photos!!.callSync() as ArrayList<Photo>? ?: arrayListOf() }
                if (photos.isEmpty()) {
                    photoStore.clear(OpenStore.PHOTO_STORE_KEY)
                }
                return photos
            }
        }, 1)
    }

    private fun getAlbumPhoto(albumId: Int, key: String): Store<String, ArrayList<Photo>> {
        photoAlbumId = albumId;
        if (albumPhoto == null)
            albumPhoto = CacheStore<String, ArrayList<Photo>>().getStore(object : CacheStore.Listener<ArrayList<Photo>> {
                override suspend fun onExecute(): ArrayList<Photo> {
                    val photos = withContext(Dispatchers.IO) {
                        cloudService.getPhotosFrom(photoAlbumId)!!.callSync() as ArrayList<Photo>? ?: arrayListOf()
                    }
                    if (photos.isEmpty()) {
                        albumPhoto!!.clear(key)
                    }
                    return photos
                }
            }, 1)
        return albumPhoto!!
    }

    private fun getAlbum(): Store<String, ArrayList<Album>> {
        return CacheStore<String, ArrayList<Album>>().getStore(object : CacheStore.Listener<ArrayList<Album>> {
            override suspend fun onExecute(): ArrayList<Album> {
                val albums = withContext(Dispatchers.IO) { cloudService.albums!!.callSync() as ArrayList<Album>? ?: arrayListOf() }
                if (albums.isEmpty()) {
                    albumStore.clear(OpenStore.ALBUM_STORE_KEY)
                }
                return albums
            }
        }, 1)
    }

    private fun getPost(): Store<String, ArrayList<Post>> {
        return CacheStore<String, ArrayList<Post>>().getStore(object : CacheStore.Listener<ArrayList<Post>> {
            override suspend fun onExecute(): ArrayList<Post> {
                val posts = withContext(Dispatchers.IO) { cloudService.posts!!.callSync() as ArrayList<Post>? ?: arrayListOf() }
                if (posts.isEmpty()) {
                    postStore.clear(OpenStore.POST_STORE_KEY)
                }
                return posts
            }
        }, 1)
    }

    private fun getTodo(): Store<String, ArrayList<Todo>> {
        return CacheStore<String, ArrayList<Todo>>().getStore(object : CacheStore.Listener<ArrayList<Todo>> {
            override suspend fun onExecute(): ArrayList<Todo> {
                val todos = withContext(Dispatchers.IO) { cloudService.todos!!.callSync() as ArrayList<Todo>? ?: arrayListOf() }
                if (todos.isEmpty()) {
                    todoStore.clear(OpenStore.TODO_STORE_KEY)
                }
                return todos
            }
        }, 1)
    }
}