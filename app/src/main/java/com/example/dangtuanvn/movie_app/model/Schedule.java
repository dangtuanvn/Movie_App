package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class Schedule implements Serializable {
    /* version_id
        1 - imax
        2 - 2d
        3 - 3d
        5 - gold class
        6 - L'amour

        is_voice
        0: digital
        1: lồng tiếng

        status_id
        1: available
        2: full?
        3: past time
    */

    public enum VersionId {
        UNKNOWN1,
        iMax,
        twoD,
        threeD,
        UNKNOWN2,
        goldClass,
        lAmour

//        iMax(1),
//        twoD(2),
//        threeD(3),
//        goldClass(5),
//        lAmour(6);
//
//        private int number;
//
//        VersionId(int number) {
//            this.number = number;
//        }
//
//        public int getNumber() {
//            return number;
//        }
    }

    private int pCinemaId;
    private String pCinemaName;
    private int cinemaId;
    private String cinemaName;
    private String cinemaNameS1;
    private String cinemaNameS2;
    private String cinemaAddress;
    private double latitude;
    private double longitude;
    private String listPrice;
    private int maxSeat;
    private String cinemaLogo;
    private String lastSync;
    private boolean useVoucher;
    private List<Session> listSessions;

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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getListPrice() {
        return listPrice;
    }

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public int getMaxSeat() {
        return maxSeat;
    }

    public void setMaxSeat(int maxSeat) {
        this.maxSeat = maxSeat;
    }

    public String getCinemaLogo() {
        return cinemaLogo;
    }

    public void setCinemaLogo(String cinemaLogo) {
        this.cinemaLogo = cinemaLogo;
    }

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public boolean isUseVoucher() {
        return useVoucher;
    }

    public void setUseVoucher(boolean useVoucher) {
        this.useVoucher = useVoucher;
    }

    public List<Session> getListSessions() {
        return listSessions;
    }

    public void setListSessions(List<Session> listSessions) {
        this.listSessions = listSessions;
    }

    public static class Session{
        private List<SessionTime> listTime;
        private VersionId movieVersion;
        private boolean isVoice;
        public Session(List<SessionTime> listTime, VersionId movieVersion, boolean isVoice){
            this.isVoice = isVoice;
            this.listTime = listTime;
            this.movieVersion = movieVersion;
        }

        public String getVersion(){
            String version = null;
            switch(movieVersion) {
                case iMax:
                    version = "IMAX";
                    break;

                case twoD:
                    version = "2D";
                    break;

                case threeD:
                    version = "3D";
                    break;

                case goldClass:
                    version = "GOLD CLASS";
                    break;

                case lAmour:
                    version = "L'AMOUR";
                    break;
            }

            if(isVoice){
                version += " LỒNG TIẾNG";
            }
            else{
                version += " DIGITAL";
            }
            return version;
        }

        public List<SessionTime> getListTime(){
            return listTime;
        }
    }
}
