package com.example.vmfld.gonggumi.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vmfld.gonggumi.ApiData.Datum;

public class SharedUserManager {

    private static final String SHARED_USER_ID= "my_shared_id";

    private static  SharedUserManager mInstance;
    private Context mCtx;

    private SharedUserManager(Context mCtx){
        this.mCtx =mCtx;
    }

    public static synchronized SharedUserManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedUserManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(Datum userData){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_USER_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("userid", userData.getUserid());

        editor.apply();
    }

    public  boolean isUseridIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_USER_ID, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt("userid", -1) != -1;
    }

}
