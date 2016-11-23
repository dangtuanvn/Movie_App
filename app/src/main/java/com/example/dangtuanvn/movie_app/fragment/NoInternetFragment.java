package com.example.dangtuanvn.movie_app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;

/**
 * Created by sinhhx on 11/22/16.
 */
public class NoInternetFragment extends Fragment {
    public static NoInternetFragment newInstance() {
        NoInternetFragment fragment = new NoInternetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_connection, container, false);
        return view;
    }
}

