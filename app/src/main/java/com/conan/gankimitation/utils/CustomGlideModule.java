package com.conan.gankimitation.utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

public class CustomGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

//        // 设置磁盘缓存为100M，缓存在内部缓存目录
//        int cacheSize100MegaBytes = 104857600;
//        builder.setDiskCache(
//                new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes)
//        );
//        //builder.setDiskCache(
//        //new ExternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));
//
//        // 20%大的内存缓存作为 Glide 的默认值
//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//
//        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//
//        builder.setMemoryCache( new LruResourceCache(customMemoryCacheSize));
//        builder.setBitmapPool( new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
