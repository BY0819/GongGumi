package com.example.vmfld.gonggumi.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vmfld.gonggumi.Adapter.EnteredRoomListAdapter;
import com.example.vmfld.gonggumi.Adapter.SearchRoomListAdapter;
import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.SearchRoomData;
import com.example.vmfld.gonggumi.ApiData.SearchRoomDataList;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.interfaces.EnteredRoomApi;
import com.example.vmfld.gonggumi.interfaces.SearchRoomApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchViewActivity extends AppCompatActivity {
        String roomname;
        ListView search_list;

        ArrayList<Integer> roomidList = new ArrayList<Integer>();

        public static final int REQUEST_CODE_FINISH_SEARCH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_list_toolbar);
        setSupportActionBar(toolbar); // 툴바 적용


        if(getIntent() != null){
            roomname  = getIntent().getStringExtra("roomname");

            Log.e("넘어온 데이터 확인 : ",roomname+"");
        }

        search_list = (ListView)findViewById(R.id.search_list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SearchRoomApi.SearchRoomUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final SearchRoomApi searchRoomApi  = retrofit.create(SearchRoomApi.class);

        Call<SearchRoomData> call =  searchRoomApi.postNameforSearchRoom(roomname);

        call.enqueue(new Callback<SearchRoomData>() {
            @Override
            public void onResponse(Call<SearchRoomData> call, Response<SearchRoomData> response) {
                SearchRoomData searchRoomData = response.body();
                List<SearchRoomDataList> searchRoomDataLists = response.body().data;


                Log.e("통신 값 :", response.code() + "");
                Log.e("SearchView Result : ", searchRoomData.getResult()+ "");

                if(searchRoomData.getResult().equals("success")){
                    for(int i = 0; i < searchRoomDataLists.size(); i++){
                        roomidList.add(i, response.body().getData().get(i).id);
                    }
                    Log.e("roomid 값 : ", roomidList+"");

                    search_list.setAdapter(new SearchRoomListAdapter(getApplicationContext(), searchRoomDataLists ));
                }else if(searchRoomData.getResult().equals("FAIL")){
                    Toast.makeText(SearchViewActivity.this, "검색하신 방이 존재하지 않습니다", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SearchRoomData> call, Throwable t) {
                Log.e("SearchView 에러발생  : ", t.getMessage());
                Toast.makeText(SearchViewActivity.this, "검색하신 방이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });

        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("position값: ", position+"");
                Intent intent = new Intent(SearchViewActivity.this, SearchRoomPopActivity.class);
                intent.putExtra("roomid", roomidList.get(position));
                startActivityForResult(intent, REQUEST_CODE_FINISH_SEARCH);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_CODE_FINISH_SEARCH  &&  resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
}
