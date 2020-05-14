package com.example.corelib.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.get
import com.example.corelib.CoreApp
import com.example.corelib.cache.OpenStore
import com.example.corelib.models.Post
import com.example.corelib.service.AbstractServiceCaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime

class PostRepository(private val myApp: CoreApp) {

    private val postsList: MutableLiveData<ArrayList<Post>> = MutableLiveData()

    private fun getPosts(): MutableLiveData<ArrayList<Post>> {
        myApp.cloudService!!.posts.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Post>> {
            override fun onError() {
                postsList.value = null
            }

            override fun onOkResult(result: ArrayList<Post>?) {
                postsList.value = result
            }
        })
        return postsList;
    }

    @ExperimentalTime
    fun getPosts(cache: Boolean): MutableLiveData<ArrayList<Post>> {
        if (cache) {
            CoroutineScope(Dispatchers.Main).launch {
                postsList.value = withContext(Dispatchers.IO) { myApp.postStore.get(OpenStore.POST_STORE_KEY) }
            }
        } else {
            return getPosts()
        }
        return postsList
    }
}