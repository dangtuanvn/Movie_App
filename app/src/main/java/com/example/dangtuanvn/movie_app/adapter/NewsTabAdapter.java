package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.databinding.NewsItemBinding;
import com.example.dangtuanvn.movie_app.viewmodel.NewsItemViewModel;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.List;

/**
 * Created by sinhhx on 11/9/16.
 */
public class NewsTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<News> newsList;
    private NewsItemViewModel vm;

    public NewsTabAdapter(Context context, List<News> newsList) {
        this.newsList = newsList;
        this.context = context;
    }

    public NewsTabAdapter.NewsDetailVH onCreateViewHolder(ViewGroup parent, int viewType) {
        NewsItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.news_item, parent, false);

        return new NewsDetailVH(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsTabAdapter.NewsDetailVH newsHolder = (NewsTabAdapter.NewsDetailVH) holder;

        NewsItemBinding binding = newsHolder.binding;
        vm = new NewsItemViewModel(newsList.get(position), context);
        binding.setNewsItemVM(vm);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView){
        if(vm != null){
            vm.onDestroy();
        }
        super.onDetachedFromRecyclerView(recyclerView);
    }

    private static class NewsDetailVH extends RecyclerView.ViewHolder {
        private NewsItemBinding binding;

        private NewsDetailVH(NewsItemBinding binding) {
            super(binding.newsDetail);
            this.binding = binding;
        }
    }
}
