package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;

import java.util.List;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public class NewsListBinding implements DisplayNewsList {
    private Context context;
    private RecyclerView.Adapter mAdapter;

    public NewsListBinding(Context context) {
        this.context = context;
    }

    @Override
    public void displayNewsList(final RecyclerView mRecyclerView, final List<News> listNews) {
        /* Use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView */
//        mRecyclerView.setHasFixedSize(true);
        if(!listNews.isEmpty()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new NewsTabAdapter(context, listNews);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}