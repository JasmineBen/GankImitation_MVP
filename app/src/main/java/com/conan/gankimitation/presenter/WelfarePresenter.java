package com.conan.gankimitation.presenter;

import com.conan.gankimitation.bean.GankContainer;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.contract.WelfareContract;
import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.data.repository.IRepository;
import com.conan.gankimitation.utils.Constants;
import com.conan.gankimitation.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Description：福利Presenter
 * Created by：JasmineBen
 * Time：2017/11/3
 */

public class WelfarePresenter implements WelfareContract.IWelfarePresenter {

    private static final String TAG = WelfarePresenter.class.getSimpleName();

    private WelfareContract.IWelfareView mView;
    private CompositeDisposable mCompositeDisposable;
    private GankContainer mGankContainer;
    private IRepository mRepository;

    @Inject
    public WelfarePresenter(IRepository repository){
        this.mRepository = repository;
        mCompositeDisposable = new CompositeDisposable();
        mGankContainer = new GankContainer();
    }

    @Override
    public void fetchWelfareList(int pageIndex, int pageSize) {
        LogUtil.i(TAG, "fetchGankList pageIndex:"+pageIndex+";pageSize"+pageSize);
        GankApi.GankDataType dataType = GankApi.GankDataType.DATA_TYPE_WELFARE;
        Observer<GankList> observer = new Observer<GankList>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                LogUtil.i(TAG, "fetchGankList onSubscribe");
                mCompositeDisposable.add(disposable);
            }

            @Override
            public void onNext(@NonNull GankList gankList) {
                LogUtil.i(TAG, "fetchGankList onNext");
                if (gankList != null) {
                    gankList.setType(dataType.getDataType());
                }
                int added = pageIndex == 1 ? mGankContainer.onRefreshNewData(gankList) : mGankContainer.onLoadMoreData(gankList);
                mView.fetchWelfareListSuccess(mGankContainer.getGankList(dataType), added >= Constants.PAGE_SIZE);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogUtil.i(TAG, "fetchGankList onError");
                mView.fetchWelfareListFail(null);
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "fetchGankList onComplete");
            }
        };
        mRepository.getRemoteGankList(observer, dataType.getDataType(), pageIndex, pageSize);
    }

    @Override
    public void attachView(WelfareContract.IWelfareView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
    }
}
