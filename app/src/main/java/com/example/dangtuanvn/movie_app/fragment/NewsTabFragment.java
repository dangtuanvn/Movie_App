package com.example.dangtuanvn.movie_app.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.databinding.MovieTabRecyclerBinding;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.example.dangtuanvn.movie_app.viewmodel.ViewModelVM;

import java.util.List;

/**
 * Created by dangtuanvn on 11/22/16.
 */

public class NewsTabFragment extends Fragment {
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeLayout;

    public static NewsTabFragment newInstance() {
        NewsTabFragment fragment = new NewsTabFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateListView(inflater, container);
        return view;
    }

    public View inflateListView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.movie_tab_recycler, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.orange),
                ContextCompat.getColor(getActivity(), R.color.blue),
                ContextCompat.getColor(getActivity(), R.color.green));

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        MovieTabRecyclerBinding binding = DataBindingUtil.bind(view);
        binding.setViewModelVM(new ViewModelVM(getContext(), swipeLayout));
        return view;
    }





}
