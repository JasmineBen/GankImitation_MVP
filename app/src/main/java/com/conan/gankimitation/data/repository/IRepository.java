package com.conan.gankimitation.data.repository;

import com.conan.gankimitation.bean.GankList;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/**
 * Description：资源层接口
 * Created by：JasmineBen
 * Time：2017/11/2
 */

public interface IRepository {
    void getRemoteGankList(Observer<GankList> observer, String type, int pageIndex, int pageSize);

    void getLocalGankList(Observer<GankList> observer, String type, int pageIndex, int pageSize);

    void cacheGankList(Observer<Boolean> observer,GankList gankList);

}
