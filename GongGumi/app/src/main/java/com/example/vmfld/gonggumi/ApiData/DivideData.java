package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.SerializedName;

public class DivideData {

    //보내야 하는 값
    private String name;
    private int getpermoney;
    private int personalused;

    //계산을 위해 필요한 값
    private int memnum=0;
    private int personaltotalused;

    /** 서버 통신 데이터  **/
    @SerializedName("result")
    private String result;
    @SerializedName("sumUsage")
    private Integer publicUsed;
    @SerializedName("publicMoney")
    private Integer totalLeft;
    @SerializedName("personalSum")
    private DividePersonalSumData[] dividePSDList = new DividePersonalSumData[memnum];
    private int i = 0;

    public DivideData() {
    }

    public DivideData(String result, Integer publicUsed, Integer totalLeft, DividePersonalSumData[] dividePSDList, int i) {
        this.result = result;
        this.publicUsed = publicUsed;
        this.totalLeft = totalLeft;
        this.memnum = dividePSDList.length;
        this.dividePSDList = dividePSDList;
        this.personalused = dividePSDList[i].getPersonalused();
        this.name = dividePSDList[i].getName();
        this.i = i;

        setName();
        setPersonaltotalused();
    }

    public String getResult(){
        return result;
    }

    public void setName(){
        name = dividePSDList[i].getName();
    }

    public void setPersonaltotalused(){
        for(int j = 0; j<memnum; j++){
            personaltotalused = personaltotalused + dividePSDList[j].getPersonalused();
        }
    }

    public String getstrGetpermoney(){
        if(getpermoney == 0){
            getpermoney = ((totalLeft + personaltotalused) / memnum) - personalused;
        }
        String str_getpermoney = makeComma(getpermoney);

        return str_getpermoney;
    }

    public int getPublicUsed(){
        return publicUsed;
    }
    public String getstrPublicused(){
        String str_publicused = makeComma(publicUsed);

        return str_publicused;
    }

    public int getPersonalused(){
        return personalused;
    }

    public String getstrPersonalused() {
        String str_personalused = makeComma(personalused);

        return str_personalused;
    }

    public String getName() {
        return name;
    }

    public int getMemnum() {
        this.memnum = dividePSDList.length;
        return memnum;
    }

    public DividePersonalSumData[] getDPSData() { return dividePSDList; }

    public int getTotalleft(){
        return totalLeft;
    }

    public String getstrTotalLeft(){
        String strTotalLeft = makeComma(totalLeft);
        return strTotalLeft;
    }



    private String makeComma(int num){
        String str_num =Integer.toString(num);

        if(num>999){
            if(num%1000 == 0){
                str_num = "," + "000";
            }
            else{
                str_num = "," +  Integer.toString(num % 1000);
            }
            return makeComma(num/1000) + str_num;
        }
        else{
            return Integer.toString(num);
        }

    }

}
