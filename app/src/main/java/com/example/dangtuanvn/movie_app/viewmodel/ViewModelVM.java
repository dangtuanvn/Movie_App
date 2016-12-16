package com.example.dangtuanvn.movie_app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dangtuanvn.movie_app.NewsDetailActivity;
import com.example.dangtuanvn.movie_app.R;
import com.example.dangtuanvn.movie_app.adapter.NewsTabAdapter;
import com.example.dangtuanvn.movie_app.datastore.FeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsDetailFeedDataStore;
import com.example.dangtuanvn.movie_app.datastore.NewsFeedDataStore;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.NewsDetail;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dangtuanvn on 12/15/16.
 */

public class ViewModelVM extends BaseObservable {
    private List<News> listObject;
    private SwipeRefreshLayout swipeLayout;
    //    private RecyclerView.Adapter mAdapter;
    private Context context;
    private NewsFeedDataStore newsFDS;
    private Handler handlerFDS;

    public ViewModelVM(final Context context, SwipeRefreshLayout swipeLayout) {
        newsFDS = new NewsFeedDataStore(context);
        this.swipeLayout = swipeLayout;
        this.context = context;
//        this.mAdapter = mAdapter;

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        DataBindingUtil.setDefaultComponent(new MyOwnDefaultDataBindingComponent());
    }

    public interface DisplayNewsList {
        @BindingAdapter({"app:items"})
        void displayNewsList(final RecyclerView mRecyclerView, final List<News> listNews);
    }

    // Applying 2nd answer
    // http://stackoverflow.com/questions/39283855/what-is-databindingcomponent-class-in-android-databinding
    public class NewsListBinding implements DisplayNewsList {
        @Override
        public void displayNewsList(RecyclerView mRecyclerView, List<News> listNews) {
                        /* Use this setting to improve performance if you know that changes
                        in content do not change the layout size of the RecyclerView */
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            RecyclerView.Adapter mAdapter = new NewsTabAdapter(context, listObject);
            mRecyclerView.setAdapter((mAdapter));

            // TODO: Should only be added once
            addOnTouchNewsItem(mRecyclerView, listNews);
        }


        private void addOnTouchNewsItem(RecyclerView mRecyclerView, final List<News> newsList) {
            final GestureDetector mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
                    final View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && mGestureDetector.onTouchEvent(e)) {
                        displayNewsDetail(rv, childView, newsList);
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            });
        }

        private void displayNewsDetail(RecyclerView rv, View childView, List<News> newsList) {
            FeedDataStore newsDetailFDS = new NewsDetailFeedDataStore(context, newsList.get(rv.getChildAdapterPosition(childView)).getNewsId());
            newsDetailFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
                @Override
                public void onDataRetrievedListener(List<?> list, Exception ex) {
                    // Start web view
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    intent.putExtra("data", ((NewsDetail) list.get(0)).getContent());
                    context.startActivity(intent);
                }
            });
        }
    }

    public class MyOwnDefaultDataBindingComponent implements android.databinding.DataBindingComponent{
        @Override
        public DisplayNewsList getDisplayNewsList() {
            return new NewsListBinding();
        }
    }


    public List<News> getListObject() {
//        final List<News> list = new ArrayList<>();
        final boolean[] check = {false};
        newsFDS.getList(new FeedDataStore.OnDataRetrievedListener() {
            @Override
            public void onDataRetrievedListener(List list, Exception ex) {
                listObject = (List<News>) list;
                Toast.makeText(context, "abc", Toast.LENGTH_LONG).show();
                check[0] = true;
            }
        });
//        while(!check[0]){
//            if(listObject != null){
//                return listObject;
//            }
//        }
        return listObject;
    }

//    @BindingAdapter("app:items")
//    public void displayNewsList(final RecyclerView mRecyclerView, final List<News> listNews) {
//
//    }


}
