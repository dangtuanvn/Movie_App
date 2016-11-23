package com.example.dangtuanvn.movie_app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.MovieDetailAdapter;

/**
 * Created by dangtuanvn on 11/21/16.
 */

public class MovieDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_recycler, container, false);
        int movieId = getArguments().getInt("movieId");
        String posterUrl = getArguments().getString("posterUrl");
//        Log.i("FRAGMENT CREATED", "CREATED");
        RecyclerView movieDetail = (RecyclerView) view.findViewById(R.id.movie_detail_recycler);
        MovieDetailAdapter adapter = new MovieDetailAdapter(getContext(), movieId, posterUrl);
        movieDetail.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.VERTICAL, false);
        movieDetail.setLayoutManager(layoutManager);
        return view;
    }
}
