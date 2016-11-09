package com.example.dangtuanvn.movie_app;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.MovieFeedDataStore;
import com.example.dangtuanvn.movie_app.model.Movie;

import java.io.Serializable;
import java.util.List;


/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieTabFragment extends Fragment {

    private int mPage;
    private RecyclerView.LayoutManager mLayoutManager;
    public static final String ARG_PAGE = "ARG_PAGE";
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private MovieFeedDataStore feedDataStore;

    public static MovieTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MovieTabFragment fragment = new MovieTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.movietabrecycler, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

      if(mPage==1) {
          ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
          NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
          if (networkInfo != null && networkInfo.isConnected()) {
              feedDataStore = new MovieFeedDataStore(getContext());    // fetch data
              feedDataStore.getList(new FeedDataStore.OnDataRetrievedListener() {
                  @Override
                  public void onDataRetrievedListener(List list, Exception ex) {
                      // Toast.makeText(getApplicationContext(),"Size: " + postList.size(),Toast.LENGTH_SHORT).show(); // size 26
                      // displayRecyclerExpandableList(postList);
//                    displayRecyclerList(postList);
//                    position = postList.size() - 2;
                      List<Movie> abc = (List<Movie>) list;
                      mLayoutManager = new LinearLayoutManager(getContext());
                      mRecyclerView.setLayoutManager(mLayoutManager);

                      // specify an adapter (see also next example)
                      mAdapter = new MovieDetailAdapter(getContext(), abc);

                      mRecyclerView.setAdapter(mAdapter);

                  }
              });
          } else {

          }

      }

        return view;





    }
}

