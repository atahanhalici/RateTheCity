package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class VotePage extends AppCompatActivity {
    TextView textView;
    RatingBar tarihRating, dogalRating, mutfakRating, ekonomiRating;
    CityDatabaseHelper dbHelper;
    float tarihScore,dogalScore, mutfakScore,ekonomiScore;
    City selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_page);

        getSupportActionBar().hide();
            textView=findViewById(R.id.sehiradi);
        ViewPager viewPager = findViewById(R.id.viewPager);
        Intent intent=getIntent();
        String sehir = (String) intent.getStringExtra("city");
        dbHelper = new CityDatabaseHelper(this);
        selectedCity =dbHelper.getCityByName(sehir);
        SliderAdapter.OnItemClickListener itemClickListener = new SliderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Tıklama işlemleri burada yapılır
                // position, tıklanan öğenin indeksidir
            }
        };
        SliderAdapter sliderAdapter = new SliderAdapter(this,selectedCity.images,itemClickListener);
        viewPager.setAdapter(sliderAdapter);
        tarihRating=findViewById(R.id.rating);
        dogalRating=findViewById(R.id.rating2);
        mutfakRating=findViewById(R.id.rating3);
        ekonomiRating=findViewById(R.id.rating4);
        textView.setText(sehir);




    }

    public void puanKaydet(View view){
        tarihScore  = tarihRating.getRating();
        dogalScore = dogalRating.getRating();
        mutfakScore = mutfakRating.getRating();
        ekonomiScore = ekonomiRating.getRating();

        if (tarihScore != 0 && dogalScore != 0 && mutfakScore != 0 && ekonomiScore != 0) {
            dbHelper.updateCityRatings(selectedCity.adi,tarihScore,dogalScore,mutfakScore,ekonomiScore);
        onBackPressed();
        }
        else{

            AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
            alertDialog.setTitle("İşlem Başarısız");
            alertDialog.setMessage("Tüm Değerler Dolu Gözükmüyor!");

            alertDialog.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        }


    }
}