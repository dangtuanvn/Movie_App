package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import android.support.v4.widget.SwipeRefreshLayout;

import com.android.databinding.library.baseAdapters.BR;
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
    private SwipeRefreshLayout swipeLayout;

    public ViewModelVM(Context context, SwipeRefreshLayout swipeLayout) {
        listObject = new ArrayList<>();
        newsFDS = new NewsFeedDataStore(context);
        this.swipeLayout = swipeLayout;
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            // TODO: There are distorted images before the view is replaced.
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void getNewsData() {
        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                {
                    listObject = (List<News>) list;
                    notifyPropertyChanged(BR.listObject);
                }
            }
        });
    }

    public void refresh() {
        getNewsData();
//        listObject.remove(0);  // Remove the first element from list to see the if the changes was made
//        notifyChange();   // Notify all properties changed
        swipeLayout.setRefreshing(false);
    }

    public void setListObject(List<News> newsList) {
        listObject = newsList;
        notifyPropertyChanged(BR.listObject);
    }

    @Bindable
    public List<News> getListObject() {
        return listObject;
    }
}
