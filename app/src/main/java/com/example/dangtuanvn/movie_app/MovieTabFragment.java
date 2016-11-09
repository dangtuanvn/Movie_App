package com.example.dangtuanvn.movie_app;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieTabFragment extends Fragment {

    private int mPage;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private MovieFeedDataStore movieFeedDataStore;
    private NewsFeedDataStore newsFeedDataStore;
    private SwipeRefreshLayout swipeLayout;

    public static MovieTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.movietabrecycler, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (mPage) {
                case 1:
                    movieFeedDataStore = new MovieFeedDataStore(getContext(), MovieFeedDataStore.DataType.SHOWING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            setList(mPage);
                        }
                    });
                    setList(mPage);
                    break;

                case 2:
                case 3:
                    movieFeedDataStore = new MovieFeedDataStore(getContext(), MovieFeedDataStore.DataType.UPCOMING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        public void onRefresh() {
                            setList(mPage);
                        }
                    });
                    setList(mPage);
                    break;

                case 4:
                    newsFeedDataStore = new NewsFeedDataStore(getContext());
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            setList(mPage);
                        }
                    });
                    setList(mPage);
                    break;
                default:

            }
        } else {
            // NO NETWORK CONNECTION
        }
        return view;
    }

    private enum TAB {
        SHOWING,
        UPCOMING,
        AROUND,
        NEWS
    }

    public void setList(final int mPage) {
        swipeLayout.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (mPage) {
                    case 1:
                        movieFeedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                            @Override
                            public void onDataRetrievedListener(List list, Exception ex) {
                                List<Movie> movieShowingList = (List<Movie>) list;
                                mAdapter = new MovieDetailAdapter(getContext(), movieShowingList, mPage);
                                mRecyclerView.setAdapter((mAdapter));
                            }
                        });
                        break;

                    case 2:
                    case 3:
                        movieFeedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                            @Override
                            public void onDataRetrievedListener(List list, Exception ex) {
                                List<Movie> movieShowingList = (List<Movie>) list;
                                mAdapter = new MovieDetailAdapter(getContext(), movieShowingList, mPage);
                                mRecyclerView.setAdapter((mAdapter));
                            }
                        });
                        break;

                    case 4:
                        newsFeedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                            @Override
                            public void onDataRetrievedListener(List list, Exception ex) {
                                final List<News> newsShowingList = (List<News>) list;
                                mAdapter = new NewsDetailAdapter(getContext(), newsShowingList, mPage);
                                mRecyclerView.setAdapter((mAdapter));
                                final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                    @Override
                                    public boolean onSingleTapUp(MotionEvent e) {

                                        return true;
                                    }
                                });
                                mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                    @Override
                                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                        View childview = rv.findChildViewUnder(e.getX(), e.getY());
                                        if (childview != null && mGestureDetector.onTouchEvent(e))  {
                                            Intent intent = new Intent(getContext(), WebViewDisplay.class);
                                            intent.putExtra("link", newsShowingList.get(rv.getChildAdapterPosition(childview)).getUrl());
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
                        });
                        break;
                }
                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }
}

