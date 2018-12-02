package com.example.vmfld.gonggumi.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.SearchRoomDataList;
import com.example.vmfld.gonggumi.R;

import java.lang.reflect.Array;
import java.util.List;

public class SearchRoomListAdapter extends ArrayAdapter {

    Integer[] RoomImgIDs= {R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.room4,
            R.drawable.room5, R.drawable.room6, R.drawable.room7, R.drawable.room8};

    private Context context;
    private List<SearchRoomDataList> searchRoom;

    public SearchRoomListAdapter(Context context, List<SearchRoomDataList> searchRoom) {
        super(context, R.layout.room_model_layout, searchRoom);
        this.context = context;
        this.searchRoom = searchRoom;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.room_model_layout,  parent,false);

        SearchRoomDataList searchRoomDataList = searchRoom.get(position);

        int img_index =(int) (Math.random()*8);
        int res = RoomImgIDs[img_index];

        convertView.setBackgroundResource(res);

        TextView txtv_roomName = (TextView) convertView.findViewById(R.id.txtv_roomName);
        txtv_roomName.setText(searchRoomDataList.getRoomname());
        TextView txtv_regdate = (TextView) convertView.findViewById(R.id.txtv_regdate);

        //Date date = new Date();
        //date = enteredRoomListClassData.getRegdate();



        String regdate = searchRoomDataList.getRegdate();
        txtv_regdate.setText(regdate.substring(0,10));

        Log.e("Date 값 : " , regdate+"");

        TextView txtv_leaderName = (TextView) convertView.findViewById(R.id.txtv_leaderName);
        txtv_leaderName.setText(searchRoomDataList.getName());
        return convertView;
    }
}
