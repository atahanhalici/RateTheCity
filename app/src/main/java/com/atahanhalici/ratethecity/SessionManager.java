package com.atahanhalici.ratethecity;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_GENDER = "gender";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String username, String password, String gender) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);
        editor.apply();
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "");
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, "");
    }

    public String getGender() {
        return pref.getString(KEY_GENDER, "");
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }
}

