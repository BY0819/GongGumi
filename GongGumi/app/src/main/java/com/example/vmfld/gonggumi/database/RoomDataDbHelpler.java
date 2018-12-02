package com.example.vmfld.gonggumi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomDataDbHelpler extends SQLiteOpenHelper {
    private static RoomDataDbHelpler sInstance;

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "roomdata.db";


    public static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %S (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    RoomDataContract.RoomDataEntry.TABLE_NAME,
                    RoomDataContract.RoomDataEntry._ID,
                    RoomDataContract.RoomDataEntry.COLUMN_NAME_ROOM_ID,
                    RoomDataContract.RoomDataEntry.COLUMN_NAME_FLAG);

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserIdContract.UserIdEntry.TABLE_NAME;


    public  static  RoomDataDbHelpler getsInstance(Context context){
        if( sInstance  == null ){
            sInstance = new RoomDataDbHelpler(context);
        }
        return sInstance;
    }

    public RoomDataDbHelpler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
