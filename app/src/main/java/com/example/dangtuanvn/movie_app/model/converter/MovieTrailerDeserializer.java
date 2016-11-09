package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.MovieTrailer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieTrailerDeserializer extends EasyDeserializer {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MovieTrailer movieTrailer = null;

        if (json != null && json.isJsonObject()) {
            movieTrailer = new MovieTrailer();
            JsonObject jsonObject = json.getAsJsonObject();
            movieTrailer.setV720p(getStringValue(jsonObject.get("v720p"), null));
            movieTrailer.setV480p(getStringValue(jsonObject.get("v480p"), null));
            movieTrailer.setV360p(getStringValue(jsonObject.get("v360p"), null));
        }
        return movieTrailer;
    }
}
