package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.converter.CinemaDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class CinemaFeedDataStore extends DataStore {
    private String url = BASE_URL + "cinema/list";

    public CinemaFeedDataStore(Context context) {
        super(context);
    }

    @Override
    protected List<Cinema> handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        List<Cinema> cinemaList;
        Type type = new TypeToken<List<Cinema>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cinema.class, new CinemaDeserializer());
        Gson gson = gsonBuilder.create();
        cinemaList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
        return cinemaList;
    }

    public Observable<List<Cinema>> getCinemaList() {
        Observable observable = super.getDataObservable(getUrl());
        try {
            return (Observable<List<Cinema>>) observable;
        } catch (ClassCastException e) {
            Log.i("ClassCastException", e.getMessage());
        }
        return null;
    }

    @Override
    protected String getUrl(){
        return url;
    }


}
