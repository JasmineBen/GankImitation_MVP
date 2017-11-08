package com.conan.gankimitation.contract;

import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.data.network.GankApi;

/**
 * Description：主界面Contract
 * Created by：JasmineBen
 * Time：2017/11/1
 */
public interface MainTabContract {

    interface IMainTabView extends IContract.IView{
        void fetchGankListSuccess(GankList gankList, boolean hasMoreData);
        void fetchGankListFail(String reason);
    }

    interface IMainTabPresenter extends IContract.IPresenter<IMainTabView>{
        void fetchGankList(GankApi.GankDataType type, int pageIndex, int pageSize);
    }
}
