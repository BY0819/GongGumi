package com.example.vmfld.gonggumi.Activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.WindowManager.LayoutParams;

import com.example.vmfld.gonggumi.ApiData.SecurityCodeData;
import com.example.vmfld.gonggumi.ApiData.SecurityCodeResultData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.GetCodeApi;
import com.example.vmfld.gonggumi.interfaces.JoinRoomApi;
import com.example.vmfld.gonggumi.interfaces.SearchRoomApi;

import org.w3c.dom.Text;

import java.security.Security;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CodeGetActivity extends AppCompatActivity {

    Integer position = null;
    Integer roomid = null;
    Integer userid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeget_pop);

        /** CUSTOM DIALOG 작성 **/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags =WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);

        getWindow().setLayout((int) (width*0.75), (int) (height*0.25));

        /** 넘어온 데이터 맞는지 확인 **/

        if(getIntent() != null){
            position = getIntent().getIntExtra("position", 0);

            Log.e("Room에서 넘어온 position :" ,position+"");
        }

        /**데이터베이스에서 roomid 가져오기 **/

        final UserDbHelper userDbHelper = UserDbHelper.getsInstance(CodeGetActivity.this);

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.RoomDataEntry.TABLE_NAME,
                        null, null, null, null, null, null);

        if(cursor != null && cursor.move(position+1)){
            roomid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.RoomDataEntry.COLUMN_NAME_ROOM_ID));
        }

        // Room 아이디 제대로 들어왔는지 확인
        Log.e("Room 아이디 : ", roomid+"");


        /** 데이터 베이스에서 userid 받아오기 **/

        Cursor idcursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.UserIdEntry.TABLE_NAME,
                        null, null, null, null, null, null);


        if(idcursor != null && idcursor.moveToFirst()){
            userid = idcursor.getInt(idcursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID));
            // cursor.close();
        }

        // 유저 아이디 제대로 들어왔는지 확인
        Log.e("유저아이디 : ", userid+"");


        /** Retrofit으로 통신하고 정보 받아오기 **/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GetCodeApi.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetCodeApi getCodeApi = retrofit.create(GetCodeApi.class);

        getCodeApi.postRoomId(roomid).enqueue(new Callback<SecurityCodeData>() {
            @Override
            public void onResponse(Call<SecurityCodeData> call, Response<SecurityCodeData> response) {
                SecurityCodeData securityCodeData = response.body();
                List<SecurityCodeResultData> securityCodeResultData = response.body().result;

                Integer check_userid = securityCodeResultData.get(0).getUserid();
                TextView txt_secretcodec = (TextView)findViewById(R.id.txt_secretcode);
                txt_secretcodec.setVisibility(View.GONE);

                Log.e("맞는지 확인", "userid: "+userid+"//check_userid: "+check_userid);
                // 유저아이디와 방장아이디와 맞으면 보안코드 뿌리기
                if(userid.equals(check_userid)){
                    txt_secretcodec.setText(securityCodeResultData.get(0).getSecuritycode().toString());
                    txt_secretcodec.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(CodeGetActivity.this, "방장만 발급 가능합니다", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SecurityCodeData> call, Throwable t) {

            }
        });


         Button mOK = (Button) findViewById(R.id.btn_check_sc);

         mOK.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
        setResult(RESULT_OK);
        finish();
        }
        });

    }

    @Override
    public void onBackPressed() {
      //  Toast.makeText(this,"사용 내역 입력을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                //Toast.makeText(this,"사용 내역 입력을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
