package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.android.databinding.library.baseAdapters.BR;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by dangtuanvn on 12/15/16.
 */

public class NewsTabViewModel extends BaseObservable {
    private List<News> listObject;
    private SwipeRefreshLayout swipeLayout;
    private Context context;
    private Subscriber<List<News>> subscriber;

    public NewsTabViewModel(Context context, SwipeRefreshLayout swipeLayout) {
        listObject = new ArrayList<>();
        this.context = context;
        this.swipeLayout = swipeLayout;
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public void getNewsData() {
        swipeLayout.setRefreshing(true);

        subscriber = new Subscriber<List<News>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("SUBSCRIBER ERROR", "ERROR IN API CALL" );
                Log.i("THROWABLE 1", e.toString());
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onNext(List<News> object) {
                listObject = object;
                notifyPropertyChanged(BR.listObject);
                swipeLayout.setRefreshing(false);
            }
        };

//        Observable.create(new OnSubscribeNews(context)).doOnError(new Action1<Throwable>() {
//                                 @Override
//                                 public void call(Throwable throwable) {
//                                     Log.i("SUBSCRIBER ERROR", throwable.toString());
//                                     throw new UnsupportedOperationException("onError exception");
//                                 }
//                             }).subscribe(subscriber);

        new NewsFeedDataStore(context).getNewsList().subscribe(subscriber);

    }

    private void refresh() {
        getNewsData();
    }

    public void setListObject(List<News> listObject) {
        this.listObject = listObject;
        notifyPropertyChanged(BR.listObject);
    }

    @Bindable
    public List<News> getListObject() {
        return listObject;
    }

    public void onDestroy() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }
}
