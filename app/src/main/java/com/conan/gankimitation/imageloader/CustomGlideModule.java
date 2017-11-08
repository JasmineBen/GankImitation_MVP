package com.conan.gankimitation.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.LogUtil;

/**
 * Description：Glide配置
 * Created by：JasmineBen
 * Time：2017/11/2
 */
public class CustomGlideModule implements GlideModule{

    private static final String TAG = CustomGlideModule.class.getSimpleName();

    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        LogUtil.i(TAG,"applyOptions");
        String cachePath = AppUtil.getImageCacheDirectory(context,"gank/glide");
        LogUtil.i(TAG,cachePath);
        int cacheSize = 200 * 1024 * 1024;
        glideBuilder.setDiskCache(new DiskLruCacheFactory(cachePath,cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
