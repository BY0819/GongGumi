package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EnteredRoomData {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("data")
    @Expose
    public List<EnteredRoomListClassData> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public EnteredRoomData() {
    }

    /**
     *
     * @param result
     * @param data
     */
    public EnteredRoomData(String result, List<EnteredRoomListClassData> data) {
        super();
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<EnteredRoomListClassData> getData() {
        return data;
    }

    public void setData(List<EnteredRoomListClassData> data) {
        this.data = data;
    }

}
