package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.Movie;

import com.example.dangtuanvn.movie_app.model.converter.MovieDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class MovieFeedDataStore extends DataStore implements FeedDataStore {
    String urlShowing = BASEURL + "film/list?status=1";
    String urlUpcoming = BASEURL + "film/list?status=2";

    public enum DataType{
        SHOWING,
        UPCOMING
    }

    DataType type;
    public MovieFeedDataStore(Context context, DataType type) {
        super(context);
        this.type = type;
    }

    @Override
    protected List<?> handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        List<Movie> movieList;
        Type type = new TypeToken<List<Movie>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieDeserializer());
        Gson gson = gsonBuilder.create();
        movieList = gson.fromJson(jsonObject.get("result").getAsJsonArray(), type);
        return movieList;
    }

    @Override
    protected String setUrl(){
        if (type == DataType.UPCOMING)
            return urlUpcoming;
        else{
            return urlShowing;
        }
    }
}

