package com.example.vmfld.gonggumi.database;

import android.provider.BaseColumns;

public class UserIdContract {
    private  UserIdContract() {

    }

    public static class UserIdEntry implements BaseColumns{
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USER_ID = "id";
    }

    public static class RoomDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "roomdata";
        public static final String COLUMN_NAME_ROOM_ID= "roomid";
        public static final String COLUMN_NAME_FLAG = "flag";
    }

    public  static  class RoomIdEntry implements  BaseColumns{
        public static final String TABLE_NAME = "roomid";
        public static final String COLUMN_NAME_ROOM_ID = "roomid";
    }

}
