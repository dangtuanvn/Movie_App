package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.News;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsDetailAdapter extends DetailAdapter {
    private List<News> newsList;

    public NewsDetailAdapter(Context context, List<News> newsList) {
        super(context, newsList);
        this.newsList = newsList;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsPic;
        private TextView title;
        private TextView time;
        private ViewHolder(View itemView) {
            super(itemView);
            newsPic = (ImageView) itemView.findViewById(R.id.newspic);
            title = (TextView) itemView.findViewById(R.id.txtTitle);
            time = (TextView) itemView.findViewById(R.id.txtTime);
        }
    }

    public NewsDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_detail, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsDetailAdapter.ViewHolder newsHolder = (NewsDetailAdapter.ViewHolder) holder;
        newsHolder.time.setText(newsList.get(position).getTimeDifference());
        newsHolder.title.setText(newsList.get(position).getNewsTitle());
        displayImagePicasso(newsHolder.newsPic, newsList.get(position).getImageFull());
        newsHolder.newsPic.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
