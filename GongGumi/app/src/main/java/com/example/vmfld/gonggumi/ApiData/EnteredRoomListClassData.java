package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EnteredRoomListClassData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("joinppl")
    @Expose
    private Integer joinppl;
    @SerializedName("regdate")
    @Expose
    private String regdate;
    @SerializedName("personalprice")
    @Expose
    private Integer personalprice;
    @SerializedName("carryoverprice")
    @Expose
    private Integer carryoverprice;
    @SerializedName("securitycode")
    @Expose
    private Integer securitycode;
    @SerializedName("deleteflag")
    @Expose
    private String deleteflag;
    @SerializedName("roomname")
    @Expose
    private String roomname;

    /**
     * No args constructor for use in serialization
     *
     */
    public EnteredRoomListClassData() {
    }

    /**
     *
     * @param id
     * @param roomname
     * @param personalprice
     * @param regdate
     * @param name
     * @param securitycode
     * @param carryoverprice
     * @param deleteflag
     * @param joinppl
     */
    public EnteredRoomListClassData(Integer id, String name, Integer joinppl, String regdate, Integer personalprice, Integer carryoverprice, Integer securitycode, String deleteflag, String roomname) {
        super();
        this.id = id;
        this.name = name;
        this.joinppl = joinppl;
        this.regdate = regdate;
        this.personalprice = personalprice;
        this.carryoverprice = carryoverprice;
        this.securitycode = securitycode;
        this.deleteflag = deleteflag;
        this.roomname = roomname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJoinppl() {
        return joinppl;
    }

    public void setJoinppl(Integer joinppl) {
        this.joinppl = joinppl;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public Integer getPersonalprice() {
        return personalprice;
    }

    public void setPersonalprice(Integer personalprice) {
        this.personalprice = personalprice;
    }

    public Integer getCarryoverprice() {
        return carryoverprice;
    }

    public void setCarryoverprice(Integer carryoverprice) {
        this.carryoverprice = carryoverprice;
    }

    public Integer getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(Integer securitycode) {
        this.securitycode = securitycode;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
