package com.abhilashmishra.upgradmovies.network;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class NetworkCall {
    private static final String movieApiBaseUrl = "http://api.themoviedb.org/";
    private static NetworkCall instance;

    public static NetworkCall getInstance() {
        if (instance == null) {
            instance = new NetworkCall();
        }
        return instance;
    }
    private NetworkCall() {

    }

    public MovieApiInterface getMovieApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(movieApiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit.create(MovieApiInterface.class);
    }

}

