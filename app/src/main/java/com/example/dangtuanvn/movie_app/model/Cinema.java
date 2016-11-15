package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;

/**
 * Created by dangtuanvn on 11/8/16.
 */

public class Cinema implements Serializable {
    private int locationId;
    private int pCinemaId;
    private String pCinemaName;
    private int cinemaId;
    private String cinemaName;
    private String cinemaNameS1;
    private String cinemaNameS2;
    private String cinemaAddress;
    private double latitude;
    private double longitude;
    private String cinemaLogo;
    private String cinemaPhone;
    private String logoUrl;
    private String listPrice;
    private String cinemaImage;
    private float distance;

    public int getLocationId() {
        return locationId;
    }

    public float getDistance(){return distance;}

    public void setDistance(float distance){this.distance=distance;}

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getpCinemaId() {
        return pCinemaId;
    }

    public void setpCinemaId(int pCinemaId) {
        this.pCinemaId = pCinemaId;
    }

    public String getpCinemaName() {
        return pCinemaName;
    }

    public void setpCinemaName(String pCinemaName) {
        this.pCinemaName = pCinemaName;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaNameS1() {
        return cinemaNameS1;
    }

    public void setCinemaNameS1(String cinemaNameS1) {
        this.cinemaNameS1 = cinemaNameS1;
    }

    public String getCinemaNameS2() {
        return cinemaNameS2;
    }

    public void setCinemaNameS2(String cinemaNameS2) {
        this.cinemaNameS2 = cinemaNameS2;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public String getCinemaLogo() {
        return cinemaLogo;
    }

    public void setCinemaLogo(String cinemaLogo) {
        this.cinemaLogo = cinemaLogo;
    }

    public String getCinemaPhone() {
        return cinemaPhone;
    }

    public void setCinemaPhone(String cinemaPhone) {
        this.cinemaPhone = cinemaPhone;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public String getCinemaImage() {
        return cinemaImage;
    }

    public void setCinemaImage(String cinemaImage) {
        this.cinemaImage = cinemaImage;
    }
}
