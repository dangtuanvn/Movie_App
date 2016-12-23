package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.databinding.MovieItemBinding;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.viewmodel.MovieItemViewModel;

import java.util.List;

/**
 * Created by sinhhx on 11/7/16.
 */
public class MovieTabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Movie> movieList;
    private int page;
    private Context context;
    private MovieItemViewModel vm;

    public MovieTabAdapter(Context context, List<Movie> movieList, int page) {
        this.context = context;
        this.movieList = movieList;
        this.page = page;
    }


    @Override
    public MovieTabAdapter.MovieItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item, parent, false);

        return new MovieItemVH(binding);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MovieTabAdapter.MovieItemVH movieHolder = (MovieTabAdapter.MovieItemVH) holder;

        MovieItemBinding binding = movieHolder.binding;
        vm = new MovieItemViewModel(movieList.get(position), context, page);
        binding.setMovieItemVM(vm);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView){
        if(vm != null){
            vm.onDestroy();
        }
        super.onDetachedFromRecyclerView(recyclerView);
    }

    private static class MovieItemVH extends RecyclerView.ViewHolder {
        private MovieItemBinding binding;

        private MovieItemVH(MovieItemBinding binding) {
            super(binding.movieDetail);
            this.binding = binding;
        }
    }
//        String text = "<font color=#cc0029>" + movieList.get(position).getImdbPoint() + "</font> <font color=#ffffff>IMDB</font>";
}
