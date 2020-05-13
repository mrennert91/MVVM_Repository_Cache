package com.example.mvvmrepository.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mvvmrepository.R;
import com.example.mvvmrepository.databinding.TodoItemBinding;
import com.example.mvvmrepository.models.Todo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.ViewHolder> {

    public ArrayList<Todo> todos;

    public TodosAdapter() {
        this.todos = new ArrayList<>();
    }

    @NonNull
    @Override
    public TodosAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        return new TodosAdapter.ViewHolder( (TodoItemBinding) DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.todo_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull TodosAdapter.ViewHolder holder, int position ) {
        Todo todo = todos.get( position );

        holder.itemBinding.title.setText( todo.getTitle() );
        holder.itemBinding.index.setText( String.valueOf( todo.getId() ) );
        if( todo.isCompleted() ) {
            holder.itemBinding.wrapper.setBackgroundColor( Color.parseColor( "#FFFFFF" ) );
        } else {
            holder.itemBinding.wrapper.setBackgroundColor( Color.parseColor( "#FFCDDC" ) );
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TodoItemBinding itemBinding;

        ViewHolder( @NonNull TodoItemBinding itemView ) {
            super( itemView.getRoot() );
            this.itemBinding = itemView;
        }
    }
}
