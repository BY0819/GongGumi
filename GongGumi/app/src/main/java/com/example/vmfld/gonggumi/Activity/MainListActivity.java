package com.example.vmfld.gonggumi.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.NonSdkApiUsedViolation;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.vmfld.gonggumi.Adapter.EnteredRoomListAdapter;
import com.example.vmfld.gonggumi.ApiData.Datum;
import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.EnteredRoomListClassData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.ApiData.UserData;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.EnteredRoomApi;
import com.example.vmfld.gonggumi.interfaces.UserIdApi;

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

    public static final int REQUEST_ROOM_FINISH = 2000;

    String userDeviceID;
    String installDateString;
    Integer userIDInt = null;
    Integer DbCount = null;

    ContentValues contentValues = new ContentValues();

    private ListView listViewEnteredRoom;
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
                startActivityForResult(new Intent(MainListActivity.this, MakeRoomActivity.class)
                                                            , REQUEST_ROOM_FINISH); // activity 전환
            }
        });

        /** 내부 DB에 저장된 사용자ID가 NULL일 경우 사용자 ID 저장하기**/

        UserDbHelper userDbHelper = UserDbHelper.getsInstance(this);

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.UserIdEntry.TABLE_NAME,
                        null, null, null, null, null, null);


        if(cursor != null && cursor.moveToFirst()){
            userIDInt = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID));
            // cursor.close();
        }

        DbCount = cursor.getCount();
        //userIDInt = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID));

        Log.e("DBCount 값 : ", DbCount+"");

        if (DbCount == 0) {

            /** 사용자 ID 받아오기 **/
            userDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

            System.out.printf(userDeviceID);
            Log.e("아이디", userDeviceID);


            /** 사용자 앱 설치 날짜 받아오기 **/
            PackageManager packageManager = getPackageManager();
            long installTimeInMilliseconds; // install time is conveniently provided in milliseconds

            Date installMDate = null;

            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                installTimeInMilliseconds = packageInfo.firstInstallTime;
                installDateString = new SimpleDateFormat("yyyy-mm-dd").format(installTimeInMilliseconds);
            } catch (PackageManager.NameNotFoundException e) {
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
            input.put("uuid", userDeviceID);
            input.put("date", installDateString);

            userIdApi.postUserData(input).enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {

                    UserData uuid = response.body();
                    List<Datum> datumList = response.body().data;


                    if (response.isSuccessful()) {

                        Log.e("통신 값 :", response.code() + "");
                        Log.e("잘 들어왔나 확인 : ", datumList.get(0).getUserid() + "");

                        userIDInt = datumList.get(0).getUserid();

                        /**  내부 DB에 USERID 저장하기 **/

                        contentValues.put(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID, userIDInt); // 저장

                        SQLiteDatabase db = UserDbHelper.getsInstance(activity).getWritableDatabase();

                        long newUserId = db.insert(UserIdContract.UserIdEntry.TABLE_NAME,
                                                                            null,
                                                                             contentValues);

                        if (newUserId == -1) {
                            //Toast.makeText(activity, "저장에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
                            Log.e("저장에 문제가 발생하였습니다 : ", "");
                        } else {
                            //Toast.makeText(activity, "UserId 저장되었습니다", Toast.LENGTH_SHORT).show();
                            Log.e("UserId 저장되었습니다 : ", "");
                        }


                    } else {
                        Log.e("데이터 통신 실패 :", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    Log.e("에러 발생 : ", t.getMessage());
                }
            });

        }
        /** 내부 DB에 저장된 사용자 ID가 들어있을 경우 통신하기**/
        else
        {
            Log.e("리스트 통신 시작 : " , userIDInt+"");
            listViewEnteredRoom = (ListView) findViewById(R.id.my_room_list);

           /** Retrofit으로 참가한 방 List 불러오기 **/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(EnteredRoomApi.EnteredRoomUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EnteredRoomApi enteredRoomApi  = retrofit.create(EnteredRoomApi.class);

           Call<EnteredRoomData> call =  enteredRoomApi.postUserIdforRoom(userIDInt);

           call.enqueue(new Callback<EnteredRoomData>() {
               @Override
               public void onResponse(Call<EnteredRoomData> call, Response<EnteredRoomData> response) {
                   EnteredRoomData uuid = response.body();
                   List<EnteredRoomListClassData> enteredRoomListClassData = response.body().data;

                   Log.e("통신 값 :", response.code() + "");
                   Log.e("잘 들어왔나 확인 : ", enteredRoomListClassData.get(0).getRoomname() + "");

                   listViewEnteredRoom.setAdapter(new EnteredRoomListAdapter(getApplicationContext(), enteredRoomListClassData));

               }

               @Override
               public void onFailure(Call<EnteredRoomData> call, Throwable t) {
                   Log.e("에러 발생2 : ", t.getMessage());
               }
           });

        }

    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main_list, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_search) {
                return true;
            } else if (id == R.id.fab_make_room) {

            }

            return super.onOptionsItemSelected(item);
        }

        /** Cursor Adapter **/

        private static class UserIDAdapter extends CursorAdapter {

            public UserIDAdapter(Context context, Cursor c) {
                super(context, c, false);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                if (cursor == null) {
                    return null;
                }
                return parent;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                cursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID);
            }
        }

        /** 만든 방 바로 보여지게 설정 **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==REQUEST_ROOM_FINISH  && resultCode == RESULT_OK){

            listViewEnteredRoom = (ListView) findViewById(R.id.my_room_list);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(EnteredRoomApi.EnteredRoomUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EnteredRoomApi enteredRoomApi  = retrofit.create(EnteredRoomApi.class);

            Call<EnteredRoomData> call =  enteredRoomApi.postUserIdforRoom(userIDInt);

            call.enqueue(new Callback<EnteredRoomData>() {
                @Override
                public void onResponse(Call<EnteredRoomData> call, Response<EnteredRoomData> response) {
                    EnteredRoomData uuid = response.body();
                    List<EnteredRoomListClassData> enteredRoomListClassData = response.body().data;

                    Log.e("통신 값 :", response.code() + "");
                    Log.e("잘 들어왔나 확인 : ", enteredRoomListClassData.get(0).getRegdate() + "");

                    listViewEnteredRoom.setAdapter(new EnteredRoomListAdapter(getApplicationContext(), enteredRoomListClassData));

                }

                @Override
                public void onFailure(Call<EnteredRoomData> call, Throwable t) {
                    Log.e("에러 발생2 : ", t.getMessage());
                }
            });
        }
    }
}
