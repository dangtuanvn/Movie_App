package com.example.dangtuanvn.movie_app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailRecyclerAdapter;
import com.example.dangtuanvn.movie_app.model.MovieDetail;

/**
 * Created by dangtuanvn on 11/21/16.
 */

public class MovieDetailFragment extends Fragment {
    private int movieId;
    private String posterUrl;
    private MovieTabFragmentListener listener;

    public MovieDetailFragment(){

    }

    @SuppressLint("ValidFragment")
    public MovieDetailFragment(MovieTabFragmentListener listener, int movieId, String posterUrl){
        this.listener = listener;
        this.movieId = movieId;
        this.posterUrl = posterUrl;
    }

    public void backPressed() {
        listener.onSwitchToNextFragment(movieId, posterUrl);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_recycler, container, false);
//        movieId = getArguments().getInt("movieId");
//        posterUrl = getArguments().getString("posterUrl");
        Log.i("FRAGMENT CREATED", "CREATED");
        RecyclerView movieDetail = (RecyclerView) view.findViewById(R.id.movie_detail_recycler);
        MovieDetailRecyclerAdapter adapter = new MovieDetailRecyclerAdapter(getContext(), movieId, posterUrl);
        movieDetail.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL, false);
        movieDetail.setLayoutManager(layoutManager);

        return view;
    }
}
