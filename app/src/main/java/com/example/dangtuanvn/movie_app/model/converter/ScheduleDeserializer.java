package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.Schedule;
import com.example.dangtuanvn.movie_app.model.Session;
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
                List<String> listSessionInfo = new ArrayList<>();
                List<List<Session>> listSessions = new ArrayList<>();

                JsonArray jsonArrayListSchedule = jsonListSchedule.getAsJsonArray();

                for(int i = 0; i < jsonArrayListSchedule.size(); i++){
                    listSessionInfo.add(jsonArrayListSchedule.get(i).getAsJsonObject().get("version_id")
                            + "_" + jsonArrayListSchedule.get(i).getAsJsonObject().get("is_voice"));

                    List<Session> sessions;
                    Type type = new TypeToken<List<Session>>() {}.getType();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Session.class, new SessionDeserializer());
                    Gson gson = gsonBuilder.create();
                    sessions = gson.fromJson(jsonArrayListSchedule.get(i).getAsJsonObject().get("sessions").getAsJsonArray(), type);

                    listSessions.add(sessions);
                }
                schedule.setListSessionInfo(listSessionInfo);
                schedule.setListSessions(listSessions);
            }
        }
        return schedule;
    }
}
