package com.example.vmfld.gonggumi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vmfld.gonggumi.Datum;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.UserData;
import com.example.vmfld.gonggumi.interfaces.UserIdApi;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Created by Choi Bo-Yun 2018-11-14
 * */

public class MainListActivity extends AppCompatActivity  {

    String userDeviceID;
    String installDateString;
    Integer userIDInt;
    String userID;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_list_toolbar);
        setSupportActionBar(toolbar); // 툴바 적용
        getSupportActionBar().setDisplayShowTitleEnabled(false); // 커스텀 타이틀 사용으로 실제 타이틀 삭제


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_make_room);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainListActivity.this, MakeRoomActivity.class )); // activity 전환
            }
        });

        /** 사용자 ID 받아오기 **/
       userDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

       System.out.printf(userDeviceID);
       Log.e("아이디", userDeviceID);


       /** 사용자 앱 설치 날짜 받아오기 **/
        PackageManager packageManager =  getPackageManager();
        long installTimeInMilliseconds; // install time is conveniently provided in milliseconds

        Date installMDate = null;

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            installTimeInMilliseconds = packageInfo.firstInstallTime;
            installDateString  = new SimpleDateFormat("yyyy-mm-dd").format(installTimeInMilliseconds);
        }
        catch (PackageManager.NameNotFoundException e) {
            // an error occurred, so display the Unix epoch
            installMDate = new Date(0);
            installDateString = installMDate.toString();
        }

        Log.e("날짜", installDateString);

        /** Retrofit  Userid 받아오기 **/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserIdApi.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserIdApi userIdApi = retrofit.create(UserIdApi.class);


        HashMap<String, String> input = new HashMap<>();
        input.put("uuid", userDeviceID );
        input.put("date", installDateString);

        userIdApi.postUserData(input).enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(  Call<UserData> call,   Response<UserData> response) {

                UserData uuid = response.body();
                List<Datum> datumList = response.body().data;


                if(response.isSuccessful())
                {
                    int responseCode = response.code();

                    Log.e("통신 값", responseCode+"");

                    Log.e("받아오는 값",  uuid.getData()+"////"+ response.body().data +"////"+ response.body().result + " //// " +uuid.data +"///"+" ////" +uuid.result+"///" );

                    Log.e("유저아이디", new Gson().toJson(response.body().data));

                    Log.e("잘 들어왔나 확인", datumList.get(0).getUserid()+"");

                    userIDInt =  datumList.get(0).getUserid();

                  if(uuid != null)
                    {
                        Log.d("CallBack", " response is " + response);
                        Log.e("postData end", "======================================");

                    }
                    else
                    {
                        Log.e("바디가 null임", "ㅜㅜ우우뭉ㄹ");
                    }

              }
                else
                {
                    Log.e("성공하지 못함", "ㅜㅜ울뭉루무");

                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.e("에러남", t.getMessage());
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        else if(id == R.id.fab_make_room){

        }

        return super.onOptionsItemSelected(item);
    }





}
