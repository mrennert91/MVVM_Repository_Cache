package com.example.corelib.viewmodels;

import com.example.corelib.CoreApp;
import com.example.corelib.models.Todo;
import com.example.corelib.repositories.TodoRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TodoViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Todo>> todos;
    private TodoRepository todoRepository;

    public void init( CoreApp myApp ) {
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