package com.abhilashmishra.upgradmovies.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abhilashmishra.upgradmovies.model.ListDisplayable;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public abstract class ListDisplayableViewHolder extends RecyclerView.ViewHolder {


    public ListDisplayableViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setUpView(ListDisplayable listDisplayable);
}
