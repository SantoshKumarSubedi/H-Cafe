package com.hauday.hauday1.Helper;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;

public class GlobalState extends Application {

    SharedPreferences checkedLogin;
    SharedPreferences.Editor checkedLoginEditor;

    SharedPreferences validUserInfo;
    SharedPreferences.Editor validUserInfoEditor;

    public static GlobalState singleton;

    public static final String PREFS_IS_CHECKED_LOGIN = "is logged in"; // to check if user is logged in
    public static final String PREFS_VALID_USER_INFO = "valid user info"; // to store the user information like name and phone number

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onCreate() {
        super.onCreate();

        checkedLogin = this.getSharedPreferences(PREFS_IS_CHECKED_LOGIN, 0);
        checkedLoginEditor = checkedLogin.edit();

        validUserInfo = this.getSharedPreferences(PREFS_VALID_USER_INFO, 0);
        validUserInfoEditor = validUserInfo.edit();

        singleton = this;
    }

    /**
     * @return MySIngleton instance
     */
    public GlobalState getInstance() {
        return singleton;
    }

    public String getPrefscheckedLogin(){
        return checkedLogin.getString(PREFS_IS_CHECKED_LOGIN,"");
    }

    public void setPrefscheckedLogin(String loggedStatus,int resultCode){
        if (resultCode == 1) {
            checkedLoginEditor.putString(PREFS_IS_CHECKED_LOGIN, loggedStatus).commit();
        } else {
            checkedLoginEditor.remove(PREFS_IS_CHECKED_LOGIN).commit();
        }
    }

    public String getPREFS_VALID_USER_INFO(){
        return validUserInfo.getString(PREFS_VALID_USER_INFO,"");
    }

    public void setPrefsValidUserInfo(String validUser,int resultCode){
        if (resultCode == 1) {
            validUserInfoEditor.putString(PREFS_VALID_USER_INFO, validUser).commit();
        } else {
            validUserInfoEditor.remove(PREFS_VALID_USER_INFO).commit();
        }
    }
}