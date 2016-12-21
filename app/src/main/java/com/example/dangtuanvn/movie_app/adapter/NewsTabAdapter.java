package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.viewmodel.NewsItemViewModel;
import com.example.dangtuanvn.movie_app.databinding.NewsDetailBinding;
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
        NewsDetailBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.news_detail, parent, false);

        return new NewsDetailVH(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NewsTabAdapter.NewsDetailVH newsHolder = (NewsTabAdapter.NewsDetailVH) holder;

        NewsDetailBinding binding = newsHolder.binding;
        vm = new NewsItemViewModel(newsList.get(position), context);
        binding.setNewsItemVM(vm);
    }

    protected static class NewsDetailVH extends RecyclerView.ViewHolder {
//        private ImageView newsPic;
//        private TextView title;
//        private TextView time;
        private NewsDetailBinding binding;

        private NewsDetailVH(NewsDetailBinding binding) {
            super(binding.newsDetail);
            this.binding = binding;
//            newsPic = (ImageView) binding.newsDetail.findViewById(R.id.newspic);
//            title = (TextView) binding.newsDetail.findViewById(R.id.txtTitle);
//            time = (TextView) binding.newsDetail.findViewById(R.id.txtTime);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void onDestroy(){
        if(vm != null){
            vm.onDestroy();
        }
    }
}
