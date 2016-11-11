package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public abstract class DataStore implements FeedDataStore {
    private static String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private static String X123F_VERSION = "3";
    protected static String BASEURL = "http://mapp.123phim.vn/android/2.97/";

    private Context context;

    public DataStore(Context context) {
        this.context = context;
    }

    @Override
    public void getList(final FeedDataStore.OnDataRetrievedListener onDataRetrievedListener) {
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
        SingletonQueue.getInstance(context).addRequest(stringRequest);
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

    protected List<?> handleData(String response){
        return null;
    }

    protected String setUrl(){
        return BASEURL;
    }
}

