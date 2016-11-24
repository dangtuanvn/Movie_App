package com.example.dangtuanvn.movie_app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.example.dangtuanvn.movie_app.MovieDetailActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Movie;


import java.util.List;

/**
 * Created by dangtuanvn on 11/22/16.
 */

public class MovieTabFragment extends Fragment {
    public enum CinemaTab {
        Showing,
        Upcoming
    }

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeLayout;

    private CinemaTab tab;
    private Handler handlerFDS = new Handler();

    public static MovieTabFragment newInstance(CinemaTab tab) {
        Bundle args = new Bundle();
        args.putSerializable("cinema_tab", tab);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab = (CinemaTab) getArguments().getSerializable("cinema_tab");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
            switch (tab) {
                case Showing:
                    final MovieFeedDataStore movieShowingFDS = new MovieFeedDataStore(getContext(),
                            MovieFeedDataStore.DataType.SHOWING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            displayMovieList(movieShowingFDS, false);
                        }
                    });
                    displayMovieList(movieShowingFDS, true);
                    break;

                case Upcoming:
                    final MovieFeedDataStore movieUpcomingFDS = new MovieFeedDataStore(getContext(), MovieFeedDataStore.DataType.UPCOMING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        public void onRefresh() {
                            displayMovieList(movieUpcomingFDS, false);
                        }
                    });
                    displayMovieList(movieUpcomingFDS, true);
                    break;
            }

        return view;
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.movie_tab_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        /* Use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView */
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void displayMovieList(final MovieFeedDataStore movieFDS, final boolean addTouch) {
//        Code to get handler of current Activity
//        Handler handler = getActivity().getWindow().getDecorView().getHandler();
//        (new Handler()).post(new Runnable() {
        swipeLayout.setRefreshing(true);
        handlerFDS.postDelayed(new Runnable() {
            @Override
            public void run() {
                movieFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List list, Exception ex) {
                        final List<Movie> movieList = (List<Movie>) list;
                        mAdapter = new MovieDetailAdapter(getContext(), movieList, tab.ordinal());
                        mRecyclerView.setAdapter((mAdapter));
                        if (addTouch) {
                            addOnMovieTouch(movieList);
                        }
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        }, 1000);
    }

    public void addOnMovieTouch(final List<Movie> movieList) {
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureDetector.onTouchEvent(e)) {

//                    MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
//                    Bundle args = new Bundle();
//                    args.putInt("movieId", movieList.get(rv.getChildAdapterPosition(childView)).getFilmId());
//                    args.putString("posterUrl", movieList.get(rv.getChildAdapterPosition(childView)).getPosterLandscape());
//
//
//                    movieDetailFragment.setArguments(args);
//
//
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    transaction.replace(R.id.viewpager, movieDetailFragment, "detail_fragment");
//                    transaction.addToBackStack(null);
//
//                    transaction.commit();

                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra("movieId",movieList.get(rv.getChildAdapterPosition(childView)).getFilmId());
                    intent.putExtra("posterUrl",movieList.get(rv.getChildAdapterPosition(childView)).getPosterLandscape());
                    startActivity(intent);

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    public void stopGetData() {
        handlerFDS.removeCallbacksAndMessages(null);
        swipeLayout.setRefreshing(false);
    }
}
