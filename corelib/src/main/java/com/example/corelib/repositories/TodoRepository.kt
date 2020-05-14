package com.example.corelib.repositories

import androidx.lifecycle.MutableLiveData
import com.dropbox.android.external.store4.get
import com.example.corelib.CoreApp
import com.example.corelib.cache.OpenStore
import com.example.corelib.models.Todo
import com.example.corelib.service.AbstractServiceCaller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

class TodoRepository(private val myApp: CoreApp) {

    private val todosList: MutableLiveData<ArrayList<Todo>> = MutableLiveData()

    private fun getTodos(): MutableLiveData<ArrayList<Todo>> {
        myApp.cloudService!!.todos.callAsync(object : AbstractServiceCaller.CloudServiceListener<ArrayList<Todo>> {
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
                todosList.value = withContext(Dispatchers.IO) { myApp.todoStore!!.get(OpenStore.TODO_STORE_KEY) }
            }
        } else {
            return getTodos()
        }
        return todosList
    }
}