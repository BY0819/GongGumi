package com.example.vmfld.gonggumi.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vmfld.gonggumi.MakeRoomActivity;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.UserUuid;
import com.example.vmfld.gonggumi.UserUuidData;
import com.example.vmfld.gonggumi.interfaces.UserIdApi;
import com.example.vmfld.gonggumi.interfaces.UuidApi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import okhttp3.ResponseBody;
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

        userIdApi.postUserData(input).enqueue(new Callback<UserUuidData>() {
            @Override
            public void onResponse(  Call<UserUuidData> call,   Response<UserUuidData> response) {
                if(response.isSuccessful())
                {
                    int responseCode = response.code();

                    Log.e("통신 값", responseCode+"");

                    UserUuidData uuid = response.body();
                  //  Log.e("받아오는 값", response.body().data() + " //// " +uuid.data()+"");


                     /* UserUuidData uuid = response.body();

                  if(uuid != null)
                    {
                        Log.d("CallBack", " response is " + response);
                        Log.e("postData end", "======================================");
                      //  Log.e("", uuid.getUserId());
                    }
                    else
                    {
                        Log.e("바디가 null임", "ㅜㅜ우우뭉ㄹ");
                    }*/

                }
                else
                {
                    Log.e("성공하지 못함", "ㅜㅜ울뭉루무");

                }
            }

            @Override
            public void onFailure(Call<UserUuidData> call, Throwable t) {
                Log.e("에러남",t.getMessage());
            }
        });




 /*
        retrofit = new Retrofit.Builder().baseUrl(UuidApi.API_URL).build();
        uuidApi = retrofit.create(UuidApi.class);

        Call<ResponseBody>userId = uuidApi.getUserid(userDeviceID, installDateString);

        userId.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.e("결과값 : ",response.body().string());
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.code(), response.body());
                    } else {
                        callback.onFailure(response.code());
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                    Log.e("에러남", "error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



        public void postFirst(HashMap<String, String> parameters, final RetroCallback callback) {
            apiService.postFirst(parameters).enqueue(new Callback<ResponseGet>() {
                @Override
                public void onResponse(Call<ResponseGet> call, Response<ResponseGet> response) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.code(), response.body());
                    } else {
                        callback.onFailure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseGet> call, Throwable t) {
                    callback.onError(t);
                }
            });
        }


        출처: https://kor45cw.tistory.com/5 [Developer's]

        retrofit = new Retrofit.Builder()
                .baseUrl(UuidApi.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        uuidApi = retrofit.create(UuidApi.class);

        uuidApi.getUserid(userDeviceID, installDateString).enqueue(new Callback<UserUuid>() {
            @Override
            public void onResponse(Call<UserUuid> call, Response<UserUuid> response) {
                UserUuid userUuid =  response.body();
                Log.e("값 : " , userDeviceID +installDateString );
                Log.e("Uuid", "onResponce : " + userUuid);

            }

            @Override
            public void onFailure(Call<UserUuid> call, Throwable t) {
                Toast.makeText(MainListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("에러남",t.getMessage());
            }
        });
        */
       /* HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://13.209.5.124:3000/api/add/user/");

        ArrayList<NameValuePair> nameValues =
                new ArrayList<NameValuePair>();

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            nameValues.add(new BasicNameValuePair(
                    "uuid", URLDecoder.decode(userDeviceID, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "date", URLDecoder.decode(installDateString, "UTF-8")));

            //HttpPost에 넘길 값을들 Set해주기
            post.setEntity(
                    new UrlEncodedFormEntity(
                            nameValues, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.e("Insert Log", ex.toString());
        }

        try {
            //설정한 URL을 실행시키기
            HttpResponse response = client.execute(post);
            //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
            Log.i("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


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
