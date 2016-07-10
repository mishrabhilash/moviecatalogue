package com.abhilashmishra.upgradmovies.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.ui.viewholder.ListDisplayableViewHolder;
import com.abhilashmishra.upgradmovies.ui.viewholder.LoaderViewHolder;
import com.abhilashmishra.upgradmovies.ui.viewholder.MovieViewHolder;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<ListDisplayableViewHolder> {
    public static final int TYPE_MOVIE=1;
    public static final int TYPE_LOADER=2;
    List<ListDisplayable> listDisplayables;
    WeakReference<Context> context;
    View.OnClickListener clickListener;
    public MovieListAdapter(List<ListDisplayable> listDisplayables, Context context, View.OnClickListener clickListener){
        this.listDisplayables = listDisplayables;
        this.context = new WeakReference<Context>(context);
        this.clickListener = clickListener;
    }
    @Override
    public ListDisplayableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ListDisplayableViewHolder vh=null;
        switch (viewType){
            case 1:v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie_movielist,parent,false);
                vh = new MovieViewHolder(v,context.get(),clickListener);
                break;
            case 2:v= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loader_movielist,parent,false);
                vh = new LoaderViewHolder(v);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ListDisplayableViewHolder holder, int position) {
        holder.setUpView(listDisplayables.get(position));
    }

    @Override
    public int getItemCount() {
        return listDisplayables.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (listDisplayables.get(position).getType()){
            case ListDisplayable.MOVIE:
                return TYPE_MOVIE;
            case ListDisplayable.LOADER:
                return TYPE_LOADER;
        }
        return TYPE_MOVIE;
    }
}
