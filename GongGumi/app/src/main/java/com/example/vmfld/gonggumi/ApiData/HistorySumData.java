package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistorySumData {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("sum")
    @Expose
    private Integer sum;
    @SerializedName("detail")
    @Expose
    private List<HistorySumDetailData> detail = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public HistorySumData() {
        this.date = null;
        this.sum = null;
        this.detail = null;
    }

    /**
     *
     * @param detail
     * @param sum
     * @param date
     */
    public HistorySumData(String date, Integer sum, List<HistorySumDetailData> detail) {
        super();
        this.date = date;
        this.sum = sum;
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<HistorySumDetailData> getDetail() {
        return detail;
    }

    public void setDetail(List<HistorySumDetailData> detail) {
        this.detail = detail;
    }
}
