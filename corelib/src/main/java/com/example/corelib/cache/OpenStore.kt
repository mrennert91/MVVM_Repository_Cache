package com.example.corelib.cache

import com.dropbox.android.external.store4.Store
import com.example.corelib.models.Album
import com.example.corelib.models.Photo
import com.example.corelib.models.Post
import com.example.corelib.models.Todo
import com.example.corelib.service.CloudService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenStore {

    companion object {

        var cloudService: CloudService? = null
        private var photo: Store<String, ArrayList<Photo>>? = null
        private var albumPhoto: Store<String, ArrayList<Photo>>? = null
        private var album: Store<String, ArrayList<Album>>? = null
        private var post: Store<String, ArrayList<Post>>? = null
        private var todo: Store<String, ArrayList<Todo>>? = null

        private var photoAlbumId: Int = -1

        val PHOTO_STORE_KEY: String = "PhotoRepo"
        val ALBUM_STORE_KEY: String = "AlbumRepo"
        val POST_STORE_KEY: String = "PostRepo"
        val TODO_STORE_KEY: String = "TodoRepo"

        fun getPhoto(): Store<String, ArrayList<Photo>> {
            if (photo == null)
                photo = CacheStore<String, ArrayList<Photo>>().getStore(object : CacheStore.Listener<ArrayList<Photo>> {
                    override suspend fun onExecute(): ArrayList<Photo> {
                        val photos = withContext(Dispatchers.IO) { cloudService?.photos!!.callSync() as ArrayList<Photo>? ?: arrayListOf() }
                        if (photos.isEmpty()) {
                            photo!!.clear(PHOTO_STORE_KEY)
                        }
                        return photos
                    }
                }, 1)
            return photo!!
        }

        fun getAlbumPhoto(albumId: Int, key: String): Store<String, ArrayList<Photo>> {
            photoAlbumId = albumId;
            if (albumPhoto == null)
                albumPhoto = CacheStore<String, ArrayList<Photo>>().getStore(object : CacheStore.Listener<ArrayList<Photo>> {
                    override suspend fun onExecute(): ArrayList<Photo> {
                        val photos = withContext(Dispatchers.IO) {
                            cloudService?.getPhotosFrom(photoAlbumId)!!.callSync() as ArrayList<Photo>? ?: arrayListOf()
                        }
                        if (photos.isEmpty()) {
                            albumPhoto!!.clear(key)
                        }
                        return photos
                    }
                }, 1)
            return albumPhoto!!
        }

        fun getAlbum(): Store<String, ArrayList<Album>> {
            if (album == null)
                album = CacheStore<String, ArrayList<Album>>().getStore(object : CacheStore.Listener<ArrayList<Album>> {
                    override suspend fun onExecute(): ArrayList<Album> {
                        val albums = withContext(Dispatchers.IO) { cloudService?.albums!!.callSync() as ArrayList<Album>? ?: arrayListOf() }
                        if (albums.isEmpty()) {
                            album!!.clear(ALBUM_STORE_KEY)
                        }
                        return albums
                    }
                }, 1)
            return album!!
        }

        fun getPost(): Store<String, ArrayList<Post>> {
            if (post == null)
                post = CacheStore<String, ArrayList<Post>>().getStore(object : CacheStore.Listener<ArrayList<Post>> {
                    override suspend fun onExecute(): ArrayList<Post> {
                        val posts = withContext(Dispatchers.IO) { cloudService?.posts!!.callSync() as ArrayList<Post>? ?: arrayListOf() }
                        if (posts.isEmpty()) {
                            post!!.clear(POST_STORE_KEY)
                        }
                        return posts
                    }
                }, 1)
            return post!!
        }

        fun getTodo(): Store<String, ArrayList<Todo>> {
            if (todo == null)
                todo = CacheStore<String, ArrayList<Todo>>().getStore(object : CacheStore.Listener<ArrayList<Todo>> {
                    override suspend fun onExecute(): ArrayList<Todo> {
                        val todos = withContext(Dispatchers.IO) { cloudService?.todos!!.callSync() as ArrayList<Todo>? ?: arrayListOf() }
                        if (todos.isEmpty()) {
                            todo!!.clear(TODO_STORE_KEY)
                        }
                        return todos
                    }
                }, 1)
            return todo!!
        }
    }

}