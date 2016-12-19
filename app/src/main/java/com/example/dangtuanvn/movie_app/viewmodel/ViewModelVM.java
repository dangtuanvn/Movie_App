package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.os.Handler;

import android.support.v4.widget.SwipeRefreshLayout;

import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 12/15/16.
 */

public class ViewModelVM extends BaseObservable {
    private List<News> listObject;
    private NewsFeedDataStore newsFDS;

    public ViewModelVM(final Context context, final SwipeRefreshLayout swipeLayout) {
        listObject = new ArrayList<>();
        newsFDS = new NewsFeedDataStore(context);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            // TODO: There are distorted images before the view is replaced.
            @Override
            public void onRefresh() {
                newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                    @Override
                    public void onDataRetrievedListener(List<?> list, Exception ex) {
                        if(!listObject.isEmpty()) {
                            listObject.clear();
                        }
                        listObject.addAll((List<News>) list);
//                        listObject.remove(0);
                        notifyChange();
                        swipeLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public List<News> getListObject() {
        return listObject;
    }
}
