package com.example.dangtuanvn.movie_app.datastore;


import java.util.List;

/**
 * Created by Jupiter (vu.cao.duy@gmail.com) on 10/8/15.
 */
public interface FeedDataStore {
    interface OnDataRetrievedListener {
        void onDataRetrievedListener(List<?> list, Exception ex);
    }

    void getList(OnDataRetrievedListener onDataRetrievedListener);
}