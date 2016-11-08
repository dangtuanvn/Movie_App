package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.converter.CinemaDeserializer;
import com.example.dangtuanvn.movie_app.model.converter.MovieDeserializer;
import com.example.dangtuanvn.movie_app.model.converter.NewsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class MovieFeedDataStore implements FeedDataStore {
    public enum DataType {
        SHOWING,
        UPCOMING,
        CINEMA,
        NEWS,
        MOVIE_DETAIL,
        MOVIE_TRAILER,
        NEWS_DETAIL
    }

    private static String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private static String X123F_VERSION = "3";
    private static String BASEURL = "http://mapp.123phim.vn/android/2.97/";

    private Context context;
    private DataType dataType;
    private String itemId;

    public MovieFeedDataStore(Context context) {
        this.context = context;
        this.dataType = null;
        this.itemId = null;
    }

    @Override
    public void getList(final OnDataRetrievedListener onDataRetrievedListener) {
//        final RequestQueue queue = Volley.newRequestQueue(context);
        String url = setUrl();

        Log.i("GET URL", url);

        final StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "Response is: "+ response);

                        onDataRetrievedListener.onDataRetrievedListener(handleData(response), null);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VOLLEY RESPONSE FAIL", "Volley gets fail");
//                        Intent retryIntent = new Intent(context, RetryConnectionActivity.class);
//                        retryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        context.startActivity(retryIntent);
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();


                long timestamp = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
//                Log.i("CURRENT UTC", "" + timestamp);
                String accessToken = hashMd5(X123F_TOKEN + timestamp) + " " + timestamp;

                params.put("X-123F-Version", X123F_VERSION);
                params.put("X-123F-Token", accessToken);

                return params;
            }
        };

        // Add the request to the RequestQueue.
        SingletonDataStore.getInstance(context).addRequest(stringRequest);
//        queue.add(stringRequest);
    }

    // http://stackoverflow.com/questions/4846484/md5-hashing-in-android
    private String hashMd5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public void updateList(List<Movie> newList){
//        if(list == null){
//            list = new ArrayList<>();
//        }
//        list.addAll(newList);
//    }

    public void setDataType(DataType type){
        this.dataType = type;
    }

    public void setItemId(String itemId){
        this.itemId = itemId;
    }

    public String setUrl(){
        String url = null;
        switch(dataType) {
            case SHOWING:
                url = BASEURL + "film/list?status=1";
                break;

            case UPCOMING:
                url = BASEURL + "film/list?status=2";
                break;

            case CINEMA:
                url = BASEURL + "cinema/list";
                break;

            case NEWS:
                url = BASEURL + "news/list?type_id=1";
                break;
        }
        return url;
    }

    private List<?> handleData(String response){
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject)jsonParser.parse(response);
        Type type;
        GsonBuilder gsonBuilder;
        Gson gson;

        switch(dataType){
            case SHOWING:
            case UPCOMING:
                List<Movie> movieList;
                type = new TypeToken<List<Movie>>(){}.getType();
                gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
                gson = gsonBuilder.create();
                movieList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
                return movieList;

            case CINEMA:
                List<Cinema> cinemaList;
                type = new TypeToken<List<Cinema>>(){}.getType();
                gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Cinema.class, new CinemaDeserializer());
                gson = gsonBuilder.create();
                cinemaList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
                return cinemaList;

            case NEWS:
                List<News> newsList;
                type = new TypeToken<List<News>>(){}.getType();
                gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(News.class, new NewsDeserializer());
                gson = gsonBuilder.create();
                newsList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
                return newsList;
        }
        return null;
    }
}

