package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.DivideData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DivideApi {
    String DivideUrl = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/devide/money")
    Call<DivideData> getDivideData(@Field("roomid") Integer roomid);

}
