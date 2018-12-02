package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SearchRoomData {
    @SerializedName("result")
    @Expose
    private String result = null ;
    @SerializedName("message")
    @Expose
    private String message = null;
    @SerializedName("data")
    @Expose
    public List<SearchRoomDataList> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchRoomData() {
        this.result = null;
        this.message = null;
        this.data = null;
    }

    /**
     * @param result
     * @param data
     */
    public SearchRoomData(String result, List<SearchRoomDataList> data) {
        super();
        this.result = result;
        this.message = null;
        this.data = data;
    }
    public SearchRoomData(String result, String message) {
        super();
        this.result = result;
        this.message = message;
        this.data = null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String result) {
        this.result = result;
    }

    public List<SearchRoomDataList> getData() {
        return data;
    }

    public void setData(List<SearchRoomDataList> data) {
        this.data = data;
    }
}
