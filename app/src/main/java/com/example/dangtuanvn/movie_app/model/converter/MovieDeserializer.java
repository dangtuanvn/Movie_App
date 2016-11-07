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

public class MovieDeserializer extends EasyDeserializer<Movie>  {
    @Override
    public Movie deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Movie movie = null;
            if (json != null && json.isJsonObject()) {
                movie = new Movie();
                JsonObject jsonObject = json.getAsJsonObject();
                movie.setFilmId(getStringValue(jsonObject.get("film_id"), null));
                movie.setFilmName(getStringValue(jsonObject.get("film_name"), null));
                movie.setFilmNameVn(getStringValue(jsonObject.get("film_name_vb"), null));
                movie.setFilmNameEn(getStringValue(jsonObject.get("film_name_en"), null));
                movie.setDuration(getStringValue(jsonObject.get("film_duration"), null));
                movie.setPublishDate(getStringValue(jsonObject.get("publish_date"), null));
                movie.setPg_rating(getStringValue(jsonObject.get("pg_rating"), null));
                movie.setPosterUrl(getStringValue(jsonObject.get("poster_url"), null));
                movie.setPosterThumb(getStringValue(jsonObject.get("poster_thumb"), null));
                movie.setPosterLandscape(getStringValue(jsonObject.get("poster_landscape"), null));


    //            JsonElement authorJsonElement = jsonObject.get("from");
    //            if (authorJsonElement != null && authorJsonElement.isJsonObject()) {
    //            facebookImage.setFromUserName(getStringValue(authorJsonElement.getAsJsonObject().get("name"), null));
    //            }

                JsonElement jsonListLocationId = jsonObject.get("list_location_id");
                        if (jsonListLocationId.isJsonArray()) {
                        JsonArray listLocationId = jsonListLocationId.getAsJsonArray();
                        movie.setListLocationId(getListLocationId(listLocationId));
                    }
                }
            return movie;
        }

    private List<Integer> getListLocationId(JsonArray list){
        List<Integer> listLocation = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            listLocation.add(Integer.parseInt(list.get(i).toString()));
        }
        return listLocation;
    }
}
