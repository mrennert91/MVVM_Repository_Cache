package com.example.mvvmrepository.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mvvmrepository.R;
import com.example.mvvmrepository.databinding.AlbumItemBinding;
import com.example.corelib.models.Album;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    public ArrayList<Album> albums;

    public AlbumsAdapter() {
        this.albums = new ArrayList<>();
    }

    @NonNull
    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        return new AlbumsAdapter.ViewHolder( (AlbumItemBinding) DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.album_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull AlbumsAdapter.ViewHolder holder, int position ) {
        Album album = albums.get( position );

        holder.itemBinding.title.setText( album.getTitle() );
        holder.itemBinding.index.setText( String.valueOf( album.getId() ) );
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        AlbumItemBinding itemBinding;

        ViewHolder( @NonNull AlbumItemBinding itemView ) {
            super( itemView.getRoot() );
            this.itemBinding = itemView;
        }
    }
}