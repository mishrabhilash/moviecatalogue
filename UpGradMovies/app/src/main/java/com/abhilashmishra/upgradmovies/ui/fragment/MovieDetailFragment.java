package com.abhilashmishra.upgradmovies.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {


    @Bind(R.id.poster)
    ImageView poster;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.title_main_body)
    TextView titleMainBody;
    @Bind(R.id.text_synopsis)
    TextView textSynopsis;
    @Bind(R.id.scroll)
    NestedScrollView scroll;

    View rootView;
    AppCompatActivity parentActivity;
    Movie movie;

    public Gson gson;
    @Bind(R.id.user_rating)
    TextView userRating;
    @Bind(R.id.release_date)
    TextView releaseDate;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment getInstance(String movieJson) {
        Bundle bundle = new Bundle();
        bundle.putString("movie_json", movieJson);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        gson = new Gson();
        if (bundle != null) {
            String movieJson = bundle.getString("movie_json");
            if (movieJson != null) {
                movie = gson.fromJson(movieJson, Movie.class);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        initVars();
        initUi();

        return rootView;
    }

    private void initVars() {

    }

    private void initUi() {
        collapsingToolbar.setTitle(movie.getTitle());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        final String posterBaseUrl = parentActivity.getString(R.string.poster_base_large);
        Picasso.with(parentActivity)
                .load(posterBaseUrl + movie.getPosterPath())
                .into(poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) poster.getDrawable()).getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                applyPalette(palette);
                            }
                        });
                    }

                    @Override
                    public void onError() {
                        Log.d("ImageLoadFailed", posterBaseUrl + movie.getPosterPath());
                    }
                });

        titleMainBody.setText(movie.getTitle());
        textSynopsis.setText(movie.getOverview());
        userRating.setText(movie.getVoteAverage()+"");
        releaseDate.setText(movie.getReleaseDate());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity) context;
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.colorPrimaryDark);
        int primary = getResources().getColor(R.color.colorPrimary);
        collapsingToolbar.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbar.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
    }
}
