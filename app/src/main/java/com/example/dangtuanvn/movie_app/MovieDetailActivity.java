package com.example.dangtuanvn.movie_app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dangtuanvn.movie_app.adapter.MovieDetailRecyclerAdapter;

/**
 * Created by sinhhx on 11/15/16.
 */
public class MovieDetailActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_recycler);
        int movieId = getIntent().getIntExtra("movieId",0);
        String posterUrl = getIntent().getStringExtra("posterUrl");

        RecyclerView movieDetail = (RecyclerView) findViewById(R.id.movie_detail_recycler);
        MovieDetailRecyclerAdapter adapter = new MovieDetailRecyclerAdapter(this,movieId,posterUrl);
        movieDetail.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        movieDetail.setLayoutManager(layoutManager);
    }
}

