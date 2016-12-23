package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.example.dangtuanvn.movie_app.MovieDetailActivity;
import com.example.dangtuanvn.movie_app.datastore.MovieDetailFeedDataStore;
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
    public String getImdbPoint() {
        return "<font color=#cc0029>" + Double.toString(movie.getImdbPoint()) + "</font> <font color=#ffffff> IMDB</font>";
    }

    @Bindable
    public String getPublishDate() {
        if(pageIsUpComingPage()) {
            try {
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate;
                newDate = formatDate.parse(movie.getPublishDate());
                formatDate = new SimpleDateFormat("dd.MM");
                return formatDate.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean pageIsUpComingPage(){
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
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra("movieId", movie.getFilmId());
                        intent.putExtra("posterUrl", movie.getPosterLandscape());

                        context.startActivity(intent);
                    }
                };
                new MovieDetailFeedDataStore(context).getMovieDetail(movie.getFilmId()).subscribe(subscriber);
            }
        };
    }

    public void onDestroy(){
        if(subscriber != null && !subscriber.isUnsubscribed()){
            subscriber.unsubscribe();
        }
    }
}
