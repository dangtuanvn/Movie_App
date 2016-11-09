package com.example.dangtuanvn.movie_app.model.converter;

/**
 * Created by dangtuanvn on 11/7/16.
 */

import com.example.dangtuanvn.movie_app.model.Movie;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MovieDeserializer extends EasyDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Movie movie = null;
            if (json != null && json.isJsonObject()) {
                movie = new Movie();
                JsonObject jsonObject = json.getAsJsonObject();
                movie.setFilmId(getIntValue(jsonObject.get("film_id"), 0));
                movie.setFilmName(getStringValue(jsonObject.get("film_name"), null));
                movie.setFilmNameVn(getStringValue(jsonObject.get("film_name_vb"), null));
                movie.setFilmNameEn(getStringValue(jsonObject.get("film_name_en"), null));
                movie.setDuration(getIntValue(jsonObject.get("film_duration"), 0));
                movie.setPublishDate(getStringValue(jsonObject.get("publish_date"), null));
                movie.setAvgPoint(getDoubleValue(jsonObject.get("avg_point"), 0));
                movie.setImdbPoint(getDoubleValue(jsonObject.get("imdb_point"), 0));
                movie.setPgRating(getStringValue(jsonObject.get("pg_rating"), null));
                movie.setPosterUrl(getStringValue(jsonObject.get("poster_url"), null));
                movie.setPosterThumb(getStringValue(jsonObject.get("poster_thumb"), null));
                movie.setPosterLandscape(getStringValue(jsonObject.get("poster_landscape"), null));

    //            JsonElement authorJsonElement = jsonObject.get("from");
    //            if (authorJsonElement != null && authorJsonElement.isJsonObject()) {
    //            facebookImage.setFromUserName(getStringValue(authorJsonElement.getAsJsonObject().get("name"), null));
    //            }

                JsonElement jsonListLocationId = jsonObject.get("list_location_id");
                if (jsonListLocationId != null && jsonListLocationId.isJsonArray()) {
                        JsonArray listLocationId = jsonListLocationId.getAsJsonArray();
                        movie.setListLocationId(getListInteger(listLocationId));
                    }
                }
            return movie;
        }
}
