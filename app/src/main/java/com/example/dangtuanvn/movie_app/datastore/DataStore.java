package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dangtuanvn.movie_app.datastoreRX.VolleyWrapperRX;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public abstract class DataStore implements VolleyWrapperRX {
    private static String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private static String X123F_VERSION = "3";
    protected static String BASE_URL = "http://mapp.123phim.vn/android/2.97/";

    private Context context;

    public DataStore(Context context) {
        this.context = context;
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

    protected Object handleData(String response){
        return null;
    }

    protected String setUrl(){
        return BASE_URL;
    }

    @Override
    public Observable<Object> getDataObservable() {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(final Subscriber<? super Object> subscriber) {
                        String url = setUrl();

                        StringRequest stringRequest = new StringRequest
                                (Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(!subscriber.isUnsubscribed()){
                                            subscriber.onNext(handleData(response));
                                            subscriber.onCompleted();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("VOLLEY RESPONSE FAIL", "Volley gets fail");
                                        if(!subscriber.isUnsubscribed()) {
                                            subscriber.onError(error);
                                        }
                                    }
                                }) {

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();

                                long timestamp = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
                                String accessToken = hashMd5(X123F_TOKEN + timestamp) + " " + timestamp;

                                params.put("X-123F-Version", X123F_VERSION);
                                params.put("X-123F-Token", accessToken);

                                return params;
                            }
                        };

                        SingletonQueue.getInstance(context).addRequest(stringRequest);
                    }
                });
            }
        });
    }
}

