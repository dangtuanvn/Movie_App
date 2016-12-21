package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.android.databinding.library.baseAdapters.BR;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.datastoreRX.OnSubscribeNews;
import com.example.dangtuanvn.movie_app.model.News;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by dangtuanvn on 12/15/16.
 */

public class NewsTabViewModel extends BaseObservable {
    private List<News> listObject;
//    private NewsFeedDataStore newsFDS;
    private SwipeRefreshLayout swipeLayout;
    private Context context;
    private Subscriber subscriber;

    public NewsTabViewModel(Context context, SwipeRefreshLayout swipeLayout) {
//        this.compositeSubscription = compositeSubscription;
        listObject = new ArrayList<>();
//        newsFDS = new NewsFeedDataStore(context);
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

//        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
//            @Override
//            public void onDataRetrievedListener(List<?> list, Exception ex) {
//                {
//                    listObject = (List<News>) list;
//                    notifyPropertyChanged(BR.listObject);
//                    swipeLayout.setRefreshing(false);
//                }
//            }
//        });

        subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("SUBSCRIBER ERROR", "ERROR IN API CALL" );
                Log.i("THROWABLE 1", e.toString());
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onNext(Object object) {
                listObject = (List<News>) object;
                notifyPropertyChanged(BR.listObject);
                swipeLayout.setRefreshing(false);
            }
        };

        Observable.create(new OnSubscribeNews(context)).doOnError(new Action1<Throwable>() {
                                 @Override
                                 public void call(Throwable throwable) {
                                     Log.i("SUBSCRIBER ERROR", throwable.toString());
                                     throw new UnsupportedOperationException("onError exception");
                                 }
                             }).subscribe(subscriber);

//        valueObservable().subscribe(subscriber);
//        compositeSubscription.add(Observable.create(new OnSubscribeNews(context)).subscribe(subscriber));
    }

    private void refresh() {
        getNewsData();
//        listObject.remove(0);  // Remove the first element from list to see the if the changes was made
//        notifyChange();   // Notify all properties changed
//        notifyPropertyChanged(BR.listObject);
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

    public Observable<Object> valueObservable() {
        return Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.create(new OnSubscribeNews(context));
            }
        });
    }
}
