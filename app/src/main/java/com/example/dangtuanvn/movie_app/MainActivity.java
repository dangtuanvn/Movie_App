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
import com.example.dangtuanvn.movie_app.model.Movie;

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
            feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    // Toast.makeText(getApplicationContext(),"Size: " + postList.size(),Toast.LENGTH_SHORT).show(); // size 26
                    // displayRecyclerExpandableList(postList);
//                    displayRecyclerList(postList);
//                    position = postList.size() - 2;
                    List<Movie> abc = (List<Movie>) list;
                    Log.i("FILM NAME" , "" + abc.get(0).getFilmName());
                    Toast.makeText(getApplicationContext(),"" + abc.get(0).getFilmName(),Toast.LENGTH_SHORT).show(); // size 26
                }
            });
        } else {

        }
    }
}
