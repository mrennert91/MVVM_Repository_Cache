package com.example.mvvmrepository.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmrepository.MainActivity;
import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.R;
import com.example.mvvmrepository.adapters.PostsAdapter;
import com.example.mvvmrepository.databinding.FragmentPostsBinding;
import com.example.mvvmrepository.models.Post;
import com.example.mvvmrepository.viewmodels.PostViewModel;

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
public class PostsFragment extends Fragment {

    private FragmentPostsBinding binding;
    private PostsAdapter adapter;
    private PostViewModel postViewModel;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        ( (MainActivity) getActivity() ).setLoaderVisibility( true );
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance( getActivity().getApplication() );
        postViewModel = new ViewModelProvider( this, factory ).get( PostViewModel.class );
        postViewModel.init( (MyApp) getActivity().getApplication() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_posts, container, false );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        initAdapter();
        initPhotosList();

        postViewModel.getPosts().observe( this, new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged( ArrayList<Post> posts ) {
                if( posts != null ) {
                    adapter.posts.addAll( posts );
                    adapter.notifyDataSetChanged();
                    ( (MainActivity) getActivity() ).setLoaderVisibility( false );
                }
            }
        } );
    }

    private void initAdapter() {
        adapter = new PostsAdapter();
    }

    private void initPhotosList() {
        binding.postsList.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
        binding.postsList.setAdapter( adapter );
    }
}