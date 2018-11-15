package com.example.vmfld.gonggumi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserUuid {
    @SerializedName("result")
    @Expose
    public String uuid;
    @SerializedName("result")
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

    public UserUuid(String uuid, String date) {
        this.uuid = uuid;
        this.date = date;
    }
}
/* private  String Uuid;

    public UserUuid(String uuid) {
        Uuid = uuid;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }


* @SerializedName("result")
    @Expose
    public String uuid;
    @SerializedName("result")
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

    public User(String uuid, String date) {
        this.uuid = uuid;
        this.date = date;
    }*/