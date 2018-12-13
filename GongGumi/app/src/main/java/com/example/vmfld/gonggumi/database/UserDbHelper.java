package com.example.vmfld.gonggumi.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    private static  UserDbHelper sInstance ;

    public static final int DB_VERSION = 1;
    public static final  String DB_NAME = "User.db";


    public static final String SQL_CREATE_ENTRIES =
            String .format("CREATE TABLE %S (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
                    UserIdContract.UserIdEntry.TABLE_NAME,
                    UserIdContract.UserIdEntry._ID,
                    UserIdContract.UserIdEntry.COLUMN_NAME_USER_ID);

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserIdContract.UserIdEntry.TABLE_NAME;


    public static final String SQL_CREATE_ROOM_ENTRIES =
            String.format("CREATE TABLE %S (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    RoomDataContract.RoomDataEntry.TABLE_NAME,
                    RoomDataContract.RoomDataEntry._ID,
                    RoomDataContract.RoomDataEntry.COLUMN_NAME_ROOM_ID,
                    RoomDataContract.RoomDataEntry.COLUMN_NAME_FLAG);

    private static final String SQL_DELETE_ROOM_ENTRIES =
            "DROP TABLE IF EXISTS " + UserIdContract.UserIdEntry.TABLE_NAME;

    public static final String SQL_CREATE_ROOM_ID_ENTRIES =
            String.format("CREATE TABLE %S (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
                    UserIdContract.RoomIdEntry.TABLE_NAME,
                    UserIdContract.RoomIdEntry._ID,
                    UserIdContract.RoomIdEntry.COLUMN_NAME_ROOM_ID);

    private static final String SQL_DELETE_ROOM_ID_ENTRIES =
            "DROP TABLE IF EXISTS " + UserIdContract.RoomIdEntry.TABLE_NAME;

    public  static  UserDbHelper getsInstance(Context context){
        if( sInstance  == null ){
            sInstance = new UserDbHelper(context);
        }
        return sInstance;
    }

    public UserDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ROOM_ENTRIES);
        db.execSQL(SQL_CREATE_ROOM_ID_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ROOM_ENTRIES);
        db.execSQL(SQL_DELETE_ROOM_ID_ENTRIES);
        onCreate(db);
    }
}
