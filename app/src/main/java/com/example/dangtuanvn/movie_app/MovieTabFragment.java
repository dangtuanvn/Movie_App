package com.example.dangtuanvn.movie_app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.adapter.CinemaDetailAdapter;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;
import com.example.dangtuanvn.movie_app.adapter.NewsDetailAdapter;
import com.example.dangtuanvn.movie_app.datastore.CinemaFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieTabFragment extends Fragment {
    private enum Tab {
        Showing,
        Upcoming,
        Cinema,
        News
    }

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS =2;

    private int mPage;
    private Tab mTab;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private GoogleMap map;
    private Polyline polyline;
    private SupportMapFragment mapFragment;
    private SwipeRefreshLayout swipeLayout;
    private Handler handlerFDS = new Handler();
    private static int frameId = View.generateViewId();
    static List<Cinema> cinemaList;

    public static MovieTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt("frame_id", frameId);
        args.putSerializable("cinema_list", (Serializable) cinemaList);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameId = getArguments().getInt("frame_id");
        mPage = getArguments().getInt(ARG_PAGE);
        cinemaList = (List<Cinema>) getArguments().getSerializable("cinema_list");
        mTab = Tab.values()[mPage];
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        // Check for network connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (mTab) {
                case Showing:
                    view = inflateListView(inflater, container);
                    final MovieFeedDataStore movieShowingFDS = new MovieFeedDataStore(getContext(),
                            MovieFeedDataStore.DataType.SHOWING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            displayMovieList(movieShowingFDS,false);
                        }
                    });
                    displayMovieList(movieShowingFDS,true);
                    break;

                case Upcoming:
                    view = inflateListView(inflater, container);
                    final MovieFeedDataStore movieUpcomingFDS = new MovieFeedDataStore(getContext(), MovieFeedDataStore.DataType.UPCOMING);
                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        public void onRefresh() {
                            displayMovieList(movieUpcomingFDS,false);
                        }
                    });
                    displayMovieList(movieUpcomingFDS,true);
                    break;

                case Cinema:
                    view = inflateMapView(inflater, container);

                    final CinemaFeedDataStore cinemaFDS = new CinemaFeedDataStore(getContext());
                    displayLocationSettingsRequest(getContext(), cinemaFDS, inflater);
                  //  displayCinemaList(cinemaFDS, inflater);
                    break;

                case News:
                    view = inflateListView(inflater, container);
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

    public void displayMovieList(final MovieFeedDataStore movieFDS,final boolean addTouch) {
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
                        mAdapter = new MovieDetailAdapter(getContext(), movieList, mPage);
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

    public void displayNewsList(final NewsFeedDataStore newsFDS, final boolean addTouch) {
        swipeLayout.setRefreshing(true);
        handlerFDS.postDelayed(new Runnable() {
            @Override
            public void run() {
                newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List list, Exception ex) {
                        List<News> newsList = (List<News>) list;
                        mAdapter = new NewsDetailAdapter(getContext(), newsList, mPage);
                        mRecyclerView.setAdapter((mAdapter));
                        if (addTouch) {
                            addOnTouchNewsItem(mRecyclerView, newsList);
                        }
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        }, 1000);
    }

    public void addOnTouchNewsItem(RecyclerView mRecyclerView, final List<News> newsList) {
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
                final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    // Cancel getting data tasks
                    handlerFDS.removeCallbacksAndMessages(null);
                    swipeLayout.setRefreshing(false);
                    displayNewsDetail(rv, childView, newsList);
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

    public void addOnMovieTouch(final List<Movie> movieList){
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
                    // Cancel getting data tasks

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

    public void displayCinemaList(FeedDataStore cinemaFDS, final LayoutInflater inflater) {
        cinemaFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                FragmentManager fm = getChildFragmentManager();
                mapFragment = (SupportMapFragment) fm.findFragmentByTag("map_fragment");
                if (mapFragment == null) {
                    cinemaList = (List<Cinema>) list;
                    mapFragment = new SupportMapFragment();
                    fm.beginTransaction().add(frameId, mapFragment, "map_fragment").commit();
                    loadMapData(inflater, cinemaList);

                } else {
                    fm.beginTransaction().add(frameId, mapFragment, null).commit();
                    cinemaList = selectionSort(cinemaList);
                    MarkerOptions currentPosition = getCurrentPosition();

                    mAdapter = new CinemaDetailAdapter(getContext(), cinemaList);
                    mRecyclerView.setAdapter(mAdapter);
                    addOnTouchMapItem(mRecyclerView, cinemaList, currentPosition);
                }
            }
        });
    }

    public MarkerOptions getCurrentPosition() {
        Location location;
        LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return null;
            // TODO: check return of request
        } else {

             location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);}
        if(location==null){
            return null;
        }else {
            return new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    public void loadMapData(final LayoutInflater inflater, final List<Cinema> cinemaList) {
        if(getCurrentPosition()==null){
            Toast.makeText(getActivity(), "Please enable location setting",
                    Toast.LENGTH_LONG).show();
        }else {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.setInfoWindowAdapter(new MyInfoWindowAdapter(inflater));

                    MarkerOptions currentPosition = getCurrentPosition();

                    double latitude = currentPosition.getPosition().latitude;
                    double longitude = currentPosition.getPosition().longitude;

                for (int i = 0; i < cinemaList.size(); i++) {
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(cinemaList.get(i).getLatitude(), cinemaList.get(i).getLongitude()))
                            .title(cinemaList.get(i).getCinemaName())
                            .snippet(cinemaList.get(i).getCinemaAddress())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.cinema_around)));

                    Location loc1 = new Location("");
                    loc1.setLatitude(latitude);
                    loc1.setLongitude(longitude);

                    Location loc2 = new Location("");
                    loc2.setLatitude(cinemaList.get(i).getLatitude());
                    loc2.setLongitude(cinemaList.get(i).getLongitude());
                    cinemaList.get(i).setDistance(loc1.distanceTo(loc2) / 1000);
                }

                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location)));
                CameraUpdate center =
                        CameraUpdateFactory.newLatLng(marker.getPosition()
                        );
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);

                map.animateCamera(zoom);

                List<Cinema> sortedCinemaList = selectionSort(cinemaList);
