package com.conan.gankimitation.contract;

import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.data.network.GankApi;

/**
 * Description：福利Contract
 * Created by：JasmineBen
 * Time：2017/11/4
 */
public interface WelfareContract {

    interface IWelfareView extends IContract.IView{
        void fetchWelfareListSuccess(GankList gankList, boolean hasMoreData);
        void fetchWelfareListFail(String reason);
    }

    interface IWelfarePresenter extends IContract.IPresenter<IWelfareView>{
        void fetchWelfareList(int pageIndex, int pageSize);
    }
}
