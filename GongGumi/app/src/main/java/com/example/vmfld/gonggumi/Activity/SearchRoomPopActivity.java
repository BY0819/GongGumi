package com.example.vmfld.gonggumi.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vmfld.gonggumi.ApiData.JoinRoomData;
import com.example.vmfld.gonggumi.ApiData.SearchRoomData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.RoomDataContract;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.JoinRoomApi;
import com.example.vmfld.gonggumi.interfaces.MakeRoomApi;
import com.example.vmfld.gonggumi.interfaces.SearchRoomApi;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class SearchRoomPopActivity extends Activity {

    Integer userid = null;
    Integer securitycode = null;
    Integer roomid = null;
    String name = null;

    ContentValues contentValues = new ContentValues();
    private Activity activity;

    EditText etxt_secretcode;
    EditText etxt_membername;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchroom_pop);

        /** CUSTOM DIALOG 작성 **/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags =WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);

        getWindow().setLayout((int) (width*0.78), (int) (height*0.3));

        /** 넘어온 데이터 맞는지 확인 **/

        if(getIntent() != null){
            roomid = getIntent().getIntExtra("roomid", 0);

            Log.e("SVA에서 넘어온 roomid :" ,roomid+"");
        }


        Button btn_check_mem = (Button) findViewById(R.id.btn_check_mem);

        btn_check_mem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    /**  사용자 입력 정보 받아오기 **/
                    etxt_secretcode =(EditText)findViewById(R.id.etxt_secretcode);
                    etxt_membername = (EditText)findViewById(R.id.etxt_membername);

                    String temp_securitycode = null;
                    temp_securitycode =  etxt_secretcode.getText().toString();
                    name =  etxt_membername.getText().toString();

                    /** 데이터 베이스에서 userid 받아오기 **/
                    UserDbHelper userDbHelper = UserDbHelper.getsInstance(SearchRoomPopActivity.this);

                    Cursor cursor = userDbHelper.getReadableDatabase()
                            .query(UserIdContract.UserIdEntry.TABLE_NAME,
                                    null, null, null, null, null, null);


                    if(cursor != null && cursor.moveToFirst()){
                        userid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID));
                        // cursor.close();
                    }

                    // 유저 아이디 제대로 들어왔는지 확인
                    Log.e("유저아이디 : ", userid+"");

                    // Post 보낼 값 잘 들어왔는지 확인
                    Log.e("SRpop 입력 데이터 확인 : ",userid +"//" + securitycode  +"//" + name  +"//" +roomid  +"//");

                    if(name.isEmpty() || temp_securitycode.isEmpty()){
                        Toast.makeText(SearchRoomPopActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        securitycode = Integer.parseInt(temp_securitycode);

                        /** Retrofit으로 통신하고 정보 받아오기 **/
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(SearchRoomApi.SearchRoomUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        final JoinRoomApi joinRoomApi = retrofit.create(JoinRoomApi.class);

                        HashMap<String, Object> input = new HashMap<>();
                        input.put("userid", userid);
                        input.put("securitycode", securitycode);
                        input.put("roomid", roomid);
                        input.put("name", name);

                        joinRoomApi.postMemberData(input).enqueue(new Callback<JoinRoomData>() {
                            @Override
                            public void onResponse(Call<JoinRoomData> call, Response<JoinRoomData> response) {
                                JoinRoomData joinRoomData = response.body();

                                String  temp_roomid = response.body().getRoomid();
                                String temp_flag = response.body().getUserreadflag();

                                Log.e("통신 값 :", response.code() + "");
                                Log.e("joinRoom Result : ", joinRoomData.getResult()+ "//" + joinRoomData.getRoomid()+"//"+temp_flag+"//" +temp_roomid +"");

                                if(joinRoomData.getResult().equals("success")){

                                    /** DataBase에 Roomid와 Flag 저장하기 **/

                                    contentValues.put(UserIdContract.RoomDataEntry.COLUMN_NAME_ROOM_ID, temp_roomid);// Room id 저장
                                    contentValues.put(UserIdContract.RoomDataEntry.COLUMN_NAME_FLAG, temp_flag);// flag 저장


                                    SQLiteDatabase db = UserDbHelper.getsInstance(activity).getWritableDatabase();

                                    long newUserId = db.insert(UserIdContract.RoomDataEntry.TABLE_NAME,
                                            null,
                                            contentValues);

                                    if (newUserId == -1) {

                                        Log.e("저장에 문제가 발생하였습니다 : ", "");
                                    } else {

                                        Log.e("UserId 저장되었습니다 : ", "");
                                    }


                                }else if(joinRoomData.getResult().equals("fail")){
                                    Toast.makeText(activity, "보안코드가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(activity, "이미 입장하셨습니다", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<JoinRoomData> call, Throwable t) {
                                Log.e("에러 발생 : ", t.getMessage());
                            }
                        });

                    }

                    setResult(RESULT_OK);
                    finish();
                 }
            }
        );
    }
}
