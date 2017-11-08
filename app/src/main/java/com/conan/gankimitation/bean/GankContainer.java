package com.conan.gankimitation.bean;

import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.utils.AppUtil;


/**
 * Description：Gank数据的管理类
 * Created by：JasmineBen
 * Time：2017/10/28
 */
public class GankContainer {

    private GankList all = new GankList(GankApi.GankDataType.DATA_TYPE_ALL.getDataType());//全部
    private GankList android = new GankList(GankApi.GankDataType.DATA_TYPE_ANDROID.getDataType());//android
    private GankList ios = new GankList(GankApi.GankDataType.DATA_TYPE_IOS.getDataType());//ios
     private GankList welfare = new GankList(GankApi.GankDataType.DATA_TYPE_WELFARE.getDataType());//福利
    private GankList breakVideo = new GankList(GankApi.GankDataType.DATA_TYPE_BREAK_VIDEO.getDataType());//休息视频
    private GankList expandResource = new GankList(GankApi.GankDataType.DATA_TYPE_EXPAND_RESOURCE.getDataType());//拓展资源
    private GankList front = new GankList(GankApi.GankDataType.DATA_TYPE_FRONT.getDataType());//前端


    public int onRefreshNewData(GankList gankList) {
        if (gankList != null && gankList.getGankDatas() != null) {
            GankList currentGankList = getGankList(gankList.getGankDataType());
            currentGankList.clear();
            currentGankList.getGankDatas().addAll(gankList.getGankDatas());
            return gankList.size();
        } else {
            return 0;
        }
    }

    public int onLoadMoreData(GankList gankList) {
        if (gankList != null && gankList.getGankDatas() != null) {
            GankList currentGankList = getGankList(gankList.getGankDataType());
            currentGankList.getGankDatas().addAll(gankList.getGankDatas());
            return gankList.size();
        } else {
            return 0;
        }
    }

    public GankList getGankList(String type) {
        return getGankList(AppUtil.parseGankDataType(type));
    }

    public GankList getGankList(GankApi.GankDataType type) {
        switch (type) {
            case DATA_TYPE_ALL:
                return all;
            case DATA_TYPE_ANDROID:
                return android;
            case DATA_TYPE_IOS:
                return ios;
            case DATA_TYPE_WELFARE:
                return welfare;
            case DATA_TYPE_BREAK_VIDEO:
                return breakVideo;
            case DATA_TYPE_EXPAND_RESOURCE:
                return expandResource;
            case DATA_TYPE_FRONT:
                return front;
            default:
                return null;
        }
    }

    public int getCurrentCount(GankApi.GankDataType type) {
        return getGankList(type).size();
    }


}
