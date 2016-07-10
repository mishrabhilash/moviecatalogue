package com.abhilashmishra.upgradmovies.network;

import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.model.MovieDiscoverResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public interface MovieApiInterface {
    @GET("/3/discover/movie")
    Call<MovieDiscoverResponse> getMovies(@QueryMap Map<String, Object> params);

    @GET("/3/discover/movie")
    Call<ListDisplayable> getMovies1(@QueryMap Map<String, Object> params);
}
