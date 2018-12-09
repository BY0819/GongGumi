package com.example.vmfld.gonggumi.database;

import android.provider.BaseColumns;

public final class RoomDataContract {

    private RoomDataContract() {

    }

    public static class RoomDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "roomdata";
        public static final String COLUMN_NAME_ROOM_ID= "roomid";
        public static final String COLUMN_NAME_FLAG = "flag";
    }

}
