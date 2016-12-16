package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dangtuanvn on 12/15/16.
 */

public class ViewModelVM extends BaseObservable {
    private List<News> listObject;
    private SwipeRefreshLayout swipeLayout;
    //    private RecyclerView.Adapter mAdapter;
    private Context context;
    private NewsFeedDataStore newsFDS;
    private Handler handlerFDS;

    public ViewModelVM(final Context context, SwipeRefreshLayout swipeLayout) {
        newsFDS = new NewsFeedDataStore(context);
        this.swipeLayout = swipeLayout;
        this.context = context;
//        this.mAdapter = mAdapter;

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    public List<News> getListObject() {
        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List list, Exception ex) {
                listObject = (List<News>) list;
                Toast.makeText(context, "abc", Toast.LENGTH_LONG).show();
            }
        });
//        while(!check[0]){
//            if(listObject != null){
//                return listObject;
//            }
//        }
        return listObject;
    }

//    @BindingAdapter("app:items")
//    public void displayNewsList(final RecyclerView mRecyclerView, final List<News> listNews) {
//
//    }
}
