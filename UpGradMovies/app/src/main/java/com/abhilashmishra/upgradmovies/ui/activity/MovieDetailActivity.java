package com.abhilashmishra.upgradmovies.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.ui.fragment.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    String movieJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if(savedInstanceState!=null){
            movieJson = savedInstanceState.getString("movie_json");
        }
        initVars();
        loadFragment();
    }

    private void initVars() {
        if(movieJson==null) {
            movieJson = getIntent().getStringExtra("movie_json");
        }
    }

    private void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(MovieDetailFragment.class.getName());
        if(fragment==null){
            fragment = MovieDetailFragment.getInstance(movieJson);
        }
        ft.replace(R.id.placeholder,fragment,MovieDetailFragment.class.getName()).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("movie_json",movieJson);
    }
}
