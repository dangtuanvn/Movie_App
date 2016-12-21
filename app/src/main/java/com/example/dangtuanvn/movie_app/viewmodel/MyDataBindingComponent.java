package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;

import com.example.dangtuanvn.movie_app.fragment.NewsTabFragment;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public class MyDataBindingComponent implements android.databinding.DataBindingComponent {
    private Context context;
    public MyDataBindingComponent(Context context){
        this.context = context;
    }
    private NewsListBinding binding;

    @Override
    public DisplayNewsList getDisplayNewsList() {
        binding = new NewsListBinding(context);
        return binding;
    }

    public void onDestroy(){
        if(binding != null) {
            binding.onDestroy();
        }
    }
}
