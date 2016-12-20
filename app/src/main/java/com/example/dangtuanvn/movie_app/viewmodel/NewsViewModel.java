package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastoreRX.OnSubscribeNewsDetail;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import rx.Observable;
import rx.Subscriber;


/**
 * Created by dangtuanvn on 12/9/16.
 */

public class NewsViewModel extends BaseObservable {
    private News news;
    private Context context;
    private static Transformation cropPosterTransformation;

    public NewsViewModel(News news, Context context) {
        this.news = news;
        this.context = context;
        cropPosterTransformation = getCropPosterTransformation();
    }

    @Bindable
    public String getNewsTitle() {
        return news.getNewsTitle();
    }

    @Bindable
    public String getTimeDifference() {
        return news.getTimeDifference();
    }

    @Bindable
    public String getImageUrl() {
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

    private Transformation getCropPosterTransformation() {
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

    public View.OnClickListener onClickNews() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(context, news.getNewsId());
//                newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//                    @Override
//                    public void onDataRetrievedListener(List<?> list, Exception ex) {
//                        Intent intent = new Intent(context, NewsDetailActivity.class);
//                        intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());
//                        context.startActivity(intent);
//                    }
//                });

                Observable observable = Observable.create(new OnSubscribeNewsDetail(context, news.getNewsId()));

                Subscriber subscriber = new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object object) {
                        Intent intent = new Intent(context, NewsDetailActivity.class);
                        intent.putExtra("data", ((NewsDetail) object).getContent());
                        context.startActivity(intent);
                    }
                };

                observable.subscribe(subscriber);
            }
        };
    }
}
