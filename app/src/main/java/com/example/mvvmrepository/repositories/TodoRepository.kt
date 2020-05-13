package com.example.mvvmrepository.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.get
import com.dropbox.android.external.store4.nonFlowValueFetcher
import com.example.mvvmrepository.MyApp
import com.example.mvvmrepository.cache.OpenStore
import com.example.mvvmrepository.models.Album
import com.example.mvvmrepository.models.Todo
import com.example.mvvmrepository.service.AbstractServiceCaller
import com.example.mvvmrepository.service.CloudService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class TodoRepository(private val myApp: MyApp) {

    private val todosList: MutableLiveData<ArrayList<Todo>> = MutableLiveData()

    private fun getTodos(): MutableLiveData<ArrayList<Todo>> {
        myApp.cloudSevice.todos.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Todo>> {
            override fun onError() {
                todosList.value = null
            }

            override fun onOkResult(result: ArrayList<Todo>?) {
                todosList.value = result
            }
        })
        return todosList;
    }

    @ExperimentalTime
    fun getTodos(cache: Boolean): MutableLiveData<ArrayList<Todo>> {
        if (cache) {
            CoroutineScope(Dispatchers.Main).launch {
                todosList.value = withContext(Dispatchers.IO) { myApp.todoStore.get(OpenStore.TODO_STORE_KEY) }
            }
        } else {
            return getTodos()
        }
        return todosList
    }
}