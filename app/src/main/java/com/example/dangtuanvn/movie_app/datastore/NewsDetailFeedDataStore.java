package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.example.dangtuanvn.movie_app.model.converter.NewsDetailDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class NewsDetailFeedDataStore extends DataStore {
    private String url = BASEURL + "news/detail?news_id=";
    private int newsId;

    public NewsDetailFeedDataStore(Context context, int newsId) {
        super(context);
        this.newsId = newsId;
    }

    @Override
    protected List<NewsDetail> handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        NewsDetail newsDetail;
        Type type = new TypeToken<NewsDetail>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NewsDetail.class, new NewsDetailDeserializer());
        Gson gson = gsonBuilder.create();
        newsDetail = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);
        return Collections.singletonList(newsDetail);
    }

    @Override
    protected String setUrl(){
        return url + newsId;
    }
}
