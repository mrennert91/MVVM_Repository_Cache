package com.example.mvvmrepository.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mvvmrepository.R;
import com.example.mvvmrepository.databinding.PhotoItemBinding;
import com.example.mvvmrepository.models.Photo;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    public ArrayList<Photo> photos;

    public PhotosAdapter() {
        this.photos = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        return new ViewHolder( (PhotoItemBinding) DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.photo_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position ) {
        Photo photo = photos.get( position );

        holder.itemBinding.title.setText( photo.getTitle() );
        Uri uri = Uri.parse( photo.getUrl() );
        try {
            holder.itemBinding.thumbnail.setBackgroundColor( Color.parseColor( "#" + uri.getLastPathSegment() ) );
        } catch( Exception e ) {
            e.printStackTrace();
        }
        holder.itemBinding.index.setText( String.valueOf( photo.getId() ) );
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        PhotoItemBinding itemBinding;

        ViewHolder( @NonNull PhotoItemBinding itemView ) {
            super( itemView.getRoot() );
            this.itemBinding = itemView;
        }
    }
}
