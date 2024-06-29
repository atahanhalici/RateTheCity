package com.atahanhalici.ratethecity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private List<String> imagesBase64List;
    private OnItemClickListener onItemClickListener; // Tıklama olayını dinlemek için arayüz

    // Arayüz tanımı
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SliderAdapter(Context context, List<String> imagesBase64List, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.imagesBase64List = imagesBase64List;
        this.onItemClickListener = onItemClickListener; // Arayüzü atama
    }

    @Override
    public int getCount() {
        return imagesBase64List.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);

        byte[] decodedString = Base64.decode(imagesBase64List.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageView.setImageBitmap(decodedByte);

        // Öğenin tıklama olayını dinle
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tıklama olayını arayüze iletmek
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    public void setImages(List<String> newImagesBase64List) {
        imagesBase64List = newImagesBase64List;
        notifyDataSetChanged();
    }
}
