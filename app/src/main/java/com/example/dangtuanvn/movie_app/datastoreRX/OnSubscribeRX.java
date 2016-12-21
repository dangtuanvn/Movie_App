package com.example.dangtuanvn.movie_app.datastoreRX;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dangtuanvn.movie_app.datastore.SingletonQueue;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by dangtuanvn on 12/20/16.
 */

public abstract class OnSubscribeRX implements VolleyWrapperRX, Observable.OnSubscribe<Object> {
    private String X123F_TOKEN = "GVlRhvnZt0Z4WF4NrfsQXwZh";
    private String X123F_VERSION = "3";
    protected String BASE_URL = "http://mapp.123phim.vn/android/2.97/";
    private Context context;

    public OnSubscribeRX(Context context) {
        this.context = context;
    }

    @Override
    public void call(final Subscriber<? super Object> subscriber) {
        createStringRequest(setUrl()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String response) {
                if(!subscriber.isUnsubscribed()){
                    subscriber.onNext(handleData(response));
                    subscriber.onCompleted();
                }
            }
        });
    }

    protected Object handleData(String response) {
       return null;
    }

    protected String setUrl() {
        return BASE_URL;
    }

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

    @Override
    public Observable<String> createStringRequest(final String url) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(final Subscriber<? super String> subscriber) {
                        StringRequest stringRequest = new StringRequest
                                (Request.Method.GET, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(!subscriber.isUnsubscribed()){
                                            subscriber.onNext(response);
                                            subscriber.onCompleted();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.i("VOLLEY RESPONSE FAIL", "Volley gets fail");
                                        subscriber.onError(error);
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
