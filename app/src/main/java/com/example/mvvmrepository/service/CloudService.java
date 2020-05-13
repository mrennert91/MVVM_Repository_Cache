package com.example.mvvmrepository.service;

import com.example.mvvmrepository.models.Album;
import com.example.mvvmrepository.models.Photo;
import com.example.mvvmrepository.models.Post;
import com.example.mvvmrepository.models.Todo;

import java.util.ArrayList;

public class CloudService {

    private APIInterface apiInterface = RetrofitService.getRetrofit().create( APIInterface.class );

    public AbstractServiceCaller getPhotos() {
        return new AbstractServiceCaller<ArrayList<Photo>>( apiInterface.getPhotos() );
    }

    public AbstractServiceCaller getPhotosFrom( int albumId ) {
        return new AbstractServiceCaller<ArrayList<Photo>>( apiInterface.getPhotosFrom( albumId ) );
    }

    public AbstractServiceCaller getTodos() {
        return new AbstractServiceCaller<ArrayList<Todo>>( apiInterface.getTodos() );
    }

    public AbstractServiceCaller getAlbums() {
        return new AbstractServiceCaller<ArrayList<Album>>( apiInterface.getAlbums() );
    }

    public AbstractServiceCaller getPosts() {
        return new AbstractServiceCaller<ArrayList<Post>>( apiInterface.getPosts() );
    }
}
