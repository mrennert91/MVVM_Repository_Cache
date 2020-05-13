package com.example.mvvmrepository.service;

import com.example.mvvmrepository.models.Album;
import com.example.mvvmrepository.models.Photo;
import com.example.mvvmrepository.models.Post;
import com.example.mvvmrepository.models.Todo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface<T> {

    @GET("/photos")
    Call<ArrayList<Photo>> getPhotos();

    @GET("/albums")
    Call<ArrayList<Album>> getAlbums();

    @GET("/albums/{albumId}/photos")
    Call<ArrayList<Photo>> getPhotosFrom(@Path( value = "albumId") int albumId );

    @GET("/posts")
    Call<ArrayList<Post>> getPosts();

    @GET("/todos")
    Call<ArrayList<Todo>> getTodos();

}
