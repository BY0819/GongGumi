package com.example.vmfld.gonggumi.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vmfld.gonggumi.ApiData.EnteredRoomData;
import com.example.vmfld.gonggumi.ApiData.EnteredRoomListClassData;
import com.example.vmfld.gonggumi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class EnteredRoomListAdapter extends ArrayAdapter {

    Integer[] RoomImgIDs= {R.drawable.room1, R.drawable.room2, R.drawable.room3, R.drawable.room4,
                                                R.drawable.room5, R.drawable.room6, R.drawable.room7, R.drawable.room8};

    private Context context;
    private List<EnteredRoomListClassData> enteredRoom;

    public EnteredRoomListAdapter(Context context, List<EnteredRoomListClassData> enteredRoom) {
        super(context, R.layout.room_model_layout, enteredRoom);
        this.context = context;
        this.enteredRoom = enteredRoom;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.room_model_layout,  parent,false);

        EnteredRoomListClassData enteredRoomListClassData = enteredRoom.get(position);

        int img_index =(int) (Math.random()*8);
        int res = RoomImgIDs[img_index];

        convertView.setBackgroundResource(res);

        TextView txtv_roomName = (TextView) convertView.findViewById(R.id.txtv_roomName);
        txtv_roomName.setText(enteredRoomListClassData.getRoomname());
        TextView txtv_regdate = (TextView) convertView.findViewById(R.id.txtv_regdate);

        //Date date = new Date();
        //date = enteredRoomListClassData.getRegdate();



        String regdate = enteredRoomListClassData.getRegdate();
        txtv_regdate.setText(regdate.substring(0,10));

        Log.e("Date 값 : " , regdate+"");

        TextView txtv_leaderName = (TextView) convertView.findViewById(R.id.txtv_leaderName);
        txtv_leaderName.setText(enteredRoomListClassData.getName());
        return convertView;
    }
}
