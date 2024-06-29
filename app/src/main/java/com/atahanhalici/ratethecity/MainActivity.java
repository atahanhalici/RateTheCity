package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView adi;
    TextView ulke;
    TextView yazar;
    TextView ozet;
    TextView tarih;
    TextView dogal;
    TextView mutfak;
    TextView ekonomi;
    TextView yorum;
    String valilikLink;
    String belediyeLink;
    RatingBar rating;
    City selectedCity;
    CityDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ViewPager viewPager = findViewById(R.id.viewPager);
        Intent intent=getIntent();
        String sehir = (String) intent.getStringExtra("city");
        dbHelper = new CityDatabaseHelper(this);
         selectedCity=dbHelper.getCityByName(sehir);
        SliderAdapter.OnItemClickListener itemClickListener = new SliderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Tıklama işlemleri burada yapılır
                // position, tıklanan öğenin indeksidir
            }
        };
        SliderAdapter sliderAdapter = new SliderAdapter(this,selectedCity.images,itemClickListener);
        viewPager.setAdapter(sliderAdapter);


rating=findViewById(R.id.ratingBar2);
        adi=findViewById(R.id.textView);
        ulke=findViewById(R.id.textView2);
        yazar=findViewById(R.id.textView4);
        ozet=findViewById(R.id.ilkozet);
        tarih=findViewById(R.id.tarihkultur);
        mutfak=findViewById(R.id.mutfak);
        ekonomi=findViewById(R.id.ekonomiveturizm);
        yorum=findViewById(R.id.yazaryorumu);
        dogal=findViewById(R.id.dogalguzellikler);

        adi.setText(selectedCity.adi);
        ulke.setText(selectedCity.ulke);
        yazar.setText("Ekleyen: "+selectedCity.yazar);
        tarih.setText(selectedCity.tarihvekultur);
        mutfak.setText(selectedCity.mutfak);
        ekonomi.setText(selectedCity.ekonomiveturizm);
        yorum.setText(selectedCity.yazaryorumu);
        dogal.setText(selectedCity.dogalguzellikler);
        ozet.setText(selectedCity.ozet);
        belediyeLink=selectedCity.belediyeLink;
        valilikLink=selectedCity.valilikLink;



        if(selectedCity.getNumberOfVote()==0){
            rating.setRating(0);
        }else{
            double sayi= selectedCity.getAuthorScore()/4/selectedCity.getNumberOfVote();
            float sayim=(float) sayi;
            rating.setRating(sayim);
        }
       /* int[] newImages = new int[selectedCity.images.size()];
        for (int i = 0; i < selectedCity.images.size(); i++) {
            newImages[i] = (int) selectedCity.images.get(i);
        }
        sliderAdapter.setImages(newImages);*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        updateCityData();
    }
    private void updateCityData() {
        selectedCity=dbHelper.getCityByName(selectedCity.adi);
        if(selectedCity.getNumberOfVote()==0){
            rating.setRating(0);
        }else{

            Log.d("TAG", "updateCityData: çalıştı");
            double sayi= selectedCity.getAuthorScore()/4/selectedCity.getNumberOfVote();
            float sayim=(float) sayi;
            rating.setRating(sayim);
        }
    }
    public void ValilikYonlendir(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(valilikLink));
        startActivity(intent);


    }

    public void oyla(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username", "");

        if(selectedCity.yazar.trim().equalsIgnoreCase(username.trim())){
            Toast.makeText(this, "Kendi Eklediğiniz Şehirleri Oylayamazsınız!", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this,VotePage.class);
            intent.putExtra("city", selectedCity.adi);
            startActivity(intent);
        }

    }
    public void BelediyeYonlendir(View view){


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(belediyeLink));
        startActivity(intent);


    }
}