package com.example.mvvmrepository;

import android.app.Application;

import com.dropbox.android.external.store4.Store;
import com.example.mvvmrepository.cache.OpenStore;
import com.example.mvvmrepository.models.Album;
import com.example.mvvmrepository.models.Photo;
import com.example.mvvmrepository.models.Post;
import com.example.mvvmrepository.models.Todo;
import com.example.mvvmrepository.service.CloudService;

import java.util.ArrayList;

public class MyApp extends Application {

    private CloudService cloudService;
    private Store<String, ArrayList<Photo>> photoStore;
    private Store<String, ArrayList<Album>> albumStore;
    private Store<String, ArrayList<Post>> postStore;
    private Store<String, ArrayList<Todo>> todoStore;

    @Override
    public void onCreate() {
        super.onCreate();

        cloudService = new CloudService();
        OpenStore.Companion.setCloudService( cloudService );
        photoStore = OpenStore.Companion.getPhoto();
        albumStore = OpenStore.Companion.getAlbum();
        postStore = OpenStore.Companion.getPost();
        todoStore = OpenStore.Companion.getTodo();
    }

    public CloudService getCloudSevice() {
        return cloudService;
    }

    public Store<String, ArrayList<Photo>> getPhotoStore() {
        return photoStore;
    }

    public Store<String, ArrayList<Album>> getAlbumStore() {
        return albumStore;
    }

    public Store<String, ArrayList<Post>> getPostStore() {
        return postStore;
    }

    public Store<String, ArrayList<Todo>> getTodoStore() {
        return todoStore;
    }

    public Store<String, ArrayList<Photo>> getPhotoAlbumStore( int albumId, String key ) {
        return OpenStore.Companion.getAlbumPhoto( albumId, key );
    }
}
