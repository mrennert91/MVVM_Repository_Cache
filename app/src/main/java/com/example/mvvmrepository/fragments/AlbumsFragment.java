package com.example.mvvmrepository.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmrepository.MainActivity;
import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.R;
import com.example.mvvmrepository.adapters.PhotosAdapter;
import com.example.mvvmrepository.databinding.FragmentAlbumsBinding;
import com.example.corelib.models.Album;
import com.example.corelib.models.Photo;
import com.example.corelib.viewmodels.AlbumViewModel;
import com.google.android.material.tabs.TabLayout;

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
public class AlbumsFragment extends Fragment {

    private FragmentAlbumsBinding binding;
    private PhotosAdapter adapter;
    private AlbumViewModel albumViewModel;

    public AlbumsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        ( (MainActivity) getActivity() ).setLoaderVisibility( true );
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance( getActivity().getApplication() );
        albumViewModel = new ViewModelProvider( this, factory ).get( AlbumViewModel.class );
        albumViewModel.init( (MyApp) getActivity().getApplication() );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_albums, container, false );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        initAdapter();
        initPhotosList();

        binding.albumTabs.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected( TabLayout.Tab tab ) {
                ( (MainActivity) getActivity() ).setLoaderVisibility( true );
                albumViewModel.fetchPhotoAlbums( tab.getPosition() + 1, tab.getText().toString() );
            }

            @Override
            public void onTabUnselected( TabLayout.Tab tab ) {

            }

            @Override
            public void onTabReselected( TabLayout.Tab tab ) {

            }
        } );
        albumViewModel.getAlbums().observe( this, new Observer<ArrayList<Album>>() {
            @Override
            public void onChanged( ArrayList<Album> albums ) {
                if( albums != null ) {
                    for( Album album : albums ) {
                        binding.albumTabs.addTab( binding.albumTabs.newTab().setText( "Album_" + String.valueOf( album.getId() ) ) );
                    }
                    ( (MainActivity) getActivity() ).setLoaderVisibility( false );
                }
            }
        } );
        albumViewModel.getPhotoAlbum().observe( this, new Observer<ArrayList<Photo>>() {
            @Override
            public void onChanged( ArrayList<Photo> photos ) {
                adapter.photos.clear();
                if( photos != null ) {
                    adapter.photos.addAll( photos );
                }
                adapter.notifyDataSetChanged();
                ( (MainActivity) getActivity() ).setLoaderVisibility( false );
            }
        } );
    }

    private void initAdapter() {
        adapter = new PhotosAdapter();
    }

    private void initPhotosList() {
        binding.albumsList.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
        binding.albumsList.setAdapter( adapter );
    }
}