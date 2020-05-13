package com.example.mvvmrepository.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import com.dropbox.android.external.store4.nonFlowValueFetcher
import com.example.mvvmrepository.MyApp
import com.example.mvvmrepository.cache.OpenStore
import com.example.mvvmrepository.models.Album
import com.example.mvvmrepository.models.Post
import com.example.mvvmrepository.service.AbstractServiceCaller
import com.example.mvvmrepository.service.CloudService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class PostRepository(private val myApp: MyApp) {

    private val postsList: MutableLiveData<ArrayList<Post>> = MutableLiveData()

    private fun getPosts(): MutableLiveData<ArrayList<Post>> {
        myApp.cloudSevice.posts.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Post>> {
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