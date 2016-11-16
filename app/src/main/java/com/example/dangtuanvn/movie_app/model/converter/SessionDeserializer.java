package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.Session;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class SessionDeserializer extends EasyDeserializer<Session> {

    @Override
    public Session deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Session session = null;
        if (json != null && json.isJsonObject()) {
            session = new Session();
            JsonObject jsonObject = json.getAsJsonObject();
            session.setSessionId(getIntValue(jsonObject.get("session_id"), 0));
            session.setSessionTime(getStringValue(jsonObject.get("session_time"), null));
            session.setSessionLink(getStringValue(jsonObject.get("session_link"), null));
            session.setStatusId(getIntValue(jsonObject.get("status_id"), 0));
            session.setRoomId(getIntValue(jsonObject.get("room_id"), 0));
            session.setRoomName(getStringValue(jsonObject.get("room_name"), null));
        }
        return session;
    }
}
