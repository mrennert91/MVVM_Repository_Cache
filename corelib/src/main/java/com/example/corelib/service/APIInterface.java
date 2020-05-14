package com.example.corelib.service;

import com.example.corelib.models.Album;
import com.example.corelib.models.Photo;
import com.example.corelib.models.Post;
import com.example.corelib.models.Todo;

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
