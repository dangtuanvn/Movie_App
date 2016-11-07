package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class Movie implements Serializable {
    private String filmId;
    private String filmName;
    private String filmNameVn;
    private String filmNameEn;
    private String duration;
    private String publishDate;
    private String pgRating;
    private String posterUrl;
    private String posterThumb;
    private String posterLandscape;
    private float avgPoint;
    private List<Integer> listLocationId;

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmNameVn() {
        return filmNameVn;
    }

    public void setFilmNameVn(String filmNameVn) {
        this.filmNameVn = filmNameVn;
    }

    public String getFilmNameEn() {
        return filmNameEn;
    }

    public void setFilmNameEn(String filmNameEn) {
        this.filmNameEn = filmNameEn;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPgRating() {
        return pgRating;
    }

    public void setPg_rating(String pg_rating) {
        this.pgRating = pg_rating;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterThumb() {
        return posterThumb;
    }

    public void setPosterThumb(String posterThumb) {
        this.posterThumb = posterThumb;
    }

    public String getPosterLandscape() {
        return posterLandscape;
    }

    public void setPosterLandscape(String posterLandscape) {
        this.posterLandscape = posterLandscape;
    }

    public float getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(float avgPoint) {
        this.avgPoint = avgPoint;
    }

    public List<Integer> getListLocationId() {
        return listLocationId;
    }

    public void setListLocationId(List<Integer> listLocationId) {
        this.listLocationId = listLocationId;
    }
}
