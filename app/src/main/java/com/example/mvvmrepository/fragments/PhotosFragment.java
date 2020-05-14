package com.example.mvvmrepository.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvvmrepository.MainActivity;
import com.example.mvvmrepository.MyApp;
import com.example.mvvmrepository.R;
import com.example.mvvmrepository.adapters.PhotosAdapter;
import com.example.mvvmrepository.databinding.FragmentPhotosBinding;
import com.example.corelib.models.Photo;
import com.example.corelib.viewmodels.PhotoViewModel;

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
public class PhotosFragment extends Fragment {

    private FragmentPhotosBinding binding;
    private PhotosAdapter adapter;
    private PhotoViewModel photoViewModel;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        ( (MainActivity) getActivity() ).setLoaderVisibility( true );
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance( getActivity().getApplication() );
        photoViewModel = new ViewModelProvider( this, factory ).get( PhotoViewModel.class );
        photoViewModel.init( (MyApp) getActivity().getApplication() );
        photoViewModel.fetchPhotos( false );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_photos, container, false );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( @NonNull View view, @Nullable Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );

        initAdapter();
        initPhotosList();

        photoViewModel.getPhotos().observe( this, new Observer<ArrayList<Photo>>() {
            @Override
            public void onChanged( ArrayList<Photo> photos ) {
                adapter.photos.clear();
                adapter.notifyDataSetChanged();
                if( photos != null ) {
                    adapter.photos.addAll( photos );
                    adapter.notifyDataSetChanged();
                    ( (MainActivity) getActivity() ).setLoaderVisibility( false );
                }
            }
        } );
        binding.refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                ( (MainActivity) getActivity() ).setLoaderVisibility( true );
                photoViewModel.fetchPhotos( true );
            }
        } );
    }

    private void initAdapter() {
        adapter = new PhotosAdapter();
    }

    private void initPhotosList() {
        binding.photosList.setLayoutManager( new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false ) );
        binding.photosList.setAdapter( adapter );
    }
}
