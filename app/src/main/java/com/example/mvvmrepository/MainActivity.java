package com.example.mvvmrepository;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.example.mvvmrepository.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private final String SELECTED_TAB = "selected_tab";
    private int selectedTabPosition = 0;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );

        if( savedInstanceState != null ) {
            selectedTabPosition = savedInstanceState.getInt( SELECTED_TAB );
        }

        binding.tabs.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected( TabLayout.Tab tab ) {
                switch( tab.getPosition() ) {
                    case 0:
                        navigateTo( R.id.photosFragment );
                        break;
                    case 1:
                        navigateTo( R.id.albumsFragment );
                        break;
                    case 2:
                        navigateTo( R.id.todosFragment );
                        break;
                    case 3:
                        navigateTo( R.id.postsFragment );
                        break;
                }
            }

            @Override
            public void onTabUnselected( TabLayout.Tab tab ) {

            }

            @Override
            public void onTabReselected( TabLayout.Tab tab ) {

            }
        } );
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.tabs.selectTab( binding.tabs.getTabAt( selectedTabPosition ) );
    }

    private void navigateTo( int id ) {
        NavHostFragment.findNavController( getSupportFragmentManager().findFragmentById( R.id.parent_fragment ) ).navigate( id );
    }

    @Override
    public void onSaveInstanceState( @NonNull Bundle outState, @NonNull PersistableBundle outPersistentState ) {
        super.onSaveInstanceState( outState, outPersistentState );
        outState.putInt( SELECTED_TAB, binding.tabs.getSelectedTabPosition() );
    }

    public void setLoaderVisibility( boolean isVisible ) {
        if( binding != null )
            binding.loader.setVisibility( isVisible ? View.VISIBLE : View.GONE );
    }
}
