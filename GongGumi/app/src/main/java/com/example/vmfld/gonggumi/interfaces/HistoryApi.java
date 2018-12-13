package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.HistoryData;
import com.example.vmfld.gonggumi.ApiData.PublicTabData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryApi {
    String PublicTabURL = "http://13.209.5.124:3000/";

    @GET("api/retrieve/usagelistOnlydetail")
    Call<HistoryData> postRoomid (@Query("roomid") Integer userid);
}
