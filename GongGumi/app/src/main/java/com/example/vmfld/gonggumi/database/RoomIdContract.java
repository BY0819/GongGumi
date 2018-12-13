package com.example.vmfld.gonggumi.database;

import android.provider.BaseColumns;

public class RoomIdContract {
    public RoomIdContract() {
    }

    public  static  class RoomIdEntry implements BaseColumns {
        public static final String TABLE_NAME = "roomid_data";
        public static final String COLUMN_NAME_ROOM_ID = "roomid";
    }
}
