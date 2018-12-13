package com.example.vmfld.gonggumi.ApiData;

public class PublicInsertData {
    private String date;
    private String memo1;
    private Integer price1;
    private String memo2;
    private Integer price2;

    public PublicInsertData() {
        this.date = null;
        this.memo1 = null;
        this.price1 = null;
        this.memo2 = null;
        this.price2 = null;
    }

    public PublicInsertData(String date, String memo1, Integer price1, String memo2, Integer price2) {
        this.date = date;
        this.memo1 = memo1;
        this.price1 = price1;
        this.memo2 = memo2;
        this.price2 = price2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo1() {
        return memo1;
    }

    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    public Integer getPrice1() {
        return price1;
    }

    public void setPrice1(Integer price1) {
        this.price1 = price1;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public Integer getPrice2() {
        return price2;
    }

    public void setPrice2(Integer price2) {
        this.price2 = price2;
    }
}
