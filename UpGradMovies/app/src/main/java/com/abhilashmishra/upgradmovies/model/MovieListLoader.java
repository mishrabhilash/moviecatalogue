package com.abhilashmishra.upgradmovies.model;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class MovieListLoader extends ListDisplayable {
    String type= ListDisplayable.LOADER;

    @Override
    public String getType() {
        return type;
    }
}
