package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;

import rx.Subscriber;

/**
 * Created by dangtuanvn on 12/9/16.
 */

public class NewsItemViewModel extends MovieAppViewModel {
    private News news;
    private Subscriber<NewsDetail> subscriber;
    private Context context;

    public NewsItemViewModel(News news, Context context) {
        super(context);
        this.context = context;
        this.news = news;
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

    public View.OnClickListener onClickNews() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subscriber = new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsDetail object) {
                        // TODO: CHECK INTERNET CONNECTION
                        Intent intent = new Intent(context, NewsDetailActivity.class);
                        intent.putExtra("data", object.getContent());
                        context.startActivity(intent);
                    }
                };
                new NewsDetailFeedDataStore(context).getNewsDetail(news.getNewsId()).subscribe(subscriber);
            }
        };
    }

    public void onDestroy(){
        if(subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }
}
