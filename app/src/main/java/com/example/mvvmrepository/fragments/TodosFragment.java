package com.example.mvvmrepository.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmrepository.MainActivity;
import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.R;
import com.example.mvvmrepository.adapters.TodosAdapter;
import com.example.mvvmrepository.databinding.FragmentTodosBinding;
import com.example.corelib.models.Todo;
import com.example.corelib.viewmodels.TodoViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodosFragment extends Fragment {

    private FragmentTodosBinding binding;
    private TodosAdapter adapter;
    private TodoViewModel todoViewModel;

    public TodosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        ( (MainActivity) getActivity() ).setLoaderVisibility( true );
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance( getActivity().getApplication() );
        todoViewModel = new ViewModelProvider( this, factory ).get( TodoViewModel.class );
        todoViewModel.init( (MyApp) getActivity().getApplication() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_todos, container, false );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        initAdapter();
        initPhotosList();

        todoViewModel.getTodos().observe( this, new Observer<ArrayList<Todo>>() {
            @Override
            public void onChanged( ArrayList<Todo> todos ) {
                if( todos != null ) {
                    adapter.todos.addAll( todos );
                    adapter.notifyDataSetChanged();
                    ( (MainActivity) getActivity() ).setLoaderVisibility( false );
                }
            }
        } );
    }

    private void initAdapter() {
        adapter = new TodosAdapter();
    }

    private void initPhotosList() {
        binding.todosList.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
        binding.todosList.setAdapter( adapter );
    }
}
