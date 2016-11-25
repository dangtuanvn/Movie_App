package com.example.dangtuanvn.movie_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dangtuanvn.movie_app.MainActivity;
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
        Button retryBtn = (Button) view.findViewById(R.id.retry_button);
        final Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        return view;
    }}

