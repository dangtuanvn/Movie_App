package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MovieFeedDataStore feedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            feedDataStore = new MovieFeedDataStore(this);    // fetch data
            feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List list, Exception ex) {
                    // Toast.makeText(getApplicationContext(),"Size: " + postList.size(),Toast.LENGTH_SHORT).show(); // size 26
                    // displayRecyclerExpandableList(postList);
//                    displayRecyclerList(postList);
//                    position = postList.size() - 2;
                    List<Movie> abc = (List<Movie>) list;
                    Log.i("FILM NAME" , "" + abc.get(0).getFilmName());
                    Toast.makeText(getApplicationContext(),"" + abc.get(0).getFilmName(),Toast.LENGTH_SHORT).show(); // size 26
                }
            });
        } else {

        }
    }
}
