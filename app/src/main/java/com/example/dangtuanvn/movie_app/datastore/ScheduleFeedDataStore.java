package com.example.dangtuanvn.movie_app.datastore;

import android.content.Context;

import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.Session;
import com.example.dangtuanvn.movie_app.model.converter.ScheduleDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by dangtuanvn on 11/15/16.
 */

public class ScheduleFeedDataStore extends DataStore {
    private String url = BASEURL + "session/cinema?film_id=840&date=2016-11-17";
    private String movieId;
    private String date;

    public ScheduleFeedDataStore(Context context, String movieId, String date) {
        super(context);
        this.movieId = movieId;
        this.date = date;
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

    @Override
    protected String setUrl(){
       return url + "?film_id=" + movieId + "&date=" + date;
    }

}
