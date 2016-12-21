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

    @Override
    public DisplayNewsList getDisplayNewsList() {
        return new NewsListBinding(context);
    }

    public void onDestroy(){

    }
}
