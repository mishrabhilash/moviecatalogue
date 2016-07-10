package com.abhilashmishra.upgradmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.ui.fragment.MovieListFragment;
import com.google.gson.Gson;

public class MoviesActivity extends AppCompatActivity implements MovieListFragment.OnMovieFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        loadFragment();
    }

    private void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(MovieListFragment.class.getName());
        if(fragment==null){
            fragment = new MovieListFragment();
        }
        ft.replace(R.id.placeholder,fragment,MovieListFragment.class.getName()).commit();
    }

    @Override
    public void onMovieClicked(ListDisplayable movie) {
        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra("movie_json",(new Gson()).toJson(movie));
        startActivity(intent);
    }
}
