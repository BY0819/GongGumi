package com.example.vmfld.gonggumi.interfaces;


import com.example.vmfld.gonggumi.ApiData.SecurityCodeData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetCodeApi {

    String BaseUrl = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/find/securitycode")
    Call<SecurityCodeData> postRoomId(@Field("roomid") Integer roomid);
}
