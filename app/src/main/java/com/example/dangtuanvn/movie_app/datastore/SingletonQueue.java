package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class SingletonQueue {
    public static SingletonQueue instance;
    private RequestQueue queue;
    private Context context;
    public SingletonQueue(Context context){
        this.context =context;
        queue = getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(queue == null){

            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public static synchronized SingletonQueue getInstance(Context context){
        if(instance ==null ){
            instance = new SingletonQueue(context);
        }
        return instance;
    }

    public<T> void addRequest(Request<T> request){
        queue.add(request);
    }

    public void cancelAllRequests(){
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}

