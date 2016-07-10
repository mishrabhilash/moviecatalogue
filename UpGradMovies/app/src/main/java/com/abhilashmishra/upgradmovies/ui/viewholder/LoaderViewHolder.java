package com.abhilashmishra.upgradmovies.ui.viewholder;

import android.view.View;
import android.widget.ProgressBar;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;

/**
 * Created by mishrabhilash on 7/10/16.
 */
public class LoaderViewHolder extends ListDisplayableViewHolder {
    ProgressBar progressBar;
    public LoaderViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
    }

    @Override
    public void setUpView(ListDisplayable listDisplayable) {

    }
}
