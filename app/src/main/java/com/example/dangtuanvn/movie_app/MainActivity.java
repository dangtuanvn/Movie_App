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
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MovieFeedDataStore feedDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            feedDataStore = new MovieFeedDataStore(this);    // fetch data
            feedDataStore.setDataType(MovieFeedDataStore.DataType.CINEMA);
            feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    // Toast.makeText(getApplicationContext(),"Size: " + postList.size(),Toast.LENGTH_SHORT).show(); // size 26
                    // displayRecyclerExpandableList(postList);
//                    displayRecyclerList(postList);
//                    position = postList.size() - 2;

//                    List<Movie> movieList = (List<Movie>) list;
//                    Log.i("FILM NAME" , "" + movieList.get(0).getFilmName());
//                    Toast.makeText(getApplicationContext(),"" + abc.get(0).getFilmName(),Toast.LENGTH_LONG).show();

//                    List<News> newsList = (List<News>) list;
//                    Log.i("NEWS TITLE" , "" + newsList.get(0).getDateAdd());
//                    Toast.makeText(getApplicationContext(),"" + newsList.get(0).getDateAdd(),Toast.LENGTH_LONG).show();

                    List<Cinema> newsList = (List<Cinema>) list;
                    Log.i("NEWS TITLE" , "" + newsList.get(0).getLatitude());
                    Toast.makeText(getApplicationContext(),"" + newsList.get(0).getLatitude(),Toast.LENGTH_LONG).show();

                }});
        } else {
            feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    feedDataStore.setDataType(MovieFeedDataStore.DataType.UPCOMING);
                    feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                        @Override
                        public void onDataRetrievedListener(List<?> list, Exception ex) {

                        }});
                }
            });
        }
    }
}
