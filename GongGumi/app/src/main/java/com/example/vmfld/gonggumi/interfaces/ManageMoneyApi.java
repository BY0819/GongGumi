package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.MakeRoomData;
import com.example.vmfld.gonggumi.ApiData.UsageData;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ManageMoneyApi {
    String BaseUrl = "http://13.209.5.124:3000/";

    @FormUrlEncoded
    @POST("api/add/usage")
    Call<UsageData> postUsage  (@FieldMap HashMap<String, Object> param);
}
