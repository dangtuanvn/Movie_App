package com.example.dangtuanvn.movie_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by dangtuanvn on 11/10/16.
 */

public abstract class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<?> list;
    private Context context;
    private Transformation cropPosterTransformation;

    public DetailAdapter(Context context, List<?> list) {
        this.list = list;
        this.context = context;
        this.cropPosterTransformation = getCropPosterTransformation();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected void displayImagePicasso(ImageView imageView, String url) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.white_placeholder)
                .transform(cropPosterTransformation)
                .into(imageView);
    }

    protected Transformation getCropPosterTransformation(){
        Transformation cropPosterTransformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                DisplayMetrics metrics = new DisplayMetrics();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                wm.getDefaultDisplay().getMetrics(metrics);
                int targetWidth = metrics.widthPixels - (metrics.widthPixels / 20);
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "cropPosterTransformation";
            }
        };
        return cropPosterTransformation;
    }
}
