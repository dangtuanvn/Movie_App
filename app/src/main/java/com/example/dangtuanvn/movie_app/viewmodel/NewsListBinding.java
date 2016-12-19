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
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;

import java.util.List;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public class NewsListBinding implements DisplayNewsList {
    private Context context;
    public NewsListBinding(Context context){
        this.context = context;
    }

    @Override
    public void displayNewsList(final RecyclerView mRecyclerView, final List<News> listNews) {
                        /* Use this setting to improve performance if you know that changes
                        in content do not change the layout size of the RecyclerView */
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        NewsFeedDataStore newsFDS = new NewsFeedDataStore(context);
        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                if(listNews.isEmpty()) {
                    listNews.addAll((List<News>) list);

                    RecyclerView.Adapter mAdapter = new NewsTabAdapter(context, listNews);
                    mRecyclerView.setAdapter(mAdapter);

                    // onTouch Should only be added once
                    addOnTouchNewsItem(context, mRecyclerView, listNews);
                }
            }
        });
    }

    private void addOnTouchNewsItem(final Context context, RecyclerView mRecyclerView, final List<News> newsList) {
        final GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
                final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mGestureDetector.onTouchEvent(e)) {
                    displayNewsDetail(context, rv, childView, newsList);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void displayNewsDetail(final Context context, RecyclerView rv, View childView, List<News> newsList) {
        FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(context, newsList.get(rv.getChildAdapterPosition(childView)).getNewsId());
        newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List<?> list, Exception ex) {
                // Start web view
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());
                context.startActivity(intent);
            }
        });
    }
}