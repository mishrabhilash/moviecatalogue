package com.abhilashmishra.upgradmovies.controller;

import android.content.Context;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.model.MovieDiscoverResponse;
import com.abhilashmishra.upgradmovies.model.MovieListLoader;
import com.abhilashmishra.upgradmovies.network.NetworkCall;
import com.abhilashmishra.upgradmovies.ui.fragment.MovieListFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class MovieListFragmentController {

    public static final String FILTER_POPULARITY = "popularity.";
    public static final String FILTER_VOTED = "vote_count.";
    public static final String ASC = "asc";
    public static final String DESC = "desc";


    int currentPageCount=0;
    int totalPageCount=1;
    String filter;
    String order;
    boolean loading;

    MovieListFragment fragment;
    List<ListDisplayable> movieList;
    WeakReference<Context> context;


    private static MovieListFragmentController instance;

    public static MovieListFragmentController getInstance(MovieListFragment movieListFragment,Context context){
        if(instance==null){
            instance = new MovieListFragmentController();
            instance.movieList = new ArrayList<>();
            instance.filter=FILTER_POPULARITY;
            instance.order=DESC;
            instance.fragment = movieListFragment;
        }
        instance.context = new WeakReference<Context>(context);
        return instance;
    }

    private void disableControls() {
        fragment.disableControls();
    }

    private void enableControls() {
        fragment.enableControls();
    }

    private void noInternetAvailable(){
        fragment.noInternetAvailable();
    }

    private void InternetAvailable(){

    }

    private void setFilterText(){
        if(filter.equals(FILTER_POPULARITY)){
            fragment.upadteFilterText(R.string.filter_text_popularity);
        }else if(filter.equals(FILTER_VOTED)){
            fragment.upadteFilterText(R.string.filter_text_votes);
        }
    }

    private void setOrderByImage() {
        if(order.equals(ASC)){
            fragment.updateOrderBy(android.R.drawable.arrow_up_float);
        }else if(order.equals(DESC)){
            fragment.updateOrderBy(android.R.drawable.arrow_down_float);
        }
    }

    public void refreshMovieList(){
        getMoviesFromDB(1);
    }

    private void getMoviesFromDB(int page){
        if(page==1){
            movieList.clear();
            updateMovieList();
        }
        if(currentPageCount<=totalPageCount) {
            loading = true;
            showLoader();
            disableControls();
            Map<String, Object> map = new HashMap<>();
            map.put("api_key", context.get().getString(R.string.api_key));
            map.put("sort_by", filter + order);
            map.put("page", page);
            NetworkCall.getInstance().getMovieApi().getMovies(map).enqueue(new Callback<MovieDiscoverResponse>() {
                @Override
                public void onResponse(Call<MovieDiscoverResponse> call, Response<MovieDiscoverResponse> response) {
                    loading = false;
                    hideLoader();
                    enableControls();
                    currentPageCount = response.body().getPage();
                    totalPageCount = response.body().getTotalPages();
                    if (currentPageCount > 1) {
                        movieList.addAll(response.body().getResults());
                    } else {
                        movieList.clear();
                        movieList.addAll(response.body().getResults());
                    }
                    updateMovieList();
                }

                @Override
                public void onFailure(Call<MovieDiscoverResponse> call, Throwable t) {
                    loading = false;
                    hideLoader();
                    enableControls();
                    noInternetAvailable();
                }
            });
        }
    }

    private void showLoader(){
        movieList.add(new MovieListLoader());
        updateMovieList();
    }

    private void hideLoader(){
        movieList.remove(movieList.size()-1);
        updateMovieList();
    }

    private void updateMovieList() {
        fragment.updateMovieList();
    }

    public void initialiseUI(){
        setFilterText();
        setOrderByImage();
        if(movieList.size()<1) {
            getMoviesFromDB(1);
        }
    }

    public List<ListDisplayable> getMovieList() {
        return movieList;
    }

    public void filterClicked(){
        fragment.showOptions();
    }

    public void filterSelected(int fil){
        String temp="";
        switch (fil){
            case 1:temp = FILTER_POPULARITY;
                break;
            case 2:temp = FILTER_VOTED;
                break;
        }
        if(!temp.equals(filter)) {
            filter=temp;
            refreshMovieList();
            setFilterText();
        }
    }

    public void orderbyClicked(){
        switch (order){
            case ASC:order = DESC;
                break;
            case DESC:order = ASC;
                break;
        }
        refreshMovieList();
        setOrderByImage();
    }

    public void orderSelected(int ord){

    }

    public void listEndReached(){
        if(!loading) {
            getMoviesFromDB(currentPageCount + 1);
        }
    }


}
