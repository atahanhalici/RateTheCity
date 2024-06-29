package com.atahanhalici.ratethecity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CityDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cities_database.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CITIES = "cities";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY_NAME = "city_name";
    private static final String COLUMN_COUNTRY_NAME = "country_name";
    private static final String COLUMN_IMAGES = "images";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_NUMBER_OF_VOTE = "number_of_vote"; // Sayısal değer olduğu için Integer yerine String olarak tanımlanmalı
    private static final String COLUMN_SUMMARY = "summary";
    private static final String COLUMN_CULTURE_DATE = "culture_date";
    private static final String COLUMN_NATURAL_BEAUTY = "natural_beauty";
    private static final String COLUMN_CUISINE = "cuisine";
    private static final String COLUMN_ECONOMY_TOURISM = "economy_tourism";
    private static final String COLUMN_AUTHOR_COMMENT = "author_comment";
    private static final String COLUMN_DATE_SCORE = "date_score"; // Sayısal değer olduğu için Double yerine String olarak tanımlanmalı
    private static final String COLUMN_BEAUTY_SCORE = "beauty_score"; // Sayısal değer olduğu için Double yerine String olarak tanımlanmalı
    private static final String COLUMN_CUISINE_SCORE = "cuisine_score"; // Sayısal değer olduğu için Double yerine String olarak tanımlanmalı
    private static final String COLUMN_ECONOMY_SCORE = "economy_score"; // Sayısal değer olduğu için Double yerine String olarak tanımlanmalı
    private static final String COLUMN_AUTHOR_SCORE = "author_score"; // Sayısal değer olduğu için Double yerine String olarak tanımlanmalı
    private static final String COLUMN_VALILIK_LINK = "valilik_link";
    private static final String COLUMN_BELEDIYE_LINK = "belediye_link";


    public CityDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_CITIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CITY_NAME + " TEXT, " +
                COLUMN_COUNTRY_NAME + " TEXT, " +
                COLUMN_IMAGES + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_NUMBER_OF_VOTE + " INTEGER DEFAULT 0, " +
                COLUMN_SUMMARY + " TEXT, " +
                COLUMN_CULTURE_DATE + " TEXT, " +
                COLUMN_NATURAL_BEAUTY + " TEXT, " +
                COLUMN_CUISINE + " TEXT, " +
                COLUMN_ECONOMY_TOURISM + " TEXT, " +
                COLUMN_AUTHOR_COMMENT + " TEXT, " +
                COLUMN_DATE_SCORE + " REAL DEFAULT 0.0, " +
                COLUMN_BEAUTY_SCORE + " REAL DEFAULT 0.0, " +
                COLUMN_CUISINE_SCORE + " REAL DEFAULT 0.0, " +
                COLUMN_ECONOMY_SCORE + " REAL DEFAULT 0.0, " +
                COLUMN_AUTHOR_SCORE + " REAL DEFAULT 0.0, " +
                COLUMN_VALILIK_LINK + " TEXT, " +
                COLUMN_BELEDIYE_LINK + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }
    public ArrayList<City> getAllCitiesSortedByScore() {
        ArrayList<City> cityList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Tüm şehirleri sorgula ve COLUMN_AUTHOR_SCORE'a göre sırala
        Cursor cursor = db.query(TABLE_CITIES, null, null, null, null, null, COLUMN_AUTHOR_SCORE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                String adi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY_NAME));
                String ulkeAdi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY_NAME));
                List<String> resimler = Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGES)).split(","));
                String yazar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                Integer oySayisi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_VOTE));
                String ozet = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUMMARY));
                String kultur = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CULTURE_DATE));
                String guzellik = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NATURAL_BEAUTY));
                String mutfak = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUISINE));
                String ekonomi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_TOURISM));
                String yazaryorum = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_COMMENT));
                Double tarihpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DATE_SCORE));
                Double guzellikpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BEAUTY_SCORE));
                Double mutfakpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CUISINE_SCORE));
                Double ekonomipuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_SCORE));
                Double yazarpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_SCORE));
                String valiliklink = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALILIK_LINK));
                String belediyelink = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BELEDIYE_LINK));

                City city = new City(adi, ulkeAdi, resimler, yazar, oySayisi, ozet, kultur, guzellik, mutfak, ekonomi, yazaryorum, tarihpuan, guzellikpuan, mutfakpuan, ekonomipuan, yazarpuan, valiliklink, belediyelink);
                cityList.add(city);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cityList;
    }
    public void updateCityRatings(String cityName, float tarihRating, float dogalRating, float mutfakRating, float ekonomiRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Mevcut puanları al
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CITIES + " WHERE " + COLUMN_CITY_NAME + " = ?", new String[]{cityName});
        if (cursor.moveToFirst()) {
            float currentTarihScore = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_DATE_SCORE));
            float currentDogalScore = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_BEAUTY_SCORE));
            float currentMutfakScore = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_CUISINE_SCORE));
            float currentEkonomiScore = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_SCORE));
            float currentAverageScore = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_SCORE));
            int currentNumberOfVote = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_VOTE));

            // Yeni puanları hesapla
            contentValues.put(COLUMN_DATE_SCORE, currentTarihScore + tarihRating);
            contentValues.put(COLUMN_BEAUTY_SCORE, currentDogalScore + dogalRating);
            contentValues.put(COLUMN_CUISINE_SCORE, currentMutfakScore + mutfakRating);
            contentValues.put(COLUMN_ECONOMY_SCORE, currentEkonomiScore + ekonomiRating);
            contentValues.put(COLUMN_AUTHOR_SCORE, currentAverageScore +tarihRating+dogalRating+mutfakRating+ekonomiRating);
            contentValues.put(COLUMN_NUMBER_OF_VOTE, currentNumberOfVote + 1);

            // Veritabanını güncelle
            db.update(TABLE_CITIES, contentValues, COLUMN_CITY_NAME + " = ?", new String[]{cityName});
        }
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        onCreate(db);
    }

    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_NAME, city.getCityName());
        values.put(COLUMN_COUNTRY_NAME, city.getCountryName());
        values.put(COLUMN_IMAGES, listToString(city.getImages()));// images dizisini metin olarak ekleyin
        values.put(COLUMN_USERNAME, city.getUsername());
        values.put(COLUMN_NUMBER_OF_VOTE, city.getNumberOfVote());
        values.put(COLUMN_SUMMARY, city.getSummary());
        values.put(COLUMN_CULTURE_DATE, city.getCultureDate());
        values.put(COLUMN_NATURAL_BEAUTY, city.getNaturalBeauty());
        values.put(COLUMN_CUISINE, city.getCuisine());
        values.put(COLUMN_ECONOMY_TOURISM, city.getEconomyTourism());
        values.put(COLUMN_AUTHOR_COMMENT, city.getAuthorComment());
        values.put(COLUMN_DATE_SCORE, city.getDateScore());
        values.put(COLUMN_BEAUTY_SCORE, city.getBeautyScore());
        values.put(COLUMN_CUISINE_SCORE, city.getCuisineScore());
        values.put(COLUMN_ECONOMY_SCORE, city.getEconomyScore());
        values.put(COLUMN_AUTHOR_SCORE, city.getAuthorScore());
        values.put(COLUMN_VALILIK_LINK, city.getValilikLink());
        values.put(COLUMN_BELEDIYE_LINK, city.getBelediyeLink());

        long result = db.insert(TABLE_CITIES, null, values);
        if (result == -1) {
            // Hata durumunda burada işlem yapılabilir.
        } else {
            // Başarılı ekleme durumunda burada işlem yapılabilir.
        }
        db.close();
    }

    public City getCityByName(String cityName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_CITY_NAME,
                COLUMN_COUNTRY_NAME,
                COLUMN_IMAGES,
                COLUMN_USERNAME,
                COLUMN_NUMBER_OF_VOTE,
                COLUMN_SUMMARY,
                COLUMN_CULTURE_DATE,
                COLUMN_NATURAL_BEAUTY,
                COLUMN_CUISINE,
                COLUMN_ECONOMY_TOURISM,
                COLUMN_AUTHOR_COMMENT,
                COLUMN_DATE_SCORE,
                COLUMN_BEAUTY_SCORE,
                COLUMN_CUISINE_SCORE,
                COLUMN_ECONOMY_SCORE,
                COLUMN_AUTHOR_SCORE,
                COLUMN_VALILIK_LINK,
                COLUMN_BELEDIYE_LINK
        };
        String selection = COLUMN_CITY_NAME + "=?";
        String[] selectionArgs = {cityName};
        Cursor cursor = db.query(TABLE_CITIES, columns, selection, selectionArgs, null, null, null);
        City city = null;
        if (cursor.moveToFirst()) {
            String adi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY_NAME));
            String ulkeAdi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY_NAME));
            List<String> resimler = Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGES)).split(","));
            String yazar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            Integer oySayisi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_VOTE));
            String ozet = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUMMARY));
            String kultur = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CULTURE_DATE));
            String guzellik = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NATURAL_BEAUTY));
            String mutfak = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUISINE));
            String ekonomi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_TOURISM));
            String yazaryorum = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_COMMENT));
            Double tarihpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DATE_SCORE));
            Double guzellikpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BEAUTY_SCORE));
            Double mutfakpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CUISINE_SCORE));
            Double ekonomipuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_SCORE));
            Double yazarpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_SCORE));
            String valiliklink = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALILIK_LINK));
            String belediyelink = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BELEDIYE_LINK));
            city = new City(adi, ulkeAdi, resimler, yazar, oySayisi, ozet, kultur, guzellik, mutfak, ekonomi, yazaryorum, tarihpuan, guzellikpuan, mutfakpuan, ekonomipuan, yazarpuan, valiliklink, belediyelink);
        }
        cursor.close();
        return city;
    }
    public boolean isCityExist(String cityName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITIES, null, COLUMN_CITY_NAME + "=?", new String[]{cityName}, null, null, null);
        boolean exist = cursor.getCount() > 0;
        cursor.close();
        return exist;
    }
    private String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item : list) {
            sb.append(item).append(",");
        }
        // Son virgülü kaldır
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    public void deleteAllCities() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITIES, null, null);
        db.close();
    }


    public ArrayList<City> getAllCities() {
        ArrayList<City> cityList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITIES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String adi = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY_NAME));
                String ulkeAdi=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY_NAME));
                List<String> resimler = Arrays.asList(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGES)).split(","));
                String yazar=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                Integer oySayisi = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NUMBER_OF_VOTE));
                String ozet=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUMMARY));
                String kultur = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CULTURE_DATE));
                String guzellik=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NATURAL_BEAUTY));
                String mutfak = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CUISINE));
                String ekonomi=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_TOURISM));
                String yazaryorum = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_COMMENT));
                Double tarihpuan=cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DATE_SCORE));
                Double guzellikpuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BEAUTY_SCORE));
                Double mutfakpuan=cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CUISINE_SCORE));
                Double ekonomipuan = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ECONOMY_SCORE));
                Double yazarpuan=cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_SCORE));
                String valiliklink =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALILIK_LINK));
                String belediyelink=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BELEDIYE_LINK));
                City city = new City(adi,ulkeAdi,resimler,yazar,oySayisi,ozet,kultur,guzellik,mutfak,ekonomi,yazaryorum,tarihpuan,guzellikpuan,mutfakpuan,ekonomipuan,yazarpuan,valiliklink,belediyelink);

                cityList.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cityList;
    }

    // Metni bir diziye dönüştürme metodu
    private String[] stringToArray(String str) {
        return str.split(",");
    }


}

