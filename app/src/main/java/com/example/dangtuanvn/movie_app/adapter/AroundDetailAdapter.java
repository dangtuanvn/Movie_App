package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/11/16.
 */
public class AroundDetailAdapter extends DetailAdapter {
    private List<Cinema> cinemaList;
    private int mPage;

    public AroundDetailAdapter(Context context, List<Cinema> cinemaList, int mPage) {
        super(context, cinemaList);
        this.cinemaList = cinemaList;
        this.mPage = mPage;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cinemaicon;
        private TextView cinemaname;
        private TextView cinemaadress;
        private ViewHolder(View itemView) {
            super(itemView);
            cinemaicon = (ImageView) itemView.findViewById(R.id.locationicon);
            cinemaname= (TextView) itemView.findViewById(R.id.cinema_name);
            cinemaadress = (TextView) itemView.findViewById(R.id.address);
        }
    }

    public AroundDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.locationdetail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        AroundDetailAdapter.ViewHolder cinemaHolder = (AroundDetailAdapter.ViewHolder) holder;
        cinemaHolder.cinemaicon.setImageResource(R.drawable.ic_around_highlight);

    }
}
