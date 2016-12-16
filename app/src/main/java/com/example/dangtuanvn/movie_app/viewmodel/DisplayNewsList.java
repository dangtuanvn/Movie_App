package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.dangtuanvn.movie_app.model.News;

import java.util.List;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public interface DisplayNewsList {
    @BindingAdapter("app:items")
    void displayNewsList(RecyclerView mRecyclerView, List<News> listNews);
}