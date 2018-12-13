package com.example.vmfld.gonggumi.Fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vmfld.gonggumi.Activity.RoomActivity;
import com.example.vmfld.gonggumi.Adapter.OfficialTabListAdapter;
import com.example.vmfld.gonggumi.ApiData.PublicInsertData;
import com.example.vmfld.gonggumi.ApiData.PublicTabData;
import com.example.vmfld.gonggumi.ApiData.PublicTabDetailData;
import com.example.vmfld.gonggumi.R;
import com.example.vmfld.gonggumi.database.UserDbHelper;
import com.example.vmfld.gonggumi.database.UserIdContract;
import com.example.vmfld.gonggumi.interfaces.PublicTabApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicTabFragment extends Fragment {

    int check = 0;
    int roomid;

    TextView txt_total_money;

    List<PublicInsertData> publicInsertData;
    List<PublicTabDetailData> publicTabDetailData;

    RoomActivity roomActivity =new RoomActivity();


    public  PublicTabFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.offical_m_fragment, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.officialMListView);
        OfficialTabListAdapter officialTabListAdapter = new OfficialTabListAdapter(this.getActivity(), getPublicInsertData());

        return  rootView;
    }

    @Override
    public  String toString(){
        return "공금";
    }

    public List<PublicInsertData> getPublicInsertData(){

        //List<PublicInsertData> publicInsertData = new ArrayList<>();

        UserDbHelper userDbHelper = UserDbHelper.getsInstance(getActivity());

        Cursor cursor = userDbHelper.getReadableDatabase()
                .query(UserIdContract.RoomIdEntry.TABLE_NAME,
                        null, null, null, null, null,  null);

        if(cursor != null && cursor.move(0)){
            roomid = cursor.getInt(cursor.getColumnIndexOrThrow(UserIdContract.RoomIdEntry.COLUMN_NAME_ROOM_ID));
        }

        Log.e("PF_Room 아이디 : ", roomid+"");


        /**Retrofit 통신하기**/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PublicTabApi.PublicTabURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final PublicTabApi publicTabApi  = retrofit.create(PublicTabApi.class);

        Call<PublicTabData> call =  publicTabApi.postRoomid(roomid);

        call.enqueue(new Callback<PublicTabData>() {
            @Override
            public void onResponse(Call<PublicTabData> call, Response<PublicTabData> response) {
                PublicTabData publicTabData = response.body();
                publicTabDetailData = response.body().detail;

                Log.e("RA_통신 값 :", response.code() + "");
                // Log.e("RA_잘 들어왔나 확인 : ", publicTabDetailData.get().getMemo() + "");

                Integer nowTotal = publicTabData.getPublicMoneyprice();
                Log.e("RA_총금액 : ",  nowTotal+"");


                /**Adapter에 들어갈 리스트 재정립 **/
                int temp  = 0;

                Log.e("사이즈값 : " , publicTabDetailData.size()+"");
                if(!publicTabDetailData.isEmpty()){
                    for(int i = 0; i < publicTabDetailData.size(); i++ ){

                        if(i!=0 && publicTabDetailData.get(i).getDate().equals(publicTabDetailData.get(i-1).getDate())){
                            check ++;

                            if(check == 2){
                                publicInsertData.get(temp).setMemo2(publicTabDetailData.get(i).getMemo());
                                publicInsertData.get(temp).setPrice2(-1 *publicTabDetailData.get(i).getUsageprice());

                                Log.e("현재  값2:  ", publicTabDetailData.get(i).getDate()+"//"+publicTabDetailData.get(i).getMemo()+"//"+publicTabDetailData.get(i).getUsageprice()+"//");
                                Log.d("Insert 리스트 확인1  : ", publicInsertData.get(temp).getDate()+"//"+publicInsertData.get(temp).getMemo1()+"//"
                                        +publicInsertData.get(temp).getPrice1()+"//"+publicInsertData.get(temp).getMemo2()+"//"+publicInsertData.get(temp).getPrice2()+"//");

                                temp ++;
                            }
                            else{
                                check = 0;
                                Log.e("현재  값3 :  ", publicTabDetailData.get(i).getDate()+"//"+publicTabDetailData.get(i).getMemo()+"//"+publicTabDetailData.get(i).getUsageprice()+"//");
                            }
                        }
                        else if(i==0 || !publicTabDetailData.get(i).getDate().equals(publicTabDetailData.get(i-1).getDate())){
                            check ++;
                            Log.e("현재  값1 :  ", publicTabDetailData.get(i).getDate()+"//"+publicTabDetailData.get(i).getMemo()+"//"+publicTabDetailData.get(i).getUsageprice()+"//");

                            PublicInsertData tempData = new PublicInsertData();
                            tempData.setDate(publicTabDetailData.get(i).getDate());
                            tempData.setMemo1(publicTabDetailData.get(i).getMemo());
                            tempData.setPrice1(-1 *publicTabDetailData.get(i).getUsageprice());
                            tempData.setMemo2(null);
                            tempData.setPrice2(null);

                            publicInsertData.add(temp, tempData);

                            Log.d("Insert 리스트 확인2  : ", publicInsertData.get(temp).getDate()+"//"+publicInsertData.get(temp).getMemo1()+"//"
                                    +publicInsertData.get(temp).getPrice1()+"//"+publicInsertData.get(temp).getMemo2()+"//"+publicInsertData.get(temp).getPrice2()+"//");
                        }
                    }

                }
                else{
                    Log.e("Error : ","publicTabDetailData 비어있음");
                }


                // listViewPublicTab.setAdapter(new OfficialTabListAdapter(getApplicationContext(), publicInsertData));
            }

            @Override
            public void onFailure(Call<PublicTabData> call, Throwable t) {
                Log.e("에러 발생 : ", t.getMessage());
            }
        });


        return publicInsertData;
    }

}
