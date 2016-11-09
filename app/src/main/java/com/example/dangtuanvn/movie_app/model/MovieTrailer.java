package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;

/**
 * Created by dangtuanvn on 11/9/16.
 */

public class MovieTrailer implements Serializable {
    private String v720p;
    private String v480p;
    private String v360p;

    public String getV720p() {
        return v720p;
    }

    public void setV720p(String v720p) {
        this.v720p = v720p;
    }

    public String getV480p() {
        return v480p;
    }

    public void setV480p(String v480p) {
        this.v480p = v480p;
    }

    public String getV360p() {
        return v360p;
    }

    public void setV360p(String v360p) {
        this.v360p = v360p;
    }
}
