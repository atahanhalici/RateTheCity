package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SplashPage extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // Splash ekranın ne kadar süreyle gösterileceği (milisaniye cinsinden)
    List<String> maleNames = Arrays.asList(
            "ahmet", "mehmet", "mustafa", "ali", "huseyin", "hasan", "ibrahim", "yusuf", "omer", "osman");

    List<String> femaleNames = Arrays.asList(
            "fatma", "ayse", "emine", "hatice", "zeynep", "sultan", "hacer", "elif", "leyla", "zeliha");

    List<String> lastNames = Arrays.asList(
            "yilmaz", "kaya", "demir", "sahin", "celik", "arslan", "koc", "ozturk", "yildirim", "kilic");



    List<String> commonPasswords = Arrays.asList(
            "123456", "sifre", "123456789", "12345678", "12345", "1234567", "1234567890", "1234", "sifre123", "123456789");
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        // SharedPreferences nesnesini al
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

// SharedPreferences'taki tüm verileri sil
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

       /* String username=sharedPreferences.getString("username", "");
        Log.d("TAG", "onCreate: "+username);

        CityDatabaseHelper dbHelper = new CityDatabaseHelper(this);
        dbHelper.deleteAllCities();*/

     /*   List<Map<String,String>> userList = generateRandomUsers(100);
        Log.d("TAG", "onCreate: "+userList);*/



        // Splash ekranın gösterilme süresi sonunda MainActivity'ye geçiş yapmak için bir Handler kullanılır
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent ile SplashActivity'den MainActivity'ye geçiş yapılır
                Intent mainIntent = new Intent(SplashPage.this, LoginPage.class);
                startActivity(mainIntent);
                finish(); // SplashActivity'yi kapat
            }
        }, SPLASH_DELAY);
    }

    public  String generateRandomUsername(String isMale) {
        Random random = new Random();
        List<String> names = isMale=="Man" ? maleNames : femaleNames;
        String firstName = names.get(random.nextInt(names.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        return firstName + lastName;
    }

    public  String generateRandomPassword() {
        return commonPasswords.get(random.nextInt(commonPasswords.size()));
    }

    public  String generateRandomGender() {
        return random.nextBoolean() ? "Man" : "Woman";
    }

    public List<Map<String,String>> generateRandomUsers(int count) {

        List<Map<String,String>> userList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Map<String, String> userMap = new HashMap<>(); // Her döngüde yeni bir harita oluştur

            String gender = generateRandomGender();
            String username = generateRandomUsername(gender);
            String password = generateRandomPassword();


            userMap.put("username", username);
            userMap.put("password", password);
            userMap.put("gender", gender);

            userList.add(userMap);
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
     /*   for (Map<String, String> userMap : userList) {
            String username = userMap.get("username");
            String password = userMap.get("password");
            String gender = userMap.get("gender");


           boolean success = dbHelper.registerUser(username, password, gender);

            if (success) {
                Log.d("TAG", "Kullanıcı başarıyla kaydedildi: " + username);
            } else {
                Log.d("TAG", "Kullanıcı kaydedilirken bir hata oluştu: " + username);
            }
        }*/
        return userList;
    }

}