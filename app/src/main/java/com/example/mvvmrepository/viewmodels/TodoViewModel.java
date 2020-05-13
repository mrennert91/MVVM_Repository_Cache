package com.example.mvvmrepository.viewmodels;

import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.models.Todo;
import com.example.mvvmrepository.repositories.TodoRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Todo>> todos;
    private TodoRepository todoRepository;

    public void init( MyApp myApp ) {
        if( todos != null ) {
            return;
        }
        todoRepository = new TodoRepository( myApp );
        fetchTodos();
    }

    public LiveData<ArrayList<Todo>> getTodos() {
        return todos;
    }

    private void fetchTodos() {
        todos = todoRepository.getTodos( true );
    }
}