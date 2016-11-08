package com.example.dangtuanvn.movie_app.model.converter;

import android.util.Log;

import com.example.dangtuanvn.movie_app.model.News;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

            news.setImageFull(getStringValue(jsonObject.get("image_full"), null));
            news.setImageMedium(getStringValue(jsonObject.get("image2x"), null));
            news.setImageSmall(getStringValue(jsonObject.get("image"), null));

            JsonElement jsonListFilm = jsonObject.get("list_film");
            if (jsonListFilm != null && jsonListFilm.isJsonArray()) {
                JsonArray listFilmId = jsonListFilm.getAsJsonArray();
                news.setListFilm(getListInteger(listFilmId));
            }

            String dateUpdate = getStringValue(jsonObject.get("date_update"), null);
            if (dateUpdate != null) {
                news.setDateUpdate(dateUpdate);
                calculateTime(news, dateUpdate);
            }
        }
        return news;
    }

    public void calculateTime(News news, String dateUpdate){

        // Calculate difference in update time and current time
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = sdf.format(new Date());
            String newsTime = dateUpdate;
//                    Log.i("CURRENT TIME", currentTime + "");
//                    Log.i("NEWS TIME", newsTime + "");

            SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = ymd.parse(currentTime);
            Date date2 = ymd.parse(newsTime);

            int numberOfDays = (int) ((date1.getTime() - date2.getTime()) / (3600 * 24 * 1000));

            if (numberOfDays > 30) {
                news.setTimeDifference(numberOfDays / 30 + " months");
//                        Log.i("MONTH DIFFERENCE", numberOfDays / 30 + " months ago");
            } else if (numberOfDays > 0) {
                news.setTimeDifference(numberOfDays + " days");
//                        Log.i("DAY DIFFERENCE", numberOfDays + " days ago");
            } else {
                SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
                String[] splitedTime1 = currentTime.split("\\s+");
                String[] splitedTime2 = newsTime.split("\\s+");
                Date time1 = hms.parse(splitedTime1[1]);
                Date time2 = hms.parse(splitedTime2[1]);
                long minuteDifference = (time1.getTime() - time2.getTime()) / 1000 / 60;

                if (minuteDifference > 60) {
                    news.setTimeDifference(minuteDifference / 60 + " hours ago");
//                            Log.i("HOUR DIFFERENCE", minuteDifference / 60 + " hours");
                } else {
                    news.setTimeDifference(minuteDifference + " minutes ago");
//                            Log.i("MINUTE DIFFERENCE", minuteDifference + " minutes");
                }
            }
        } catch (ParseException e) {
            Log.i("TIME CALC EXCEPTION", e.getMessage());
        }
    }
}
