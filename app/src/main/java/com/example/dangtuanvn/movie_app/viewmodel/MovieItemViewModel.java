package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.model.Movie;
import com.example.dangtuanvn.movie_app.model.MovieDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Subscriber;

/**
 * Created by dangtuanvn on 12/22/16.
 */

public class MovieItemViewModel extends MovieAppViewModel {
    private Context context;
    private Movie movie;
    private Subscriber<MovieDetail> subscriber;
    private int page;

    public MovieItemViewModel(Movie movie, Context context, int page) {
        super(context);
        this.context = context;
        this.movie = movie;
        this.page = page;
    }

    @Bindable
    public double getImdbPoint() {
        return movie.getImdbPoint();
    }

    @Bindable
    public String getPublishDate() {
        if(isUpComingPage()) {
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate;
                newDate = format.parse(movie.getPublishDate());
                format = new SimpleDateFormat("dd.MM");
                return format.format(newDate);
//            movieHolder.calendar.setVisibility(View.VISIBLE);
//            movieHolder.calendar.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isUpComingPage(){
        return page == 1;
    }

    @Bindable
    public String getImageUrl() {
        return movie.getPosterLandscape();
    }

    @BindingAdapter("app:image")
    public static void loadImage(ImageView view, String url) {
        displayImagePicasso(view, url);
//        view.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public View.OnClickListener onClickMovie() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subscriber = new Subscriber<MovieDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieDetail object) {
//                        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
//                        intent.putExtra("movieId", movie.getFilmId());
//                        intent.putExtra("posterUrl", movie.getPosterLandscape());
//
//                        startActivity(intent);
                    }
                };
//                new NewsDetailFeedDataStore(context).getNewsDetail(news.getNewsId()).subscribe(subscriber);
            }
        };
    }

    public void onDestroy(){
        if(subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }
}
