package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.UserUuid;
import com.example.vmfld.gonggumi.UserUuidData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserIdApi {

    String Url = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/add/user")
    Call<UserUuidData> postUserData (@FieldMap HashMap<String, String> param);
}
