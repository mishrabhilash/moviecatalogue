package com.abhilashmishra.upgradmovies.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhilashmishra.upgradmovies.R;
import com.abhilashmishra.upgradmovies.controller.MovieListFragmentController;
import com.abhilashmishra.upgradmovies.model.ListDisplayable;
import com.abhilashmishra.upgradmovies.ui.adapter.MovieListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.filter_text)
    TextView filterText;
    @Bind(R.id.order)
    ImageView order;
    @Bind(R.id.list_displayble_recyclerview)
    RecyclerView listDisplaybleRecyclerview;

    private OnMovieFragmentInteractionListener mListener;
    private View rootView;
    private MovieListFragmentController fragmentController;
    private Activity parentActivity;

    private MovieListAdapter adapter;
    private List<ListDisplayable> movieArrayList;
    private GridLayoutManager gridLayoutManager;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void initVars() {
        fragmentController = MovieListFragmentController.getInstance(this, parentActivity);
        movieArrayList = new ArrayList<>();
        movieArrayList.addAll(fragmentController.getMovieList());
        adapter = new MovieListAdapter(movieArrayList,parentActivity,this);
        gridLayoutManager = new GridLayoutManager(parentActivity,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span=2;
                switch (adapter.getItemViewType(position)){
                    case MovieListAdapter.TYPE_MOVIE:span= 1;break;
                    case MovieListAdapter.TYPE_LOADER:span=2;break;
                }
                return span;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        initVars();
        initUi();
        return rootView;
    }

    private void initUi() {
        filterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentController.filterClicked();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentController.orderbyClicked();
            }
        });

        fragmentController.initialiseUI();
        listDisplaybleRecyclerview.setAdapter(adapter);
        listDisplaybleRecyclerview.setLayoutManager(gridLayoutManager);
        listDisplaybleRecyclerview.addOnScrollListener(scrollListener);
    }

    public void upadteFilterText(int textId) {
        filterText.setText(textId);
    }

    public void updateOrderBy(int drawable) {
        order.setImageDrawable(parentActivity.getResources().getDrawable(drawable));
    }

    public void showOptions(){
        PopupMenu popupMenu = new PopupMenu(getContext(), filterText);
        popupMenu.getMenuInflater().inflate(R.menu.filter_options, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popularity:
                        fragmentController.filterSelected(1);
                        break;
                    case R.id.votes:
                        fragmentController.filterSelected(2);
                        break;
                }
                return true;
            }
        });
    }

    public void updateMovieList(){
        movieArrayList.clear();
        movieArrayList.addAll(fragmentController.getMovieList());
        adapter.notifyDataSetChanged();
    }

    public void disableControls(){
        filterText.setClickable(false);
        order.setClickable(false);
    }

    public void enableControls(){
        filterText.setClickable(true);
        order.setClickable(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnMovieFragmentInteractionListener) {
            mListener = (OnMovieFragmentInteractionListener) activity;
            parentActivity = activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnMovieFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        mListener.onMovieClicked(movieArrayList.get((int) view.getTag()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnMovieFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMovieClicked(ListDisplayable movie);
    }

    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) //check for scroll down
            {
                visibleItemCount = gridLayoutManager.getChildCount();
                totalItemCount = gridLayoutManager.getItemCount();
                pastVisiblesItems = gridLayoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    fragmentController.listEndReached();
                }
            }
        }
    };

}
