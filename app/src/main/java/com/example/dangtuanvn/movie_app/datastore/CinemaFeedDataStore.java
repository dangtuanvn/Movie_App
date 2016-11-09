package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.converter.CinemaDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class CinemaFeedDataStore extends DataStore {
    private String url = BASEURL + "cinema/list";

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

    @Override
    protected String setUrl(){
        return url;
    }


}
