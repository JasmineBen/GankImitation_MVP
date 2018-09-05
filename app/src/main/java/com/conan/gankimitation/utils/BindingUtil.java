package com.conan.gankimitation.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.conan.gankimitation.widget.WelfareImage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingUtil {

    @BindingAdapter({"welfareImageUrl"})
    public static void loadWelfareImage(WelfareImage imgView, String url) {
       imgView.load(url);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadItemImage(ImageView imgView, String url) {
        url = AppUtil.buildRequestImageParam(imgView.getContext(), url, 72);
        Glide.with(imgView.getContext())
                .load(url).centerCrop()
                .into(imgView);
    }

    @BindingConversion
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
