package com.atahanhalici.ratethecity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_ID = "ID";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";
    private static final String COL_GENDER = "gender";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_GENDER + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean registerUser(String username, String password, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_GENDER, gender);

        // Kullanıcı adının daha önce kullanılıp kullanılmadığını kontrol et
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_USERNAME + "=?", new String[]{username});
        if (cursor.getCount() > 0) {
            // Kullanıcı adı zaten var
            cursor.close();
            return false;
        } else {
            // Yeni kullanıcıyı ekle
            long result = db.insert(TABLE_NAME, null, contentValues);
            cursor.close();
            return result != -1;
        }
    }
    public List<Map<String, String>> filterUsers(String username, String gender) {
        List<Map<String, String>> filteredUsers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Cinsiyete göre filtreleme sorgusu oluştur
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE ";
        if (!gender.equals("")) {
            query += COL_GENDER + "='" + gender + "' AND ";
        }
        // Kullanıcı adına göre filtreleme sorgusunu ekle
        query += COL_USERNAME + " LIKE '" + username + "%'";

        Cursor cursor = db.rawQuery(query, null);
        // Filtrelenmiş kullanıcıları al
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Map<String, String> userMap = new HashMap<>();
                int usernameIndex = cursor.getColumnIndex(COL_USERNAME);
                int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
                int genderIndex = cursor.getColumnIndex(COL_GENDER);

                String filteredUsername = cursor.getString(usernameIndex);
                String password = cursor.getString(passwordIndex);
                String filteredGender = cursor.getString(genderIndex);

                // Kullanıcı bilgilerini map'e ekle
                userMap.put("username", filteredUsername);
                userMap.put("password", password);
                userMap.put("gender", filteredGender);

                // Filtrelenmiş kullanıcıyı listeye ekle
                filteredUsers.add(userMap);
            } while (cursor.moveToNext());
        }

        // Cursor ve veritabanı bağlantısını kapat
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        // Filtrelenmiş kullanıcıları içeren listeyi döndür
        return filteredUsers;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }


    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_USERNAME + " = ?", new String[]{username});
        return result > 0;
    }

    public Map<String, String> getUserByUsername(String username) {
        Map<String, String> userMap = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL_USERNAME, COL_PASSWORD, COL_GENDER}, COL_USERNAME + "=?",
                new String[]{username}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
            int genderIndex = cursor.getColumnIndex(COL_GENDER);

            String password = cursor.getString(passwordIndex);
            String gender = cursor.getString(genderIndex);

            // Kullanıcı bilgilerini map'e ekle
            userMap.put("username", username);
            userMap.put("password", password);
            userMap.put("gender", gender);
        }
        // Cursor ve veritabanı bağlantısını kapat
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        // Kullanıcı bilgilerini içeren map'i döndür
        return userMap;
    }



}
