package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.converter.MovieDetailDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieDetailFeedDataStore extends DataStore {
    private String url = BASEURL + "film/detail?film_id=";
    private int movieId;

    public MovieDetailFeedDataStore(Context context, int movieId) {
        super(context);
        this.movieId = movieId;
    }

    @Override
    protected List<MovieDetail> handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        MovieDetail movieDetail;
        Type type = new TypeToken<MovieDetail>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MovieDetail.class, new MovieDetailDeserializer());
        Gson gson = gsonBuilder.create();
        movieDetail = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);

        return Collections.singletonList(movieDetail);
    }

    @Override
    protected String setUrl(){
        return url + movieId;
    }
}