//                for (int i = 0; i < sortedCinemaList.size(); i++) {
//                    Log.i("LIST DISTANCE", "" + sortedCinemaList.get(i).getDistance());
//                }

                mAdapter = new CinemaDetailAdapter(getContext(), sortedCinemaList);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mAdapter);
                addOnTouchMapItem(mRecyclerView, sortedCinemaList, currentPosition);
            }
        });
    }}

    public void addOnTouchMapItem(final RecyclerView mRecyclerView, final List<Cinema> cinemaList, final MarkerOptions currentPosition) {
        final GestureDetector mGesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGesture.onTouchEvent(e)) {
                    LatLng destination = new LatLng(
                            cinemaList.get(mRecyclerView.getChildAdapterPosition(childView)).getLatitude(),
                            cinemaList.get(mRecyclerView.getChildAdapterPosition(childView)).getLongitude());
                    String url = getDirectionsUrl(currentPosition.getPosition(), destination);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                    setMap(destination, currentPosition.getPosition());
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

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getPosition() == currentPosition.getPosition()){
                    marker.hideInfoWindow();
                    return false;
                } else{
                    String url = getDirectionsUrl(currentPosition.getPosition(), marker.getPosition());

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                    setMap(marker.getPosition(), currentPosition.getPosition());
                    return false;
            }}
        });
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

    public View inflateMapView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.google_map, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.map_fragment);
        frameLayout.setId(frameId);
        return view;
    }

    public void displayNewsDetail(RecyclerView rv, View childView, List<News> newsList) {
        FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(getContext(), newsList.get(rv.getChildAdapterPosition(childView)).getNewsId());
        newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                // Start web view
                Intent intent = new Intent(getContext(), WebViewDisplay.class);
                intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());
