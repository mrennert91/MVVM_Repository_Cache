package com.example.mvvmrepository.viewmodels;

import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.models.Post;
import com.example.mvvmrepository.repositories.PostRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PostViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Post>> posts;
    private PostRepository postRepository;

    public void init( MyApp myApp ) {
        if( posts != null ) {
            return;
        }
        postRepository = new PostRepository( myApp );
        fetchPosts();
    }

    public LiveData<ArrayList<Post>> getPosts() {
        return posts;
    }

    private void fetchPosts() {
        posts = postRepository.getPosts( true );
    }

}