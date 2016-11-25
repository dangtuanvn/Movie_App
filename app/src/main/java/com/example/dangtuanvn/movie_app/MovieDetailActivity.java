package com.example.dangtuanvn.movie_app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;

/**
 * Created by sinhhx on 11/15/16.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private Toolbar mytoolbar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_recycler);
        mytoolbar = (Toolbar)findViewById(R.id.my_toolbar);
        mytoolbar.setNavigationIcon(R.drawable.back_btn);
        mytoolbar.setTitle("");
        setSupportActionBar(mytoolbar);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        int movieId = getIntent().getIntExtra("movieId",0);
        String posterUrl = getIntent().getStringExtra("posterUrl");

        RecyclerView movieDetail = (RecyclerView) findViewById(R.id.movie_detail_recycler);
        MovieDetailAdapter adapter = new MovieDetailAdapter(this, movieId, posterUrl);
        movieDetail.setAdapter(adapter);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        movieDetail.setLayoutManager(layoutManager);

    }
}

