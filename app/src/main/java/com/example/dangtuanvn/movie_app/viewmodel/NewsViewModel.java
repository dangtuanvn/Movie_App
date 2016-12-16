package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.model.News;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by dangtuanvn on 12/9/16.
 */

public class NewsViewModel extends BaseObservable {
    private News news;
    private Context context;
    private static Transformation cropPosterTransformation;

    public NewsViewModel(News news, Context context){
        this.news = news;
        this.context = context;
        cropPosterTransformation = getCropPosterTransformation();
    }

    public String getNewsTitle(){
        return news.getNewsTitle();
    }

    public String getTimeDifference(){
        return news.getTimeDifference();
    }

    public String getImageUrl(){
        return news.getImageFull();
    }

    @BindingAdapter("app:image")
    public static void loadImage(ImageView view, String url) {
        displayImagePicasso(view, url);
//        view.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    private static void displayImagePicasso(ImageView imageView, String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.white_placeholder)
                .transform(cropPosterTransformation)
                .into(imageView);
    }

    private Transformation getCropPosterTransformation(){
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
