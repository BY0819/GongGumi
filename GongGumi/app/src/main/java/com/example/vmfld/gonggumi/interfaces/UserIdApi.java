package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.UserData;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserIdApi {

    String Url = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/add/user")
    Call<UserData> postUserData (@FieldMap HashMap<String , String> param);
}
