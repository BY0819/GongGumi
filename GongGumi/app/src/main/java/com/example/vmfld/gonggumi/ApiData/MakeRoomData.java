package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeRoomData {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("roomindex")
    @Expose
    private Integer roomindex;
    @SerializedName("securitycode")
    @Expose
    private Integer securitycode;

    /**
     * No args constructor for use in serialization
     *
     */
    public MakeRoomData() {
    }

    /**
     *
     * @param result
     * @param roomindex
     * @param securitycode
     */
    public MakeRoomData(String result, Integer roomindex, Integer securitycode) {
        super();
        this.result = result;
        this.roomindex = roomindex;
        this.securitycode = securitycode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getRoomindex() {
        return roomindex;
    }

    public void setRoomindex(Integer roomindex) {
        this.roomindex = roomindex;
    }

    public Integer getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(Integer securitycode) {
        this.securitycode = securitycode;
    }
}
