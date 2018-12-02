package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.JoinRoomData;
import com.example.vmfld.gonggumi.ApiData.MakeRoomData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JoinRoomApi {
    String JoinRoomURL = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/join/room")
    Call<JoinRoomData> postMemberData (@FieldMap HashMap<String, Object> param);
}
