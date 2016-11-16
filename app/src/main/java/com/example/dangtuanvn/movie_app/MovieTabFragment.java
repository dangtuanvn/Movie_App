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
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.adapter.AroundDetailAdapter;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final int REQUEST_CHECK_SETTINGS =2;

    private int mPage;
//    private RecyclerView.LayoutManager mLayoutManager;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView recyclerview;
    double latitude;
    double longitude;
    Polyline polyline;
    GoogleMap map;
    SupportMapFragment mapFragment;


    private RecyclerView.Adapter nAdapter;
    private SwipeRefreshLayout swipeLayout;
    Handler handlerFDS = new Handler();
    private static int frameId = View.generateViewId();

    public static MovieTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putInt("frame_id", frameId);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameId = getArguments().getInt("frame_id");
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        // Check for network connection
        class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

            private final View myContentsView;

            MyInfoWindowAdapter(){
                myContentsView = inflater.inflate(R.layout.info_window_layout, null);
            }

            @Override
            public View getInfoContents(Marker marker) {



               return null;
            }

            @Override
            public View getInfoWindow(Marker marker) {
                myContentsView.setBackgroundColor(Color.BLACK);
                ImageView cinemaicon = (ImageView) myContentsView.findViewById(R.id.locationicon);
                cinemaicon.getPaddingLeft();
                cinemaicon.setImageResource(R.drawable.cinema_holder);
                TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.cinema_name));
                tvTitle.setText(marker.getTitle());
                TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.address));
                tvSnippet.setText(marker.getSnippet());
                return myContentsView;
            }

        }
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            switch (TAB.values()[mPage]) {
                case SHOWING:
                    view = inflateListView(inflater, container);
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
                    view = inflateListView(inflater, container);
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
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            displayLocationSettingsRequest(getContext());
                        }});
                    t.start();
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    view = inflateMapView(inflater, container);

                    mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view2);

                    final CinemaFeedDataStore localcinema = new CinemaFeedDataStore(getContext());
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.map_fragment);
                    frameLayout.setId(frameId);

                    localcinema.getList(new FeedDataStore.OnDataRetrievedListener() {
                        @Override
                        public void onDataRetrievedListener(List<?> list, Exception ex) {
                            final List<Cinema> cinemalist = (List<Cinema>) list;



                            FragmentManager fm = getChildFragmentManager();
                           mapFragment = (SupportMapFragment) fm.findFragmentByTag("map_fragment");
                            final List<Float> distance = new ArrayList<Float>();
                            if(mapFragment == null) {
                                mapFragment = new SupportMapFragment();
                                final MarkerOptions[] curentposition = new MarkerOptions[1];

                                fm.beginTransaction().add(frameId, mapFragment, "map_fragment").commit();

//                            SupportMapFragment mapFragment =
//                                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);

                                mapFragment.getMapAsync(new OnMapReadyCallback() {

                                    @Override
                                    public void onMapReady(GoogleMap googlemap) {
                                        map = googlemap;
                                        map.setInfoWindowAdapter(new MyInfoWindowAdapter());
//                                        List<Float> distance = new ArrayList<Float>();
                                        LocationManager locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                                            return;
                                        }else {
                                            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                           latitude  = location.getLatitude();
                                           longitude = location.getLongitude();
                                            curentposition[0] = new MarkerOptions()
                                                    .position(new LatLng(latitude,longitude));

                                        }
                                        for (int i = 0; i < cinemalist.size(); i++) {
                                            map.addMarker(new MarkerOptions()
                                                    .position(new LatLng(cinemalist.get(i).getLatitude(), cinemalist.get(i).getLongtitude()))
                                                    .title(cinemalist.get(i).getCinemaName())
                                                    .snippet(cinemalist.get(i).getCinemaAddress())
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cinema_around)));

                                            Location loc1 = new Location("");
                                            loc1.setLatitude(latitude);
                                            loc1.setLongitude(longitude);

                                            Location loc2 = new Location("");
                                            loc2.setLatitude(cinemalist.get(i).getLatitude());
                                            loc2.setLongitude(cinemalist.get(i).getLongtitude());
                                            distance.add(loc1.distanceTo(loc2)/1000);
                                            cinemalist.get(i).setDistance(loc1.distanceTo(loc2)/1000);
                                        }


                                        Marker marker = map.addMarker(new MarkerOptions()
                                                .position(new LatLng(latitude, longitude))
                                                .title("here i am")
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location)));
                                        CameraUpdate center =
                                                CameraUpdateFactory.newLatLng(marker.getPosition()
                                                );
                                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

                                        map.moveCamera(center);
                                        map.animateCamera(zoom);


                                        final List<Cinema> cinemaList = selectionSort(cinemalist);

                                        for(int i = 0; i < cinemaList.size(); i ++){
                                            Log.i("LIST DISTANCE", "" + cinemaList.get(i).getDistance());
                                        }
                                        mAdapter = new AroundDetailAdapter(getContext(),cinemaList,mPage);
                                        mRecyclerView .setLayoutManager(new LinearLayoutManager(getActivity()));
                                        mRecyclerView.setAdapter(mAdapter);
                                        final GestureDetector mGesture = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                                            @Override
                                            public boolean onSingleTapUp(MotionEvent e) {
                                                return true;
                                            }
                                        });
                                        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                            @Override
                                            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                                                View childview = rv.findChildViewUnder(e.getX(), e.getY());


                                                if (childview != null && mGesture.onTouchEvent(e))  {
                                                    LatLng destiny = new LatLng(cinemaList.get(mRecyclerView.getChildAdapterPosition(childview)).getLatitude(),cinemaList.get(mRecyclerView.getChildAdapterPosition(childview)).getLongtitude());
                                                    String url = getDirectionsUrl(curentposition[0].getPosition(), destiny);

                                                    DownloadTask downloadTask = new DownloadTask();

                                                    // Start downloading json data from Google Directions API
                                                    downloadTask.execute(url);
                                                    setmap(destiny);

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
                                               String url = getDirectionsUrl(curentposition[0].getPosition(), marker.getPosition());

                                               DownloadTask downloadTask = new DownloadTask();

                                               // Start downloading json data from Google Directions API
                                               downloadTask.execute(url);
                                               setmap(marker.getPosition());
                                               return false;
                                           }
                                       });

                                    }


                                });
                            }
                            else{
                                fm.beginTransaction().add(frameId, mapFragment, null).commit();
                                mAdapter = new AroundDetailAdapter(getContext(),cinemalist,mPage);
                                mRecyclerView .setLayoutManager(new LinearLayoutManager(getActivity()));
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        }

                    });
                    break;

                case NEWS:
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

    public void displayMovieList(final MovieFeedDataStore movieFDS) {
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
                        List<Movie> movieList = (List<Movie>) list;
                        mAdapter = new MovieDetailAdapter(getContext(), movieList, mPage);
                        mRecyclerView.setAdapter((mAdapter));
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
            public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
                final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    // Cancel the current refreshing data
                    handlerFDS.removeCallbacksAndMessages(null);
                    swipeLayout.setRefreshing(false);


                    FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(getContext(), newsShowingList.get(rv.getChildAdapterPosition(childView)).getNewsId());
                    newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                        @Override
                        public void onDataRetrievedListener(List<?> list, Exception ex) {
                            // Start web view
                            Intent intent = new Intent(getContext(), WebViewDisplay.class);
                            intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());
                            handlerFDS.removeCallbacksAndMessages(null);
                            swipeLayout.setRefreshing(false);
                            startActivity(intent);
                        }
                    });

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

        // Cancel all Volley requests that have not start yet
