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
import com.example.dangtuanvn.movie_app.model.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieDetailAdapter extends DetailAdapter {
    private List<Movie> movieList;
    private int mPage;

    public MovieDetailAdapter(Context context, List<Movie> movieList, int mPage) {
        super(context, movieList);
        this.movieList = movieList;
        this.mPage = mPage;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView moviePic;
        private TextView IMDB;
        private TextView calendar;
        private ViewHolder(View itemView) {
            super(itemView);
            moviePic = (ImageView) itemView.findViewById(R.id.moviepic);
            IMDB = (TextView) itemView.findViewById(R.id.txtIMDB);
            calendar = (TextView) itemView.findViewById(R.id.txtCalendar);
        }
    }

    @Override
    public MovieDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_movie_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MovieDetailAdapter.ViewHolder movieHolder = (MovieDetailAdapter.ViewHolder) holder;
        String text = "<font color=#cc0029>" + movieList.get(position).getImdbPoint() + "</font> <font color=#ffffff>IMDB</font>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            movieHolder.IMDB.setText(Html.fromHtml((text), Html.FROM_HTML_MODE_LEGACY));
        } else {
            movieHolder.IMDB.setText(Html.fromHtml(text));
        }

        displayImagePicasso(movieHolder.moviePic, movieList.get(position).getPosterLandscape());
        if (mPage == 0) {
            movieHolder.calendar.setVisibility(View.GONE);
        }
        else if (mPage == 1) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate;
                newDate = format.parse(movieList.get(position).getPublishDate());
                format = new SimpleDateFormat("dd.MM");
                String date = format.format(newDate);
                movieHolder.calendar.setVisibility(View.VISIBLE);
                movieHolder.calendar.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        movieHolder.moviePic.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
