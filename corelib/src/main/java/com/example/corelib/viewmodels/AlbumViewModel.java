package com.example.corelib.viewmodels;

import com.example.corelib.CoreApp;
import com.example.corelib.models.Album;
import com.example.corelib.models.Photo;
import com.example.corelib.repositories.AlbumRepository;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlbumViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Album>> albums;
    private MutableLiveData<ArrayList<Photo>> photoAlbum;
    private AlbumRepository albumRepository;

    public void init( CoreApp myApp ) {
        if( albums != null ) {
            return;
        }
        albumRepository = new AlbumRepository( myApp );
        fetchAlbums();
        fetchPhotoAlbums( -1, null );
    }

    public LiveData<ArrayList<Album>> getAlbums() {
        return albums;
    }

    private void fetchAlbums() {
        albums = albumRepository.getAlbums( true );
    }

    public LiveData<ArrayList<Photo>> getPhotoAlbum() {
        return photoAlbum;
    }

    public void fetchPhotoAlbums( int albumId, String key ) {
        photoAlbum = albumRepository.getPhotoAlbums( albumId, key, true );
    }
}
