package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class LoginPage extends AppCompatActivity {

    EditText usernameEditText;
    EditText  passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        usernameEditText = findViewById(R.id.kullaniciAdiGiris);
        passwordEditText = findViewById(R.id.sifreGiris);

        getSupportActionBar().hide();

    }

public void kayit(View view){
    // İkinci aktiviteye geçiş için intent oluştur
    Intent intent = new Intent(LoginPage.this, RegisterPage.class);
    // İkinci aktiviteye geçiş
    startActivity(intent);
}
    public void girisYap(View view){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() ){
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("Giriş Başarısız");
            alertDialog.setMessage("Tüm Değerler Dolu Gözükmüyor!");

            alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }else {

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            boolean loginSuccessful = dbHelper.loginUser(username, password);
            if(loginSuccessful) {
               dbHelper.getUserByUsername(username);
                Map<String, String> userMap = dbHelper.getUserByUsername(username);
                String gender = userMap.get("gender");

                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.createLoginSession(username, password, gender);


                Intent intent = new Intent(this, CitiesPage.class);
                 startActivity(intent);
                finish();
            } else {
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle("Giriş Başarısız");
                alertDialog.setMessage("Girilen Değerler Hatalı Olabilir! Lütfen Tekrar Deneyin.");

                alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        }
    }
}