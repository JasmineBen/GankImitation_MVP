package com.conan.gankimitation.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.conan.gankimitation.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingUtil {

    @BindingAdapter({"welfareImageUrl"})
    public static void loadWelfareImage(ImageView imgView, String url) {
        int columnWidth = AppUtil.getColumnWidth(imgView.getContext(), 2, 16);
        url = AppUtil.buildRequestImageParam(imgView.getContext(), url, columnWidth);
        imgView.setLayoutParams(new FrameLayout.LayoutParams(columnWidth, columnWidth));
        Glide.with(imgView.getContext())
                .load(url).centerCrop().placeholder(R.color.c_fafafa).error(R.color.c_fafafa).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgView);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadItemImage(ImageView imgView, String url) {
        url = AppUtil.buildRequestImageParam(imgView.getContext(), url, 72);
        Glide.with(imgView.getContext())
                .load(url).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgView);
    }

    @BindingConversion
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }


}
