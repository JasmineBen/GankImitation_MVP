package com.conan.gankimitation.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.conan.gankimitation.di.qualifier.ImageFetcher;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Description：图片下载包装
 * Created by：JasmineBen
 * Time：2017/11/2
 */
@Singleton
public class ImageLoader implements IFetcher {

    private IFetcher mImageFetcher;

    @Inject
    public ImageLoader(@ImageFetcher("Glide") IFetcher fetcher) {
        this.mImageFetcher = fetcher;
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String url, DisplayOptions options, ImageLoaderListener listener) {
        if (contextDestroyed(context)) {
            return;
        }
        mImageFetcher.displayImage(context, imageView, url, options, listener);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, int resourceId, DisplayOptions options, ImageLoaderListener listener) {
        if (contextDestroyed(context)) {
            return;
        }
        mImageFetcher.displayImage(context, imageView, resourceId, options, listener);
    }

    @Override
    public void displayGif(Context context, ImageView imageView, String url, DisplayOptions options, ImageLoaderListener listener) {
        if (contextDestroyed(context)) {
            return;
        }
        mImageFetcher.displayGif(context, imageView, url, options, listener);
    }

    @Override
    public void clearDiskCache(Context context) {
        mImageFetcher.clearDiskCache(context);
    }

    private boolean assertDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return true;
        }
        return false;
    }

    private boolean contextDestroyed(Context context) {
        return context == null || (context instanceof FragmentActivity && assertDestroyed((FragmentActivity) context))
                || (context instanceof Activity && assertDestroyed((Activity) context));
    }


}
