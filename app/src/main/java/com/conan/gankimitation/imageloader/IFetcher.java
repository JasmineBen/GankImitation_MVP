package com.conan.gankimitation.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Description：图片下载接口
 * Created by：JasmineBen
 * Time：2017/11/2
 */
 public interface IFetcher {
    void displayImage(Context context, ImageView imageView, String url, DisplayOptions options, ImageLoaderListener listener);
    void displayImage(Context context, ImageView imageView, int resourceId, DisplayOptions options, ImageLoaderListener listener);
    void clearDiskCache(Context context);
    void displayGif(Context context, ImageView imageView, String url, DisplayOptions options, ImageLoaderListener listener);
}
