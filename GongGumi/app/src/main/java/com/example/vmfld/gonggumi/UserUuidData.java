package com.example.vmfld.gonggumi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserUuidData {

    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("result")
    @Expose
    public String result;
    @SerializedName("data")
    @Expose
    public List<Datum> data = new ArrayList<>();

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Datum> getData() {
        return data;
    }

    public  class Datum{
        @SerializedName("userid")
        @Expose
        public Integer userid;
    }

    public UserUuidData(String uuid, String date) {
        this.uuid = uuid;
        this.date = date;
    }


    /*@SerializedName("userid")
    @Expose
    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
*/
}
