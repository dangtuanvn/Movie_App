package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;
import android.util.Log;

import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.converter.ScheduleDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;

/**
 * Created by dangtuanvn on 11/15/16.
 */

public class ScheduleFeedDataStore extends DataStore {
    private String url = BASE_URL + "session/cinema";

    public ScheduleFeedDataStore(Context context) {
        super(context);
    }

    @Override
    protected List<Schedule> handleData(String response) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
        List<Schedule> listSchedule;
        Type type = new TypeToken<List<Schedule>>() {}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Schedule.class, new ScheduleDeserializer());
        Gson gson = gsonBuilder.create();
        listSchedule = gson.fromJson(jsonObject.get("result").getAsJsonObject().get("list_session").getAsJsonArray(), type);

        return listSchedule;
    }

    public Observable<List<Schedule>> getScheduleList(int movieId, String date){
        Observable observable = super.getDataObservable(getUrl() + "?film_id=" + movieId + "&date=" + date);
        try {
            return (Observable<List<Schedule>>) observable;
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
