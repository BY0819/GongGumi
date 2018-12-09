package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublicTabData {
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("publicMoneyprice")
    @Expose
    private Integer publicMoneyprice;
    @SerializedName("detail")
    @Expose
    public List<PublicTabDetailData> detail = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public PublicTabData() {
    }

    /**
     *
     * @param result
     * @param detail
     * @param publicMoneyprice
     */
    public PublicTabData(String result, Integer publicMoneyprice, List<PublicTabDetailData> detail) {
        super();
        this.result = result;
        this.publicMoneyprice = publicMoneyprice;
        this.detail = detail;
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

    public List<PublicTabDetailData> getDetail() {
        return detail;
    }

    public void setDetail(List<PublicTabDetailData> detail) {
        this.detail = detail;
    }
}
