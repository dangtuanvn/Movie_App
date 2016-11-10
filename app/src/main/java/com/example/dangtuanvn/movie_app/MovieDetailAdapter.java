package com.example.dangtuanvn.movie_app;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.model.Movie;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {
    private List<Movie> movieList;
    private Context context;
    private int mPage;
    Transformation cropPosterTransformation;

    public MovieDetailAdapter(Context context, List<Movie> movieList, int mPage,Transformation cropPosterTransformation) {
        this.movieList = movieList;
        this.mPage = mPage;
        this.context = context;
        this.cropPosterTransformation=cropPosterTransformation;
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

    public MovieDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.showing_movie_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(final MovieDetailAdapter.ViewHolder holder, final int position) {
        String text = "<font color=#cc0029>" + movieList.get(position).getImdbPoint() + "</font> <font color=#ffffff>IMDB</font>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.IMDB.setText(Html.fromHtml((text), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.IMDB.setText(Html.fromHtml(text));
        }
//        holder.IMDB.setText(Html.fromHtml(text));

        displayMovieList_Picasso(holder.moviePic, movieList.get(position).getPosterLandscape());
        if (mPage == 1) {
            holder.calendar.setVisibility(View.GONE);
        }
        else if (mPage == 2) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate;
                newDate = format.parse(movieList.get(position).getPublishDate());
                format = new SimpleDateFormat("dd.MM");
                String date = format.format(newDate);
                holder.calendar.setVisibility(View.VISIBLE);
                holder.calendar.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.moviePic.setScaleType(ImageView.ScaleType.FIT_XY);
    }




    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void displayMovieList_Picasso(ImageView imageView, String url) {

        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.white_placeholder)
                .transform(cropPosterTransformation)
                .into(imageView);
    }


}
