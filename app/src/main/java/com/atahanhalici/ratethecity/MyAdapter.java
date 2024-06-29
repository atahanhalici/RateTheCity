package com.atahanhalici.ratethecity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<City> {
    public MyAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_items, parent, false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView info = (TextView) convertView.findViewById(R.id.info);
        RatingBar rating = (RatingBar) convertView.findViewById(R.id.rating);

        // İlk resmi kullanıyoruz, birden fazla resim varsa başka bir yöntem kullanılabilir

        Bitmap bitmap = decodeBase64(city.getImages().get(0));
        image.setImageBitmap(bitmap);
        name.setText(city.getCityName());
        info.setText(truncateText(city.getSummary()));

        if(city.getNumberOfVote()==0){
            rating.setRating(0);
        }else{
            double sayi= city.getAuthorScore()/4/city.getNumberOfVote();
            float sayim=(float) sayi;
            rating.setRating(sayim);
        }


        return convertView;
    }


    public static String truncateText(String text) {
        if (text.length() > 247) {
            text = text.substring(0, 247) + "...";
        }

        Log.d("TAG", "truncateText: "+text);
        return text;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}

