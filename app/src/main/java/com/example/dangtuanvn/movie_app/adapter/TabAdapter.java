package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.viewmodel.ViewModelVM;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
