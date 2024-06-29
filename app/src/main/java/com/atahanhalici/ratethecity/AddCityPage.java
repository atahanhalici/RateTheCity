package com.atahanhalici.ratethecity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddCityPage extends AppCompatActivity {
    private static final int PICK_IMAGES_REQUEST_CODE = 1;
    List<String> userImagesList = new ArrayList<>();
    ViewPager viewPager;
    EditText sehiradi,ulkeadi,ozetbilgi,tarihvekultur,dogalguzellikler,mutfaks,ekonomiveturizm,yazaryorumu,valiliklink,belediyelink;
    SliderAdapter sliderAdapter;
    String base64String;
    SliderAdapter.OnItemClickListener itemClickListener = new SliderAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            // Tıklanan öğenin view'ini al

            resimSec(); // Tıklanan view'i geçir
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city_page);
         viewPager=findViewById(R.id.addViewPager);
         sehiradi=findViewById(R.id.sehiradi);
        ulkeadi=findViewById(R.id.ulkeadi);
        ozetbilgi=findViewById(R.id.ozetbilgi);
        tarihvekultur=findViewById(R.id.tarihvekultur);
        dogalguzellikler=findViewById(R.id.dogalguzellikler);
        mutfaks=findViewById(R.id.mutfak);
        ekonomiveturizm=findViewById(R.id.ekonomiveturizm);
        yazaryorumu=findViewById(R.id.yazaryorumu);
        valiliklink=findViewById(R.id.valiliklink);
        belediyelink=findViewById(R.id.belediyelink);
        getSupportActionBar().hide();


        base64String = imageToBase64(R.drawable.logoresimekle);
        userImagesList.add(base64String);
         sliderAdapter = new SliderAdapter(this,userImagesList,itemClickListener);
        viewPager.setAdapter(sliderAdapter);


    }
    private String imageToBase64(int resourceId) {
        // Resmi kaynaktan oku
        InputStream inputStream = getResources().openRawResource(resourceId);

        try {
            // Resmi byte dizisine dönüştür
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            // Resmi Base64'e dönüştür
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void resimSec(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE);
    }

    public void kaydet(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        String username=sharedPreferences.getString("username", "");
        String cityName = sehiradi.getText().toString().trim();
        String countryName = ulkeadi.getText().toString().trim();
        String summary = ozetbilgi.getText().toString().trim();
        String tarihVeKultur = tarihvekultur.getText().toString().trim();
        String dogalGuzellikler = dogalguzellikler.getText().toString().trim();
        String mutfak = mutfaks.getText().toString().trim();
        String ekonomiVeTurizm = ekonomiveturizm.getText().toString().trim();
        String yazarYorumu = yazaryorumu.getText().toString().trim();
        String valilikLink = valiliklink.getText().toString().trim();
        String belediyeLink = belediyelink.getText().toString().trim();
        CityDatabaseHelper dbHelper = new CityDatabaseHelper(this); // this, AddCityPage sınıfının bir örneğini temsil eder
// Verilerin boş olup olmadığını kontrol et
        if (dbHelper.isCityExist(cityName)) {
            Toast.makeText(this, "Bu şehir zaten mevcut", Toast.LENGTH_SHORT).show();
        }
       else if (!cityName.isEmpty() && !countryName.isEmpty() && userImagesList != null && userImagesList.get(0)!=base64String &&
                !username.isEmpty() && !summary.isEmpty() && !tarihVeKultur.isEmpty() &&
                !dogalGuzellikler.isEmpty() && !mutfak.isEmpty() && !ekonomiVeTurizm.isEmpty() &&
                !yazarYorumu.isEmpty() && !valilikLink.isEmpty() && !belediyeLink.isEmpty()) {
            // Tüm veriler dolu, işlemleri yapabiliriz
            // Örneğin, City nesnesi oluşturup veritabanına ekleme işlemi gerçekleştirebiliriz

            City city = new City(cityName, countryName, userImagesList, username, 0, summary, tarihVeKultur,
                    dogalGuzellikler, mutfak, ekonomiVeTurizm, yazarYorumu, 0.0, 0.0, 0.0, 0.0, 0.0,
                    valilikLink, belediyeLink);
            // Şehri veritabanına ekleyebiliriz

            dbHelper.addCity(city);
            onBackPressed();
            Toast.makeText(this, "Şehir Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
        } else {

            // Verilerden biri veya birkaçı boş, kullanıcıya uyarı verilebilir veya işlem iptal edilebilir
            // Örneğin, kullanıcıya bir Toast mesajı gösterebiliriz
            Toast.makeText(this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show();
        }



    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getClipData() != null) {

                ClipData clipData = data.getClipData();
                userImagesList.clear();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    InputStream inputStream = null;
                    try {
                        inputStream = AddCityPage.this.getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] byteArray = outputStream.toByteArray();

                    userImagesList.add(Base64.encodeToString(byteArray, Base64.DEFAULT));


                    //bunlar base64 veritabanına yazılmaya hazır
                    imageView.setImageResource(R.drawable.selectedimage);
                    Toast.makeText(AddCityPage.this,"Resimler Seçildi",Toast.LENGTH_LONG).show();
                }
            } else if (data.getData() != null) {

                Uri imageUri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = AddCityPage.this.getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                userImagesList.clear();
                userImagesList.add(Base64.encodeToString(byteArray, Base64.DEFAULT));


                imageView.setImageResource(R.drawable.selectedimage);
                Toast.makeText(AddCityPage.this,"Resim Seçildi",Toast.LENGTH_LONG).show();
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getClipData() != null) {
                ClipData clipData = data.getClipData();
                int selectedImageCount = clipData.getItemCount();
                if (selectedImageCount > 3) {
                    Toast.makeText(AddCityPage.this, "Maksimum 3 resim seçebilirsiniz", Toast.LENGTH_LONG).show();
                    return; // 3'ten fazla resim seçildiği için işlemi sonlandır
                }
                userImagesList.clear();
                for (int i = 0; i < selectedImageCount; i++) {
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    InputStream inputStream = null;
                    try {
                        inputStream = getContentResolver().openInputStream(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] byteArray = outputStream.toByteArray();
                    userImagesList.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
                }
                sliderAdapter = new SliderAdapter(this,userImagesList,itemClickListener);
                viewPager.setAdapter(sliderAdapter);
                Toast.makeText(AddCityPage.this, "Resimler Seçildi", Toast.LENGTH_LONG).show();
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                userImagesList.clear();
                userImagesList.add(Base64.encodeToString(byteArray, Base64.DEFAULT));
                sliderAdapter = new SliderAdapter(this,userImagesList,itemClickListener);
                viewPager.setAdapter(sliderAdapter);
                Toast.makeText(AddCityPage.this, "Resim Seçildi", Toast.LENGTH_LONG).show();
            }
        }
    }
}