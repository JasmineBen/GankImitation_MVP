package com.conan.gankimitation.imageloader;

/**
 * Description：Listener
 * Created by：JasmineBen
 * Time：2017/11/2
 */
public interface ImageLoaderListener<R> {
    void onLoadStart();

    void onLoadComplete(R result);

    void onLoadFail(Exception e);
}
