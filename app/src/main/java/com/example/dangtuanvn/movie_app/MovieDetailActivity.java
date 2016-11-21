package com.example.dangtuanvn.movie_app;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailReyclerAdapter;
import com.example.dangtuanvn.movie_app.adapter.ScheduleExpandableAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieTrailerFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.ScheduleFeedDataStore;
import com.example.dangtuanvn.movie_app.model.ScheduleCinemaGroupList;
import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.MovieTrailer;

import com.example.dangtuanvn.movie_app.model.Schedule;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

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
        MovieDetailReyclerAdapter adapter = new MovieDetailReyclerAdapter(this,movieId,posterUrl);
        movieDetail.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        movieDetail.setLayoutManager(layoutManager);
    }
}

