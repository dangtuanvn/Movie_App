package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class NewsDetail implements Serializable {
    private int newsId;
    private String newsTitle;
    private String newsDescription;
    private String url;
    private String content;
    private String bannerLarge;
    private String dateAdd;
    private String dateUpdate;
    private List<Integer> relatedNewsList;
    private int filmId;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBannerLarge() {
        return bannerLarge;
    }

    public void setBannerLarge(String bannerLarge) {
        this.bannerLarge = bannerLarge;
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

    public List<Integer> getRelatedNewsList() {
        return relatedNewsList;
    }

    public void setRelatedNewsList(List<Integer> relatedNewsList) {
        this.relatedNewsList = relatedNewsList;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
}
