package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieFeedDataStore movieFeedDataStore;
    private NewsFeedDataStore newsFeedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            movieFeedDataStore = new MovieFeedDataStore(this, MovieFeedDataStore.DataType.SHOWING);    // fetch data

            movieFeedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    List<Movie> movieList = (List<Movie>) list;
                    Log.i("FILM NAME" , "" + movieList.get(0).getFilmName());
                    Toast.makeText(getApplicationContext(),"" + movieList.get(0).getFilmName(),Toast.LENGTH_LONG).show();
                }
            });

            newsFeedDataStore = new NewsFeedDataStore(this);
            newsFeedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    if (list != null && checkType(News.class, list.get(0))) {
                        List<News> newsList = (List<News>) list;
//                    List<News> newsList = getNewsList(list);
                        Log.i("NEWS TITLE", "" + newsList.get(0).getNewsTitle());
                        Toast.makeText(getApplicationContext(), "" + newsList.get(0).getNewsTitle(), Toast.LENGTH_LONG).show();
                    }
                    else{
//                         IF THERE IS NO DATA
                    }
                }
            });
//                    List<Cinema> cinemaList = (List<Cinema>) list;
//                    Log.i("CINEMA ADDRESS" , "" + cinemaList.get(0).getCinemaAddress());
//                    Toast.makeText(getApplicationContext(),"" + cinemaList.get(0).getCinemaAddress(),Toast.LENGTH_LONG).show();

        } else {
            // SHOW NO NETWORK CONNECTION ERROR HERE
        }
    }

    public List<Movie> getMovieList(List<?> list) {
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            movieList.add((Movie) list.get(i));
        }
        return movieList;
    }

    public static boolean checkType(Class expectedType, Object object) {
        return expectedType.isInstance(object);
    }
}
