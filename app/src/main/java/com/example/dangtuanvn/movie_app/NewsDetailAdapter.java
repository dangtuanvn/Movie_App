package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.News;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.ViewHolder> {
    private List<News> newsList;
    private Context context;
    private int mPage;
    Transformation cropPosterTransformation;

    public NewsDetailAdapter(Context context, List<News> newsList, int mPage, Transformation cropPosterTransformation) {
        this.newsList = newsList;
        this.mPage = mPage;
        this.context = context;
        this.cropPosterTransformation=cropPosterTransformation;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsPic;
        private TextView title;
        private TextView time;
        private ViewHolder(View itemView) {
            super(itemView);
            newsPic = (ImageView) itemView.findViewById(R.id.newspic);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            time = (TextView) itemView.findViewById(R.id.txtTime
            );
        }
    }

    public NewsDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final NewsDetailAdapter.ViewHolder holder, final int position) {
        holder.time.setText(newsList.get(position).getTimeDifference());
        holder.title.setText(newsList.get(position).getNewsTitle());
        displayNews_Picasso(holder.newsPic, newsList.get(position).getImageFull());
        holder.newsPic.setScaleType(ImageView.ScaleType.FIT_XY);
    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void displayNews_Picasso(ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.white_placeholder)
                .transform(cropPosterTransformation)
                .into(imageView);
    }
}
