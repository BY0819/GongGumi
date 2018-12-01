package com.example.vmfld.gonggumi.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vmfld.gonggumi.ApiData.MakeRoomData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.MakeRoomApi;
import com.example.vmfld.gonggumi.interfaces.UserIdApi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Thread.sleep;

public class MakeRoomActivity extends AppCompatActivity {
    public int num_mem = 0;

    public static final int REQUEST_CODE_FINISH = 1000;

   // Button btn_add_mem = (Button)findViewById(R.id.btn_add_mem);
    //Button btn_sub_mem = (Button)findViewById(R.id.btn_sub_mem);
   private DecimalFormat decimalFormat = new DecimalFormat("#,###");
   private String result="";

    EditText etxt_roomname;
    EditText etxt_leadername;
    EditText etxt_price_one;
    EditText etxt_price_other;

    String roomname = null;
    Integer joinppl = null;
    String regdate ;
    Integer personalprice = null;
    Integer carryoverprice = null;
    String deleteflag;
    String leadername = null;
    Integer userid;
    Integer secretcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.makeroom_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); // 커스텀 타이틀 사용으로 실제 타이틀 삭제
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 생성



        findViewById(R.id.btn_sub_mem).setOnClickListener(btnClickListener);
        findViewById(R.id.btn_add_mem).setOnClickListener(btnClickListener);

        /** 유저 아이디 DB에서 받아오기 **/
        UserDbHelper userDbHelper = UserDbHelper.getsInstance(this);

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.UserIdEntry.TABLE_NAME,
                        null, null, null, null, null, null);


        if(cursor != null && cursor.moveToFirst()){
            userid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID));
            // cursor.close();
        }

        // 유저 아이디 제대로 들어왔는지 확인
        Log.e("유저아이디 : ", userid+"");


        Log.e("현재 값1 : ", roomname+"//"+leadername+"//"+personalprice+"//"+carryoverprice+"//"
                                                            +joinppl+"//"+deleteflag+"//"+regdate+"//");
        etxt_price_one = findViewById(R.id.etxt_price_one);
        etxt_price_one.addTextChangedListener(watcher_one);

        etxt_price_other = findViewById(R.id.etxt_price_other);
        etxt_price_other.addTextChangedListener(watcher_other);

        findViewById(R.id.btn_makeroom).setOnClickListener(makeRoombtnClickListener);

    }

    /** 확인 버튼 눌렀을때 리스너 **/
    private  Button.OnClickListener makeRoombtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            etxt_roomname = findViewById(R.id.etxt_roomname);
            etxt_leadername = findViewById(R.id.etxt_leadername);
            etxt_price_one = findViewById(R.id.etxt_price_one);
            etxt_price_other = findViewById(R.id.etxt_price_other);
            String temp_one = null;
            String temp_other = null;


            /** 사용자 입력 정보 받아오기 **/
            roomname = etxt_roomname.getText().toString();
            leadername = etxt_leadername.getText().toString();
            // personalprice = Integer.parseInt(etxt_price_one.getText().toString().replaceAll(",", ""));
            //carryoverprice = Integer.parseInt(etxt_price_other.getText().toString().replaceAll(",", ""));
            joinppl = num_mem;
            deleteflag = "N";

            temp_one = etxt_price_one.getText().toString();
            temp_other = etxt_price_other.getText().toString();


            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            regdate =simpleDateFormat.format(date);
            //regdate = simpleDateFormat.parse(temp);



            Log.e("현재 값2 : ", roomname+"//"+leadername+"//"+temp_one+"//"+temp_other+"//"
                    +joinppl+"//"+deleteflag+"//"+regdate+"//");

            // 사용자가 입력을 모두 했는지 확인
            if(roomname.isEmpty() || leadername.isEmpty() || temp_one.isEmpty() || temp_other.isEmpty() )
            {
                Toast.makeText(MakeRoomActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
            else
            {

                personalprice = Integer.parseInt(temp_one.replaceAll(",", ""));
                carryoverprice = Integer.parseInt(etxt_price_other.getText().toString().replaceAll(",", ""));


                /** Retrofit으로 Room정보 Post하기**/
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(MakeRoomApi.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final MakeRoomApi makeRoomApi = retrofit.create(MakeRoomApi.class);

                Log.e("현재 값2 : ", roomname+"//"+leadername+"//"+personalprice+"//"+carryoverprice+"//"
                        +joinppl+"//"+deleteflag+"//"+regdate+"//");

                HashMap<String, Object> input = new HashMap<>();
                input.put("roomname", roomname);
                input.put("joinppl", joinppl);
                input.put("regdate", regdate);
                input.put("personalprice", personalprice);
                input.put("carryoverprice", carryoverprice);
                input.put("deleteflag", deleteflag);
                input.put("name", leadername);
                input.put("userid", userid);

                makeRoomApi.postUserData(input).enqueue(new Callback<MakeRoomData>() {
                    @Override
                    public void onResponse(Call<MakeRoomData> call, Response<MakeRoomData> response) {
                        MakeRoomData makeRoomData =response.body();

                        if (response.isSuccessful()) {

                            Log.e("통신 값 :", response.code() + "");
                            Log.e("잘 들어왔나 확인 : ", makeRoomData.getRoomindex() + ""
                                    +makeRoomData.getSecuritycode()+"");
                            secretcode = makeRoomData.getSecuritycode();

                            Intent intent = new Intent(MakeRoomActivity.this, MakeRoomPopActivity.class);

                            intent.putExtra("p_secretcode", secretcode);
                            startActivityForResult(intent, REQUEST_CODE_FINISH);

                            //startActivity(new Intent(MakeRoomActivity.this,  MakeRoomPopActivity.class) );
                        } else {
                            Log.e("데이터 통신 실패 :", response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<MakeRoomData> call, Throwable t) {
                        Log.e("에러 발생 : ", t.getMessage());
                    }
                });
            }




        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_CODE_FINISH  &&  resultCode == RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final TextView txt_num_mem = (TextView)findViewById(R.id.txt_num_mem);


           if (v.getId() == R.id.btn_add_mem)
           {
               num_mem += 1;
               txt_num_mem.setText(""+num_mem);
           }
           else if (v.getId() == R.id.btn_sub_mem  && num_mem == 0)
           {
               Toast.makeText(MakeRoomActivity.this, "0 이하는 불가능합니다.", Toast.LENGTH_SHORT).show();
           }
           else if (v.getId() == R.id.btn_sub_mem)
           {
               num_mem -= 1;
               txt_num_mem.setText(""+num_mem);
           }

        }
    };

    /** 세자리씩 끊어서 , 찍어주기 **/
    TextWatcher watcher_one = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                etxt_price_one.setText(result);
                etxt_price_one.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /** 세자리씩 끊어서 , 찍어주기 **/
    TextWatcher watcher_other = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                etxt_price_other.setText(result);
                etxt_price_other.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    public void onBackPressed() {
        Toast.makeText(this,"방 만들기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Toast.makeText(this,"방 만들기를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
