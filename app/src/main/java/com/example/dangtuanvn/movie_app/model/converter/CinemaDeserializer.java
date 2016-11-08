package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.Cinema;
import com.example.dangtuanvn.movie_app.model.Movie;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by dangtuanvn on 11/8/16.
 */

public class CinemaDeserializer extends EasyDeserializer<Cinema>   {
    @Override
    public Cinema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Cinema cinema = null;
        if (json != null && json.isJsonObject()) {
            cinema = new Cinema();
            JsonObject jsonObject = json.getAsJsonObject();
            cinema.setLocationId(getIntValue(jsonObject.get("location_id"), 0));
            cinema.setpCinemaId(getIntValue(jsonObject.get("p_cinema_id"), 0));
            cinema.setpCinemaName(getStringValue(jsonObject.get("p_cinema_name"), null));
            cinema.setCinemaId(getIntValue(jsonObject.get("cinema_id"), 0));
            cinema.setCinemaName(getStringValue(jsonObject.get("cinema_name"), null));
            cinema.setCinemaNameS1(getStringValue(jsonObject.get("cinema_name_s1"), null));
            cinema.setCinemaNameS2(getStringValue(jsonObject.get("cinema_name_s2"), null));
            cinema.setCinemaAddress(getStringValue(jsonObject.get("cinema_address"), null));
            cinema.setLatitude(getDoubleValue(jsonObject.get("cinema_latitude"), 0));
            cinema.setLongtitude(getDoubleValue(jsonObject.get("cinema_longtitude"), 0));
            cinema.setCinemaLogo(getStringValue(jsonObject.get("cinema_logo"), null));
            cinema.setCinemaPhone(getStringValue(jsonObject.get("cinema_phone"), null));
            cinema.setCinemaImage(getStringValue(jsonObject.get("cinema_image"), null));
            cinema.setLogoUrl(getStringValue(jsonObject.get("logo_url"), null));
            cinema.setListPrice(getStringValue(jsonObject.get("list_price"), null));
        }
        return cinema;
    }
}
