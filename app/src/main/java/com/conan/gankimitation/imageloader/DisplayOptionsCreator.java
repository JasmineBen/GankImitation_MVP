package com.conan.gankimitation.imageloader;

import android.graphics.Bitmap;

import com.conan.gankimitation.R;

/**
 * Description：Option工厂
 * Created by：JasmineBen
 * Time：2017/11/2
 */
public class DisplayOptionsCreator {

    public static DisplayOptions getDefault() {
        return new DisplayOptions.Builder()
                .configBitmap(Bitmap.Config.RGB_565)
                .resizeToCircle(false)
                .showImageOnFail(R.mipmap.default_loadfail_pic)
                .showImageForEmptyUri(R.mipmap.default_loadfail_pic)
                .showImageOnLoading(R.mipmap.default_loadfail_pic).build();
    }

    public static DisplayOptions getAvatarOptions() {
        return new DisplayOptions.Builder()
                .configBitmap(Bitmap.Config.RGB_565)
                .resizeToCircle(true)
                .showImageOnFail(R.mipmap.default_loadfail_pic)
                .showImageForEmptyUri(R.mipmap.default_loadfail_pic)
                .showImageOnLoading(R.mipmap.default_loadfail_pic).build();
    }

}
