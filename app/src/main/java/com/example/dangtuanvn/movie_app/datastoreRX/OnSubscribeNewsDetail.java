package com.example.dangtuanvn.movie_app.datastoreRX;

import android.content.Context;

import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.example.dangtuanvn.movie_app.model.converter.NewsDetailDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by dangtuanvn on 12/20/16.
 */

public class OnSubscribeNewsDetail extends OnSubscribeRX {
    private String url = BASE_URL + "news/detail?news_id=";
    private int newsId;

    public OnSubscribeNewsDetail(Context context, int newsId) {
        super(context);
        this.newsId = newsId;
    }

    @Override
    public NewsDetail handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        NewsDetail newsDetail;
        Type type = new TypeToken<NewsDetail>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NewsDetail.class, new NewsDetailDeserializer());
        Gson gson = gsonBuilder.create();
        newsDetail = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);
        return newsDetail;
    }

    @Override
    protected String setUrl(){
        return url + newsId;
    }
}
