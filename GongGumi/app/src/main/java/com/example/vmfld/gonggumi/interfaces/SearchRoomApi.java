package com.example.vmfld.gonggumi.interfaces;

import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.SearchRoomData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchRoomApi {
    String SearchRoomUrl = "http://13.209.5.124:3000/";

    @GET("api/search/room")
    Call<SearchRoomData> postNameforSearchRoom (@Query("roomname") String roomname);
}
