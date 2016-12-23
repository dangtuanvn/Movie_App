package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.example.dangtuanvn.movie_app.model.converter.NewsDetailDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class NewsDetailFeedDataStore extends DataStore {
    private String url = BASE_URL + "news/detail?news_id=";

    public NewsDetailFeedDataStore(Context context) {
        super(context);
    }

    @Override
    protected NewsDetail handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        NewsDetail newsDetail;
        Type type = new TypeToken<NewsDetail>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NewsDetail.class, new NewsDetailDeserializer());
        Gson gson = gsonBuilder.create();
        newsDetail = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);
        return newsDetail;
    }

    public Observable<NewsDetail> getNewsDetail(int newsId){
        Observable observable = super.getDataObservable(getUrl() + newsId);
        try {
            return (Observable<NewsDetail>) observable;
        } catch (ClassCastException e){
            Log.i("ClassCastException", e.getMessage());
        }
        return null;
    }

    @Override
    protected String getUrl(){
        return url;
    }
}
