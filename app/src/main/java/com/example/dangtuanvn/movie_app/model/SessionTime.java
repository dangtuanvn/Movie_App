package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;

/**
 * Created by dangtuanvn on 11/14/16.
 */

public class SessionTime implements Serializable {
    public enum StatusId{
        UNKNOWN,
        AVAILABLE,
        FULL,
        OVERTIME
    }

    private int sessionId;
    private String sessionTime;
    private String sessionLink;
    private StatusId statusId;
    private int roomId;
    private String roomName;
    private boolean useVoucher;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getSessionLink() {
        return sessionLink;
    }

    public void setSessionLink(String sessionLink) {
        this.sessionLink = sessionLink;
    }

    public StatusId getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusId statusId) {
        this.statusId = statusId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isUseVoucher() {
        return useVoucher;
    }

    public void setUseVoucher(boolean useVoucher) {
        this.useVoucher = useVoucher;
    }
}
