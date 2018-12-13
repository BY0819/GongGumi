package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryData {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("publicMoneyprice")
    @Expose
    private Integer publicMoneyprice;
    @SerializedName("sumData")
    @Expose
    public List<HistorySumData> sumData = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public HistoryData() {
    }

    /**
     *
     * @param result
     * @param publicMoneyprice
     * @param sumData
     */
    public HistoryData(String result, Integer publicMoneyprice, List<HistorySumData> sumData) {
        super();
        this.result = result;
        this.publicMoneyprice = publicMoneyprice;
        this.sumData = sumData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getPublicMoneyprice() {
        return publicMoneyprice;
    }

    public void setPublicMoneyprice(Integer publicMoneyprice) {
        this.publicMoneyprice = publicMoneyprice;
    }

    public List<HistorySumData> getSumData() {
        return sumData;
    }

    public void setSumData(List<HistorySumData> sumData) {
        this.sumData = sumData;
    }


}
