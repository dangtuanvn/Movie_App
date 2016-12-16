package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.viewmodel.NewsViewModel;
import com.example.dangtuanvn.movie_app.databinding.NewsDetailBinding;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.List;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsTabAdapter extends TabAdapter {
    private Context context;
    private List<News> newsList;

    public NewsTabAdapter(Context context, List<News> newsList) {
        super(context, newsList);
        this.newsList = newsList;
        this.context = context;
    }

    public NewsTabAdapter.NewsDetailVH onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.news_detail, parent, false);

        return new NewsDetailVH(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsTabAdapter.NewsDetailVH newsHolder = (NewsTabAdapter.NewsDetailVH) holder;

        NewsDetailBinding binding = newsHolder.binding;
        binding.setNewsVM(new NewsViewModel(newsList.get(position), context));
        displayImagePicasso(newsHolder.newsPic, newsList.get(position).getImageFull());

    }

    protected static class NewsDetailVH extends RecyclerView.ViewHolder {
        private ImageView newsPic;
        private TextView title;
        private TextView time;
        private NewsDetailBinding binding;

        private NewsDetailVH(NewsDetailBinding binding) {
            super(binding.newsDetail);
            this.binding = binding;
            newsPic = (ImageView) binding.newsDetail.findViewById(R.id.newspic);
            title = (TextView) binding.newsDetail.findViewById(R.id.txtTitle);
            time = (TextView) binding.newsDetail.findViewById(R.id.txtTime);
        }
    }
}
