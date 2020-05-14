package com.example.mvvmrepository.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.mvvmrepository.R;
import com.example.mvvmrepository.databinding.PostItemBinding;
import com.example.corelib.models.Post;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public ArrayList<Post> posts;

    public PostsAdapter() {
        this.posts = new ArrayList<>();
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        return new PostsAdapter.ViewHolder( (PostItemBinding) DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.post_item, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull PostsAdapter.ViewHolder holder, int position ) {
        Post post = posts.get( position );

        holder.itemBinding.title.setText( post.getTitle() );
        holder.itemBinding.index.setText( String.valueOf( post.getId() ) );
        holder.itemBinding.body.setText( String.valueOf( post.getBody() ) );
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        PostItemBinding itemBinding;

        ViewHolder( @NonNull PostItemBinding itemView ) {
            super( itemView.getRoot() );
            this.itemBinding = itemView;
        }
    }
}