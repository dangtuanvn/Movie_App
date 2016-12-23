package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.example.dangtuanvn.movie_app.model.converter.MovieDetailDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieDetailFeedDataStore extends DataStore {
    private String url = BASE_URL + "film/detail?film_id=";

    public MovieDetailFeedDataStore(Context context) {
        super(context);
    }

    @Override
    protected MovieDetail handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        MovieDetail movieDetail;
        Type type = new TypeToken<MovieDetail>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MovieDetail.class, new MovieDetailDeserializer());
        Gson gson = gsonBuilder.create();
        movieDetail = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);

        return movieDetail;
    }

    public Observable<MovieDetail> getMovieDetail(int movieId){
        Observable observable = super.getDataObservable(getUrl() + movieId);
        try {
            return (Observable<MovieDetail>) observable;
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
