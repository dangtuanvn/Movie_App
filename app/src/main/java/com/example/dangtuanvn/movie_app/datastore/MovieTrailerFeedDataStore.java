package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.example.dangtuanvn.movie_app.model.converter.MovieTrailerDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import rx.Observable;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieTrailerFeedDataStore extends DataStore {
    private String url = BASE_URL + "film/trailer?film_id=";

    public MovieTrailerFeedDataStore(Context context) {
        super(context);
    }

    @Override
    protected List<MovieTrailer> handleData(String response) {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
            MovieTrailer movieTrailer;
            Type type = new TypeToken<MovieTrailer>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(MovieTrailer.class, new MovieTrailerDeserializer());
            Gson gson = gsonBuilder.create();
            movieTrailer = gson.fromJson(jsonObject.get("result").getAsJsonObject(), type);

            return Collections.singletonList(movieTrailer);
        }
        catch (Exception e){
            // TODO: HANDLE NULL
            return null;
        }
    }

    public Observable<MovieTrailer> getMovieTrailer(int movieId){
        Observable observable = super.getDataObservable(getUrl() + movieId);
        try {
            return (Observable<MovieTrailer>) observable;
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
