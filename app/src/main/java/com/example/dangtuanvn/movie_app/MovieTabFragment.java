package com.example.dangtuanvn.movie_app;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.adapter.AroundDetailAdapter;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;
import com.example.dangtuanvn.movie_app.adapter.NewsDetailAdapter;
import com.example.dangtuanvn.movie_app.datastore.CinemaFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;


/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieTabFragment extends Fragment {
    private enum TAB {
        SHOWING,
        UPCOMING,
        CINEMA,
        NEWS
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private int mPage;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerview;
    private RecyclerView.Adapter nAdapter;
    private SwipeRefreshLayout swipeLayout;
    Handler handlerFDS = new Handler();

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
        View view = inflater.inflate(R.layout.movietabrecycler, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (TAB.values()[mPage]) {
                case SHOWING:

                    final MovieFeedDataStore movieShowingFDS = new MovieFeedDataStore(getContext(),
                            MovieFeedDataStore.DataType.SHOWING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            displayMovieList(movieShowingFDS);
                        }
                    });
                    displayMovieList(movieShowingFDS);
                    break;

                case UPCOMING:

                    final MovieFeedDataStore movieUpcomingFDS = new MovieFeedDataStore(getContext(),
                            MovieFeedDataStore.DataType.UPCOMING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        public void onRefresh() {
                            displayMovieList(movieUpcomingFDS);
                        }
                    });
                    displayMovieList(movieUpcomingFDS);
                    break;

                case CINEMA:
                    view = inflater.inflate(R.layout.googlemap, container, false);
                    final CinemaFeedDataStore localcinema = new CinemaFeedDataStore(getContext());
                    recyclerview = (RecyclerView) view.findViewById(R.id.map_recycler);
                    localcinema.getList(new FeedDataStore.OnDataRetrievedListener() {
                        @Override
                        public void onDataRetrievedListener(List<?> list, Exception ex) {
                            final List<Cinema> cinemadetail = (List<Cinema>) list;
                            nAdapter = new AroundDetailAdapter(getContext(), cinemadetail, mPage);
                            recyclerview.setAdapter((nAdapter));

                            SupportMapFragment mapFragment =
                                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                                        @Override
                                                        public void onMapReady(GoogleMap map) {
                                                            for (int i = 0; i < cinemadetail.size(); i++) {
                                                             map.addMarker(new MarkerOptions()
                                                                        .position(new LatLng(cinemadetail.get(i).getLatitude(), cinemadetail.get(i).getLongtitude()))
                                                                        .title(cinemadetail.get(i).getCinemaName())
                                                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_around_highlight)));


                                                            }


                                                            LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
                                                            Criteria criteria = new Criteria();
                                                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                                                return;
                                                            }else {
                                                                Location location = locationManager.getLastKnownLocation(locationManager
                                                                        .getBestProvider(criteria, false));
                                                                double latitude = location.getLatitude();
                                                                double longitude = location.getLongitude();
                                                                Marker marker = map.addMarker(new MarkerOptions()
                                                                        .position(new LatLng(latitude, longitude))
                                                                        .title("here i am"));
                                                                CameraUpdate center =
                                                                        CameraUpdateFactory.newLatLng(marker.getPosition()
                                                                        );
                                                                CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

                                                                map.moveCamera(center);
                                                                map.animateCamera(zoom);
                                                            }
                                                        }
                            }
                            );

                        }

                    });

                    break;

                case NEWS:
                    final NewsFeedDataStore newsFDS = new NewsFeedDataStore(getContext());
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            displayNewsList(newsFDS, false);
                        }
                    });
                    displayNewsList(newsFDS, true);
                    break;

                default:
                    // YOU SHOULD NOT SEE THIS TAB
            }
        } else {
            // NO NETWORK CONNECTION
        }
        return view;
    }

    public void displayMovieList(final MovieFeedDataStore movieShowingFDS) {
        swipeLayout.setRefreshing(true);
        handlerFDS.postDelayed(new Runnable() {
            @Override
            public void run() {
                movieShowingFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List list, Exception ex) {
                        List<Movie> movieShowingList = (List<Movie>) list;
                        mAdapter = new MovieDetailAdapter(getContext(), movieShowingList, mPage);
                        mRecyclerView.setAdapter((mAdapter));
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        }, 1000);
    }

    public void displayNewsList(final NewsFeedDataStore newsFDS, final boolean addTouch) {
        swipeLayout.setRefreshing(true);
//        Code to get handler of current Activity
//        Handler handler = getActivity().getWindow().getDecorView().getHandler();
//        (new Handler()).post(new Runnable() {
        handlerFDS.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List list, Exception ex) {
                        final List<News> newsShowingList = (List<News>) list;
                        mAdapter = new NewsDetailAdapter(getContext(), newsShowingList, mPage);
                        mRecyclerView.setAdapter((mAdapter));
                        if (addTouch) {
                            addOnTouchNewsItem(mRecyclerView, newsShowingList);
                        }
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        }, 1000);
    }



    public void addOnTouchNewsItem(RecyclerView mRecyclerView, final List<News> newsShowingList) {
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    stopGetData();
                    Intent intent = new Intent(getContext(), WebViewDisplay.class);
                    intent.putExtra("link", newsShowingList.get(rv.getChildAdapterPosition(childView)).getUrl());
                    handlerFDS.removeCallbacksAndMessages(null);
                    swipeLayout.setRefreshing(false);
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
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        handlerFDS.removeCallbacksAndMessages(null);
    }

    public void stopGetData() {
        handlerFDS.removeCallbacksAndMessages(null);
        swipeLayout.setRefreshing(false);
    }


    }






