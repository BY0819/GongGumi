package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.MakeRoomData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MakeRoomApi {
    String BaseUrl = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/add/room")
    Call<MakeRoomData> postUserData (@FieldMap HashMap<String, Object> param);
}
