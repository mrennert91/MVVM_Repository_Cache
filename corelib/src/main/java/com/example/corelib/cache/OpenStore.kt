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
        val PHOTO_STORE_KEY: String = "PhotoRepo"
        val ALBUM_STORE_KEY: String = "AlbumRepo"
        val POST_STORE_KEY: String = "PostRepo"
        val TODO_STORE_KEY: String = "TodoRepo"
    }
}