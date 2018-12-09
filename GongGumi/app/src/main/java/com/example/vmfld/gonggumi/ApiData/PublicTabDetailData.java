package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicTabDetailData {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("roomid")
    @Expose
    private Integer roomid;
    @SerializedName("usageprice")
    @Expose
    private Integer usageprice;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("publicpersonal")
    @Expose
    private String publicpersonal;
    @SerializedName("memo")
    @Expose
    private String memo;
    @SerializedName("deleteflag")
    @Expose
    private String deleteflag;

    /**
     * No args constructor for use in serialization
     *
     */
    public PublicTabDetailData() {
    }

    /**
     *
     * @param roomid
     * @param memo
     * @param userid
     * @param publicpersonal
     * @param usageprice
     * @param date
     * @param deleteflag
     */
    public PublicTabDetailData(Integer userid, Integer roomid, Integer usageprice, String date, String publicpersonal, String memo, String deleteflag) {
        super();
        this.userid = userid;
        this.roomid = roomid;
        this.usageprice = usageprice;
        this.date = date;
        this.publicpersonal = publicpersonal;
        this.memo = memo;
        this.deleteflag = deleteflag;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public Integer getUsageprice() {
        return usageprice;
    }

    public void setUsageprice(Integer usageprice) {
        this.usageprice = usageprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublicpersonal() {
        return publicpersonal;
    }

    public void setPublicpersonal(String publicpersonal) {
        this.publicpersonal = publicpersonal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }
}
