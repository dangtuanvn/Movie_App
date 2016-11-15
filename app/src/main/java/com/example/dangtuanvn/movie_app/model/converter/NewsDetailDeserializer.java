package com.example.dangtuanvn.movie_app.model.converter;

import com.example.dangtuanvn.movie_app.model.NewsDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class NewsDetailDeserializer extends EasyDeserializer<NewsDetail> {
    @Override
    public NewsDetail deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        NewsDetail newsDetail = null;
        if (json != null && json.isJsonObject()) {
            newsDetail = new NewsDetail();
            JsonObject jsonObject = json.getAsJsonObject();
            newsDetail.setNewsId(getIntValue(jsonObject.get("news_id"), 0));
            newsDetail.setNewsTitle(getStringValue(jsonObject.get("news_title"), null));
            newsDetail.setNewsDescription(getStringValue(jsonObject.get("news_description"), null));
            newsDetail.setContent(getStringValue(jsonObject.get("content"), null));
            newsDetail.setFilmId(getIntValue(jsonObject.get("film_id"), 0));
            newsDetail.setUrl(getStringValue(jsonObject.get("url"), null));
            newsDetail.setDateAdd(getStringValue(jsonObject.get("date_add"), null));
            newsDetail.setDateUpdate(getStringValue(jsonObject.get("date_update"), null));
            newsDetail.setBannerLarge(getStringValue(jsonObject.get("banner_large"), null));

            JsonElement jsonListRelated = jsonObject.get("list_related");
            if (jsonListRelated != null && jsonListRelated.isJsonArray()) {
                JsonArray listNewsRelated = jsonListRelated.getAsJsonArray();
                List<Integer> listNewsId = new ArrayList<>();
                for(int i = 0; i < listNewsRelated.size(); i++){
                    listNewsId.add(listNewsRelated.get(i).getAsJsonObject().get("news_id").getAsInt());
                }
                newsDetail.setRelatedNewsList(listNewsId);
            }
        }
        return newsDetail;
    }
}
