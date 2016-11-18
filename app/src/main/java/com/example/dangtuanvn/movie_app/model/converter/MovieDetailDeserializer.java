package com.example.dangtuanvn.movie_app.model.converter;

import android.util.Log;

import com.example.dangtuanvn.movie_app.model.MovieDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieDetailDeserializer extends EasyDeserializer<MovieDetail> {
    @Override
    public MovieDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MovieDetail movieDetail = null;
        if (json != null && json.isJsonObject()) {
            movieDetail = new MovieDetail();
            JsonObject jsonObject = json.getAsJsonObject();
            movieDetail.setFilmId(getIntValue(jsonObject.get("film_id"), 0));
            movieDetail.setFilmName(getStringValue(jsonObject.get("film_name"), null));
            movieDetail.setFilmNameEn(getStringValue(jsonObject.get("film_name_en"), null));
            movieDetail.setFilmNameVn(getStringValue(jsonObject.get("film_name_vn"), null));
            movieDetail.setDuration(getIntValue(jsonObject.get("film_duration"), 0));
            movieDetail.setDescriptionMobile(getStringValue(jsonObject.get("film_description_mobile"), null));
            movieDetail.setFilmUrl(getStringValue(jsonObject.get("film_url"), null));
            movieDetail.setFilmVersion(getStringValue(jsonObject.get("film_version"), null));
            movieDetail.setPublishDate(getStringValue(jsonObject.get("publish_date"), null));
            movieDetail.setPgRating(getStringValue(jsonObject.get("pg_rating"), null));
            movieDetail.setPosterLandscape(getStringValue(jsonObject.get("poster_landscape"), null));
            movieDetail.setAvgPoint(getDoubleValue(jsonObject.get("avg_point"), 0));
            movieDetail.setImdbPoint(getDoubleValue(jsonObject.get("imdb_point"), 0));

            JsonElement jsonListActors = jsonObject.get("list_actor");
            if (jsonListActors != null && jsonListActors.isJsonArray()) {
                JsonArray listActors = jsonListActors.getAsJsonArray();
                List<String> listActorNames = new ArrayList<>();
                for(int i = 0; i < listActors.size(); i++){
                    if(listActors.get(i).getAsJsonObject().get("char_name").getAsString().equals("Đạo Diễn")){
                        movieDetail.setDirectorName(listActors.get(i).getAsJsonObject().get("artist_name").getAsString());
                    }
                    else {
                        listActorNames.add(listActors.get(i).getAsJsonObject().get("artist_name").getAsString());
                    }
//                    Log.i("ROLE NAME", listActors.get(i).getAsJsonObject().get("char_name").getAsString());
                }
                movieDetail.setListActors(listActorNames);
            }
        }
        return movieDetail;
    }
}
