package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieDetail implements Serializable {
    private int filmId;
    private String filmName;
    private String filmNameEn;
    private String filmNameVn;
    private int duration;
    private String descriptionMobile;
    private String filmUrl;
    private String filmVersion;
    private String publishDate;
    private String pgRating;
    private String mediaId;
    private int statusId;
    private String posterLandscape;
    private double avgPoint;
    private double imdbPoint;
    private List<String> listActors;
    private String directorName;

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getFilmNameEn() {
        return filmNameEn;
    }

    public void setFilmNameEn(String filmNameEn) {
        this.filmNameEn = filmNameEn;
    }

    public String getFilmNameVn() {
        return filmNameVn;
    }

    public void setFilmNameVn(String filmNameVn) {
        this.filmNameVn = filmNameVn;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescriptionMobile() {
        return descriptionMobile;
    }

    public void setDescriptionMobile(String descriptionMobile) {
        this.descriptionMobile = descriptionMobile;
    }

    public String getFilmUrl() {
        return filmUrl;
    }

    public void setFilmUrl(String filmUrl) {
        this.filmUrl = filmUrl;
    }

    public String getFilmVersion() {
        return filmVersion;
    }

    public void setFilmVersion(String filmVersion) {
        this.filmVersion = filmVersion;
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

    public void setPgRating(String pgRating) {
        this.pgRating = pgRating;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getPosterLandscape() {
        return posterLandscape;
    }

    public void setPosterLandscape(String posterLandscape) {
        this.posterLandscape = posterLandscape;
    }

    public double getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(double avgPoint) {
        this.avgPoint = avgPoint;
    }

    public double getImdbPoint() {
        return imdbPoint;
    }

    public void setImdbPoint(double imdbPoint) {
        this.imdbPoint = imdbPoint;
    }

    public List<String> getListActors() {
        return listActors;
    }

    public void setListActors(List<String> listActors) {
        this.listActors = listActors;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
}
