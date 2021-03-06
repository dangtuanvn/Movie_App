package com.example.dangtuanvn.movie_app.model.converter;

import android.util.Log;

import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.SessionTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/15/16.
 */

public class ScheduleDeserializer extends EasyDeserializer<Schedule> {
    @Override
    public Schedule deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Schedule schedule = null;
        if (json != null && json.isJsonObject()) {
            schedule = new Schedule();
            JsonObject jsonObject = json.getAsJsonObject();
            schedule.setpCinemaId(getIntValue(jsonObject.get("p_cinema_id"), 0));
            schedule.setpCinemaName(getStringValue(jsonObject.get("p_cinema_name"), null));
            schedule.setCinemaId(getIntValue(jsonObject.get("cinema_id"), 0));
            schedule.setCinemaAddress(getStringValue(jsonObject.get("cinema_address"), null));
            schedule.setCinemaName(getStringValue(jsonObject.get("cinema_name"), null));
            schedule.setCinemaNameS1(getStringValue(jsonObject.get("cinema_name_s1"), null));
            schedule.setCinemaNameS2(getStringValue(jsonObject.get("cinema_name_s2"), null));
            schedule.setLatitude(getDoubleValue(jsonObject.get("cinema_latitude"), 0));
            schedule.setLongitude(getDoubleValue(jsonObject.get("cinema_longitude"), 0));
            schedule.setListPrice(getStringValue(jsonObject.get("list_price"), null));
            schedule.setCinemaLogo(getStringValue(jsonObject.get("cinema_logo"), null));
            schedule.setUseVoucher(getBooleanValue(jsonObject.get("is_use_voucher"), false));
            JsonElement jsonListSchedule = jsonObject.get("group_sessions");
            if (jsonListSchedule != null && jsonListSchedule.isJsonArray()) {
                List<Schedule.Session> listSessions = new ArrayList<>();
                JsonArray jsonArrayListSchedule = jsonListSchedule.getAsJsonArray();
//                try {
                    for (int i = 0; i < jsonArrayListSchedule.size(); i++) {
                        int versionId = jsonArrayListSchedule.get(i).getAsJsonObject().get("version_id").getAsInt();
                        Boolean isVoice = jsonArrayListSchedule.get(i).getAsJsonObject().get("is_voice").getAsBoolean();

                        List<SessionTime> sessionsTime;
                        Type type = new TypeToken<List<SessionTime>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.registerTypeAdapter(SessionTime.class, new SessionTimeDeserializer());
                        Gson gson = gsonBuilder.create();
                        sessionsTime = gson.fromJson(jsonArrayListSchedule.get(i).getAsJsonObject().get("sessions").getAsJsonArray(), type);

                        listSessions.add(new Schedule.Session(sessionsTime, versionId, isVoice));
                    }
//                }

                // Check Schedule class for enum VersionId, if a version_id has not registered before, it will throw Array out of bound exception.
//                catch (ArrayIndexOutOfBoundsException e){
//                    Log.i("SCHEDULE JSON EXCEPTION", e.getMessage());
//                }
                schedule.setListSessions(listSessions);
            }
        }
        return schedule;
    }
}
