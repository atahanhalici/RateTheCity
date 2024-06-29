package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class CitiesPage extends AppCompatActivity {

    ArrayList<City> cities;
    ListView listView;
    ArrayAdapter arrayAdapter;
    ImageView imageView,backgroundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_page);

    listView=findViewById(R.id.listview);
        CityDatabaseHelper dbHelper = new CityDatabaseHelper(this);
        cities=dbHelper.getAllCities();

        for (City city : cities) {
            Log.d("CityInfo", city.toString());
        }

         backgroundImageView = (ImageView) findViewById(R.id.backgroundImageView);

        Picasso.get()
                .load(R.drawable.logop) // buraya fotoğrafınızın id'si gelecek
                .transform(new BlurTransformation(this, 5, 15)) // bu satır fotoğrafı bulanıklaştırır
                .into(backgroundImageView);
        MyAdapter adapter = new MyAdapter(this, cities);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //System.out.println(landmarkNames.get(i));
                //System.out.println(countryNames.get(i));

                Intent intent = new Intent(CitiesPage.this,MainActivity.class);
                intent.putExtra("city", cities.get(i).adi);
                startActivity(intent);

            }
        });

        if (cities.isEmpty()) {
            imageView = findViewById(R.id.imageView);
            imageView.setVisibility(View.VISIBLE);
            backgroundImageView.setVisibility(View.GONE);
        }



    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCityData();
    }
    private void updateCityData() {
        CityDatabaseHelper dbHelper = new CityDatabaseHelper(this);
        cities = dbHelper.getAllCities();
        MyAdapter adapter = new MyAdapter(this, cities);

// ListView'ı bulun ve adapterı ayarlayın
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        if (!cities.isEmpty()) {
            imageView = findViewById(R.id.imageView);
            imageView.setVisibility(View.GONE);
            backgroundImageView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

        // İkinci menü öğelerinin simgelerini görünür yapın
        MenuItem sortMenuItem = menu.findItem(R.id.action_sort);
        SubMenu sortSubMenu = sortMenuItem.getSubMenu();
        for (int i = 0; i < sortSubMenu.size(); i++) {
            MenuItem item = sortSubMenu.getItem(i);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER); // Simgeleri görünür yapın
        }
        // Çıkış yap butonunu bulalım
        MenuItem logoutMenuItem = menu.findItem(R.id.action_logout);
        if (logoutMenuItem != null) {
            // Yazı rengini kırmızıya ayarlayalım
            SpannableString spannableString = new SpannableString(logoutMenuItem.getTitle());
            spannableString.setSpan(new ForegroundColorSpan(Color.RED), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            logoutMenuItem.setTitle(spannableString);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CityDatabaseHelper dbHelper = new CityDatabaseHelper(this);
        MyAdapter adapter = new MyAdapter(this, cities);
        ListView listView = (ListView) findViewById(R.id.listview);
        switch (item.getItemId()) {
            case R.id.action_addCity:
                Intent intent = new Intent(CitiesPage.this,AddCityPage.class);

                startActivity(intent);

                /*CityDatabaseHelper dbHelper = new CityDatabaseHelper(this);
                  dbHelper.deleteAllCities();*/
                return true;
            case R.id.action_logout:
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
                alertDialog.setTitle("Çıkış Yapılacak");
                alertDialog.setMessage("Oturumunuz Kapatılacak ve Giriş Sayfasına Yönlendirileceksiniz. Emin Misiniz?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intents = new Intent(getApplicationContext(), LoginPage.class);
                        intents.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intents);
                        finish();
                        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);

// SharedPreferences'taki tüm verileri sil
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.show();

                return true;
            case R.id.action_userFilter:
                Intent intenta = new Intent(CitiesPage.this,UserFilterActivity.class);
                startActivity(intenta);
                return true;
            case R.id.sort_by_date_desc:
                updateCityData();
                return true;
            case R.id.sort_by_date_asc:

                cities = dbHelper.getAllCities();
                Collections.reverse(cities);
                 adapter = new MyAdapter(this, cities);


                 listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);
                if (!cities.isEmpty()) {
                    imageView = findViewById(R.id.imageView);
                    imageView.setVisibility(View.GONE);
                    backgroundImageView.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.sort_by_rating_desc:
                cities =   dbHelper.getAllCitiesSortedByScore();
                adapter = new MyAdapter(this, cities);



                listView.setAdapter(adapter);
                if (!cities.isEmpty()) {
                    imageView = findViewById(R.id.imageView);
                    imageView.setVisibility(View.GONE);
                    backgroundImageView.setVisibility(View.VISIBLE);
                }


                 listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);

                return true;
            case R.id.sort_by_rating_asc:
                cities =   dbHelper.getAllCitiesSortedByScore();
                Collections.reverse(cities);
                adapter = new MyAdapter(this, cities);

// ListView'ı bulun ve adapterı ayarlayın
                listView = (ListView) findViewById(R.id.listview);
                listView.setAdapter(adapter);
                if (!cities.isEmpty()) {
                    imageView = findViewById(R.id.imageView);
                    imageView.setVisibility(View.GONE);
                    backgroundImageView.setVisibility(View.VISIBLE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


/*
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
       String username=sharedPreferences.getString("username", "");
        Log.d("TAG", "onCreate: "+username);
*/
    }



