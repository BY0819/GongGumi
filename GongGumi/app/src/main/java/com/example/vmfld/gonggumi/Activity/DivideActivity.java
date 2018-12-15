package com.example.vmfld.gonggumi.Activity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vmfld.gonggumi.interfaces.DivideApi;
import com.example.vmfld.gonggumi.ApiData.DivideData;
import com.example.vmfld.gonggumi.Adapter.DivideListAdapter;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DivideActivity extends AppCompatActivity {
    private ListView DivideDataListView;
    private int Roomid;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);

        DivideDataListView = (ListView)findViewById(R.id.divideListView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Create BackButton
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Remove Real Title


        /**roomid**/

        if(getIntent() != null){
            position = getIntent().getIntExtra("position",0);

            Log.e("Room에서 넘어온 position : ",position+"");
        }
        final UserDbHelper userDbHelper = UserDbHelper.getsInstance(DivideActivity.this);

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.RoomDataEntry.TABLE_NAME,
                        null, null, null, null, null, null);

        if(cursor != null && cursor.move(position+1)){
            Roomid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.RoomDataEntry.COLUMN_NAME_ROOM_ID));
        }

        Log.e("roomid is :", Roomid+"");

        /**
         * retrofit
         **/
        Retrofit divideretrofit = new Retrofit.Builder()
                .baseUrl(DivideApi.DivideUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final DivideApi divideApi = divideretrofit.create(DivideApi.class);

        Call<DivideData> call = divideApi.getDivideData(Roomid);
        call.enqueue(new Callback<DivideData>() {
            @Override
            public void onResponse(Call<DivideData> call, Response<DivideData> response) {

                Log.e("서버성공",response.message().toString());

                DivideListAdapter divideListAdapter = new DivideListAdapter();

                DivideData divideData = response.body();

                TextView str_i_totalleft = (TextView)findViewById(R.id.str_i_totalleft);
                str_i_totalleft.setText(divideData.getstrTotalLeft());

                for(int i = 0; i<divideData.getMemnum();i++){
                    divideListAdapter.addItem(
                            divideData.getResult(),
                            divideData.getPublicUsed(),
                            divideData.getTotalleft(),
                            divideData.getDPSData(),
                            i);
                }

                DivideDataListView.setAdapter(divideListAdapter);

            }

            @Override
            public void onFailure(Call<DivideData> call, Throwable t) {
                Log.e("서버오류",t.getMessage());
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