//        SingletonQueue.getInstance(getContext()).cancelAllRequests();

        // Cancel all tasks running on thread
        handlerFDS.removeCallbacksAndMessages(null);}

    public void stopGetData(){
        handlerFDS.removeCallbacksAndMessages(null);
        swipeLayout.setRefreshing(false);
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.movie_tab_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        /* Use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView */
        mRecyclerView.setHasFixedSize(true);

//        mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public View inflateMapView(LayoutInflater inflater, ViewGroup container){
       View view = inflater.inflate(R.layout.googlemap, container, false);
       return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if(mPage == 2) { // CINEMA TAB
//            FragmentManager fm = getChildFragmentManager();
//            Fragment fragment = fm.findFragmentById(R.id.map_fragment);
//            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
//        }


    }
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }



    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
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

    public class Parser extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {


        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionParser parser = new DirectionParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
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
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

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
            if(polyline!=null){
                polyline.remove();
            }
         polyline=   map.addPolyline(lineOptions);



            // Drawing polyline in the Google Map for the i-th route

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
            // do nothing if the curIndex has the smallest value
            if (smallestIndex == curIndex)
                ;
                // swap values otherwise
            else {
                Log.i("SWAP", "SWAP");
                Cinema temp = data.get(curIndex);
                data.set(curIndex, data.get(smallestIndex));
                data.set(smallestIndex, temp);
            }
        }
//        for(int i = 0; i < data.size(); i ++){
//            Log.i("LIST DISTANCE", "" + data.get(i).getDistance());
//        }
        return data;
    }
    public void setmap(LatLng position){
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(position
                );
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        map.moveCamera(center);
        map.animateCamera(zoom);
    }
    private void displayLocationSettingsRequest(Context context) {

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




}





