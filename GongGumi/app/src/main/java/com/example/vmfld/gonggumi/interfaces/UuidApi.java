package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.UserUuid;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UuidApi {

    String API_URL = "http://13.209.5.124:3000/api/add/user";

    @FormUrlEncoded
    @POST("/")
    Call<ResponseBody> getUserid(@Field("uuid") String uuid,
                                                    @Field("date") String date);


}
