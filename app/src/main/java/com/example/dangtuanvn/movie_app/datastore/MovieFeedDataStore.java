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
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.converter.MovieDeserializer;
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
    private Context context;
    private List<Movie> list, newList;
    public MovieFeedDataStore(Context context) {
        this.context = context;
    }

    @Override
    public void getList(final OnDataRetrievedListener onDataRetrievedListener) {
        final RequestQueue queue = Volley.newRequestQueue(context);

        String BaseUrl = "http://mapp.123phim.vn/android/2.97";
        String url = "http://mapp.123phim.vn/android/2.97/film/list?status=1";

        Log.i("GET URL", url);

        final StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "Response is: "+ response);


                        Type type = new TypeToken<List<Movie>>(){}.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
                        Gson gson = gsonBuilder.create();

                        JsonParser jsonParser = new JsonParser();

                        JsonObject jsonObject = (JsonObject)jsonParser.parse(response);
//                      System.out.println(jsonObject.toString());

                        newList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
                        updateList(newList);

//                        Log.i("LIST SIZE", "" + posts.size());
//                        afterPost = jsonObject.get("data").getAsJsonObject().get("after").getAsString();
//                        Log.i("AFTER", afterPost);

                        onDataRetrievedListener.onDataRetrievedListener(list, null);
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
//                        Log.i("CURRENT UTC", "" + timestamp);
                String token = hashMd5("GVlRhvnZt0Z4WF4NrfsQXwZh" + timestamp) + " " + timestamp;

                params.put("X-123F-Version", "3");
                params.put("X-123F-Token", token);

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

    public void updateList(List<Movie> newList){
        if(list == null){
            list = new ArrayList<>();
        }
        list.addAll(newList);
    }
}

