package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinRoomData {
    @SerializedName("result")
    @Expose
    private String result= null;
    @SerializedName("roomid")
    @Expose
    private String roomid = null;
    @SerializedName("userreadflag")
    @Expose
    private String userreadflag= null;
    @SerializedName("message")
    @Expose
    private String message = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public JoinRoomData() {
    }

    /**
     *
     * @param roomid
     * @param result
     * @param userreadflag
     */
    public JoinRoomData(String result, String roomid, String userreadflag) {
        super();
        this.result = result;
        this.roomid = roomid;
        this.userreadflag = userreadflag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String result) {
        this.result = result;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getUserreadflag() {
        return userreadflag;
    }

    public void setUserreadflag(String userreadflag) {
        this.userreadflag = userreadflag;
    }

}
