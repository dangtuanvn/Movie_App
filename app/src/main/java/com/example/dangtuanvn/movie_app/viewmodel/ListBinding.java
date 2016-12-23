package com.example.dangtuanvn.movie_app.viewmodel;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dangtuanvn.movie_app.adapter.MovieTabAdapter;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.List;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public class ListBinding implements DisplayList {
    private Context context;
    private int tabId;
    public ListBinding(Context context, int tabId) {
        this.context = context;
        this.tabId = tabId;
    }

    @Override
    public void displayList(final RecyclerView mRecyclerView, final List listObject) {
        /* Use this setting to improve performance if you know that changes
        in content do not change the layout size of the RecyclerView */
//        mRecyclerView.setHasFixedSize(true);
        if(!listObject.isEmpty()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            switch(tabId){
                case 0:
                    mRecyclerView.setAdapter(new MovieTabAdapter(context, listObject, 0));
                case 1:
                    mRecyclerView.setAdapter(new MovieTabAdapter(context, listObject, 1));
                case 3:
                    mRecyclerView.setAdapter(new NewsTabAdapter(context, listObject));
            }
        }
    }
}