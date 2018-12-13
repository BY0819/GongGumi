package com.example.vmfld.gonggumi.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.vmfld.gonggumi.ApiData.UsageData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.MakeRoomApi;
import com.example.vmfld.gonggumi.interfaces.ManageMoneyApi;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageMoneyActivity extends AppCompatActivity {

    int add_or_sub = 1;


    EditText etxt_price;
    EditText etxt_memo;

    Integer userid = null;
    String memo;
    Integer usageprice;
    String date;
    String publicpersonal="Y";
    String deleteflag;
    Integer roomid;


    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_money);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Create BackButton
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Remove Real Title

        final Button btn_addMoney = (Button) findViewById(R.id.btn_addMoney);
        final Button btn_subMoney = (Button) findViewById(R.id.btn_subMoney);
        final Switch btn_pubper = (Switch) findViewById(R.id.switch1);

        btn_pubper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    publicpersonal = "N";
                    Log.e("publicpersonal: ", publicpersonal);
                }
                else{
                    publicpersonal = "Y";
                    Log.e("publicpersonal: ", publicpersonal);
                }
            }
        });

        btn_addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_addMoney.setBackgroundResource(R.color.colorPrimary);
                btn_subMoney.setBackgroundResource(R.color.colorGray);
                add_or_sub = 1;
                Log.e("add_or_sub: ", add_or_sub+"");
            }
        });

        btn_subMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_subMoney.setBackgroundResource(R.color.colorPrimary);
                btn_addMoney.setBackgroundResource(R.color.colorGray);
                add_or_sub = -1;
                Log.e("add_or_sub: ", add_or_sub+"");
            }
        });

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

        etxt_price = findViewById(R.id.etxt_price);
        etxt_price.addTextChangedListener(watcher_one);

        findViewById(R.id.btn_manage).setOnClickListener(ManagebtnClickListener);


    }//onCreate

    /** 확인 버튼 눌렀을때 리스너 **/
    private  Button.OnClickListener ManagebtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            etxt_memo = findViewById(R.id.etxt_memo);
            etxt_price = findViewById(R.id.etxt_price);
            String temp_price = null;

            /** 넘어온 데이터 맞는지 확인 **/

            if(getIntent() != null){
                roomid = getIntent().getIntExtra("roomid", 0);

                Log.e("RA에서 넘어온 roomid :" ,roomid+"");
            }

            /** 사용자 입력 정보 받아오기 **/
            memo = etxt_memo.getText().toString();
            deleteflag = "N";

            long now = System.currentTimeMillis();
            Date date_ = new Date(now);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date =simpleDateFormat.format(date_);

            temp_price = etxt_price.getText().toString();

            // 사용자가 입력을 모두 했는지 확인
            if(memo.isEmpty() || temp_price.isEmpty() )
            {
                Toast.makeText(ManageMoneyActivity.this, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
            else
            {
                usageprice = add_or_sub * Integer.parseInt(temp_price.replaceAll(",",""));

                /** Retrofit으로 Room정보 Post하기**/
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ManageMoneyApi.BaseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                final ManageMoneyApi manageMoneyApi = retrofit.create(ManageMoneyApi.class);

                Log.e("입력 값 형식 변환 확인 : ", userid+"//"+roomid+"//"+usageprice+"//"+date+"//"
                        +publicpersonal+"//"+memo+"//"+deleteflag+"//");

                HashMap<String, Object> input = new HashMap<>();
                input.put("userid", userid);
                input.put("roomid", roomid);
                input.put("usageprice", usageprice);
                input.put("date", date);
                input.put("publicpersonal", publicpersonal);
                input.put("memo", memo);
                input.put("deleteflag", deleteflag);

                manageMoneyApi.postUsage(input).enqueue(new Callback<UsageData>() {
                    @Override
                    public void onResponse(Call<UsageData> call, Response<UsageData> response) {
                        UsageData usageData = response.body();

                        if (response.isSuccessful()) {

                            Log.e("통신 값 :", response.code() + "");
                            Log.e("잘 들어왔나 확인 : ", "방 번호 :" + usageData.getResult() +"");


                        }

                    }

                    @Override
                    public void onFailure(Call<UsageData> call, Throwable t) {
                        Log.e("에러 발생 : ", t.getMessage());
                    }
                });

                setResult(RESULT_OK);
                finish();
            }




        }};


    /** 세자리씩 끊어서 , 찍어주기 **/
    TextWatcher watcher_one = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(result)){
                result = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                etxt_price.setText(result);
                etxt_price.setSelection(result.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"사용 내역 입력을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                Toast.makeText(this,"사용 내역 입력을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
