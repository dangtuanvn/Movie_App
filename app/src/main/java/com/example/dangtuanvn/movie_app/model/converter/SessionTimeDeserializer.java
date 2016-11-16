package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.SessionTime;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class SessionTimeDeserializer extends EasyDeserializer<SessionTime> {

    @Override
    public SessionTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SessionTime sessionTime = null;
        if (json != null && json.isJsonObject()) {
            sessionTime = new SessionTime();
            JsonObject jsonObject = json.getAsJsonObject();
            sessionTime.setSessionId(getIntValue(jsonObject.get("session_id"), 0));
            sessionTime.setSessionTime(getStringValue(jsonObject.get("session_time"), null));
            sessionTime.setSessionLink(getStringValue(jsonObject.get("session_link"), null));
            sessionTime.setStatusId(SessionTime.StatusId.values()[getIntValue(jsonObject.get("status_id"), 0)]);
            sessionTime.setRoomId(getIntValue(jsonObject.get("room_id"), 0));
            sessionTime.setRoomName(getStringValue(jsonObject.get("room_name"), null));
        }
        return sessionTime;
    }
}
