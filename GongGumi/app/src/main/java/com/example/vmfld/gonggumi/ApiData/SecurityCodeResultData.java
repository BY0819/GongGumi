package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityCodeResultData {
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("securitycode")
    @Expose
    private Integer securitycode;

    /**
     * No args constructor for use in serialization
     *
     */
    public SecurityCodeResultData() {
    }

    /**
     *
     * @param userid
     * @param securitycode
     */
    public SecurityCodeResultData(Integer userid, Integer securitycode) {
        super();
        this.userid = userid;
        this.securitycode = securitycode;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getSecuritycode() {
        return securitycode;
    }

    public void setSecuritycode(Integer securitycode) {
        this.securitycode = securitycode;
    }
}
