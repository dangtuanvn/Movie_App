package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/8/16.
 */

public class News implements Serializable {
    private int newsId;
    private String newsTitle;
    private String newsDescription;
    private String shortContent;
    private int filmId;
    private int pCinemaId;
    private List<Integer> listFilm;
    private String url;
    private String dateAdd;
    private String dateUpdate;
    private String imageFull;
    private String imageMedium;
    private String imageSmall;
    private String timeDifference;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getImageFull() {
        return imageFull;
    }

    public void setImageFull(String imageFull) {
        this.imageFull = imageFull;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public int getpCinemaId() {
        return pCinemaId;
    }

    public void setpCinemaId(int pCinemaId) {
        this.pCinemaId = pCinemaId;
    }

    public List<Integer> getListFilm() {
        return listFilm;
    }

    public void setListFilm(List<Integer> listFilm) {
        this.listFilm = listFilm;
    }

    public String getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(String timeDifference) {
        this.timeDifference = timeDifference;
    }
}
