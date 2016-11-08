package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.News;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/8/16.
 */

public class NewsDeserializer extends EasyDeserializer<News> {
    @Override
    public News deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        News news = null;
        if (json != null && json.isJsonObject()) {
            news = new News();
            JsonObject jsonObject = json.getAsJsonObject();
            news.setNewsId(getIntValue(jsonObject.get("news_id"), 0));
            news.setNewsTitle(getStringValue(jsonObject.get("news_title"), null));
            news.setNewsDescription(getStringValue(jsonObject.get("news_description"), null));
            news.setShortContent(getStringValue(jsonObject.get("short_content"), null));
            news.setFilmId(getIntValue(jsonObject.get("film_id"), 0));
            news.setpCinemaId(getIntValue(jsonObject.get("p_cinema_id"), 0));
            news.setUrl(getStringValue(jsonObject.get("url"), null));
            news.setDateAdd(getStringValue(jsonObject.get("date_add"), null));
            news.setDateUpdate(getStringValue(jsonObject.get("date_update"), null));
            news.setImageFull(getStringValue(jsonObject.get("image_full"), null));
            news.setImageMedium(getStringValue(jsonObject.get("image2x"), null));
            news.setImageSmall(getStringValue(jsonObject.get("image"), null));

            JsonElement jsonListFilm = jsonObject.get("list_film");
            if (jsonListFilm != null && jsonListFilm.isJsonArray()) {
                JsonArray listFilmId = jsonListFilm.getAsJsonArray();
                news.setListFilm(getListInteger(listFilmId));
            }
        }
        return news;
    }

}
