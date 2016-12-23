package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;

/**
 * Created by dangtuanvn on 12/16/16.
 */

public class MyDataBindingComponent implements android.databinding.DataBindingComponent {
    private Context context;
    private int tabId;
    public MyDataBindingComponent(Context context, int tabId){
        this.context = context;
        this.tabId = tabId;
    }

    @Override
    public DisplayList getDisplayList() {
        return new ListBinding(context, tabId);
    }
}
