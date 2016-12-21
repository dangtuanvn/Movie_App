package com.example.dangtuanvn.movie_app.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by dangtuanvn on 11/10/16.
 */

public abstract class TabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<?> listObject;

    public TabAdapter(List<?> listObject) {
        this.listObject = listObject;
    }

    @Override
    public int getItemCount() {
        return listObject.size();
    }
}