//                Log.i("RELATED NEWS", "" + ((NewsDetail) list.get(0)).getRelatedNewsList().get(0).toString());
                handlerFDS.removeCallbacksAndMessages(null);
                swipeLayout.setRefreshing(false);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        // Cancel all Volley requests that have not start yet
//        SingletonQueue.getInstance(getContext()).cancelAllRequests();

        // Cancel all tasks running on thread
        handlerFDS.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mTab == Tab.Cinema) {
            FragmentManager fm = getChildFragmentManager();
            Fragment fragment = fm.findFragmentByTag("map_fragment");
            fm.beginTransaction()
                    .remove(fragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    public void stopGetData() {
        handlerFDS.removeCallbacksAndMessages(null);
        swipeLayout.setRefreshing(false);
    }

    // Google Map functions

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * A class to download data from Google Directions URL
     */
    private class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Parser parserTask = new Parser();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    public class Parser extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionParser parser = new DirectionParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(20);
                lineOptions.color(Color.RED);
            }
            if (polyline != null) {
                polyline.remove();
            }
            // Drawing polyline in the Google Map for the i-th route
            polyline = map.addPolyline(lineOptions);
        }
    }

    public static List<Cinema> selectionSort(List<Cinema> data) {
        // just return if the array list is null
        if (data == null)
            return data;

        // just return if the array list is empty or only has a single element
        if (data.size() == 0 || data.size() == 1)
            return data;

        // declare an int variable to hold value of index at which the element
        // has the smallest value
        int smallestIndex;

        // declare an int variable to hold the smallest value for each iteration
        // of the outer loop
        float smallest;

        // for each index in the array list
        for (int curIndex = 0; curIndex < data.size(); curIndex++) {
            /* find the index at which the element has smallest value */
            // initialize variables
            smallest = data.get(curIndex).getDistance();
            smallestIndex = curIndex;

            for (int i = curIndex + 1; i < data.size(); i++) {
                if (smallest > data.get(i).getDistance()) {
                    // update smallest
                    smallest = data.get(i).getDistance();
                    smallestIndex = i;
                }
            }

			/* swap the value */
            // swap if the curIndex is not the smallest
            if (smallestIndex != curIndex) {
//                Log.i("SWAP", "SWAP");
                Cinema temp = data.get(curIndex);
                data.set(curIndex, data.get(smallestIndex));
                data.set(smallestIndex, temp);
            }
        }
        return data;
    }


    private void displayLocationSettingsRequest(final Context context, final FeedDataStore cinemaFDS, final LayoutInflater inflater) {

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        displayCinemaList(cinemaFDS, inflater);
                        Log.i("satisfy", "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i("update", "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            Log.i("pending", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i("not created", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    public void setMap(LatLng position, LatLng currentLocation) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(position);
        builder.include(currentLocation);

        LatLngBounds bounds = builder.build();
        int padding = 150
                ; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        map.moveCamera(cu);
        map.animateCamera(cu);
    }

    private class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private View myContentsView;

        private MyInfoWindowAdapter(LayoutInflater inflater) {
            myContentsView = inflater.inflate(R.layout.location_detail, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            myContentsView.setBackgroundColor(Color.BLACK);
            LinearLayout distanceLinear = (LinearLayout) myContentsView.findViewById(R.id.lineardistance);
            distanceLinear.setVisibility(View.GONE);
            ImageView cinemaIcon = (ImageView) myContentsView.findViewById(R.id.location_icon);
            cinemaIcon.getPaddingLeft();
            cinemaIcon.setImageResource(R.drawable.cinema_holder);
            TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.cinema_name));
            tvTitle.setText(marker.getTitle());
            TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.address));
            tvSnippet.setText(marker.getSnippet());
            return myContentsView;
        }
    }
}