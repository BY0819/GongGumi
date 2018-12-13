package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsageData {
    @SerializedName("result")
    @Expose
    private String result;

    public UsageData() {
    }

    public UsageData(String result) {
        super();
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
