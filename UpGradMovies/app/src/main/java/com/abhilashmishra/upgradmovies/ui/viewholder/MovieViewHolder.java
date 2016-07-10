package com.abhilashmishra.upgradmovies.ui.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class MovieViewHolder extends ListDisplayableViewHolder {
    View rootView;
    ImageView moviePoster;
    TextView altText;
    WeakReference<Context> context;
    View.OnClickListener clickListener;
    public MovieViewHolder(View itemView, Context context, View.OnClickListener clickListener) {
        super(itemView);
        rootView = itemView;
        this.context = new WeakReference<Context>(context);
        this.clickListener = clickListener;
        moviePoster = (ImageView)itemView.findViewById(R.id.movie_poster_image_view);
        altText = (TextView)itemView.findViewById(R.id.alter_text);
    }

    @Override
    public void setUpView(ListDisplayable listDisplayable) {
        final Movie movie = (Movie) listDisplayable;
        final String posterBaseUrl = context.get().getString(R.string.poster_base);
        altText.setText(null);
        Picasso.with(context.get())
                .load(posterBaseUrl+movie.getPosterPath())
                .into(moviePoster, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Log.d("Error",posterBaseUrl+movie.getPosterPath());
                        altText.setText(movie.getTitle());
                    }
                });
        rootView.setTag(getAdapterPosition());
        rootView.setOnClickListener(clickListener);
    }
}
