package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.UserData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EnteredRoomApi {
    String EnteredRoomUrl = "http://13.209.5.124:3000/";

    @GET("api/retrieve/joinroom")
    Call<EnteredRoomData> postUserIdforRoom (@Query("userid") Integer userid);
}
