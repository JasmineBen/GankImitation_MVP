package com.conan.gankimitation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.data.repository.IRepository;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.LogUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GankListViewModel extends AndroidViewModel {

    private static final String TAG = "GankListViewModel";


    private IRepository mRepository;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private SingleLiveEvent<GankList> mLoadMoreTaskEvent = new SingleLiveEvent<>();
    private SingleLiveEvent<GankList> mRefreshTaskEvent = new SingleLiveEvent<>();

    public GankListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setRepository(IRepository repository){
        mRepository = repository;
    }

    public SingleLiveEvent<GankList> getLoadMoreTaskEvent() {
        return mLoadMoreTaskEvent;
    }

    public SingleLiveEvent<GankList> getRefreshTaskEvent() {
        return mRefreshTaskEvent;
    }

    public void fetchGankList(final GankApi.GankDataType type, final int pageIndex, final int pageSize){
        LogUtil.i(TAG, "fetchGankList type:" + type+",pageIndex:"+pageIndex+",pageSize:"+pageSize);
        Observer<GankList> observer = new Observer<GankList>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                LogUtil.i(TAG, "fetchGankList onSubscribe");
                mCompositeDisposable.add(disposable);
            }

            @Override
            public void onNext(@io.reactivex.annotations.NonNull GankList gankList) {
                LogUtil.i(TAG, "fetchGankList onNext");
                if (gankList != null) {
                    gankList.setType(type.getDataType());
                }
                cacheGankList(gankList);
                if(pageIndex == 1){
                    mRefreshTaskEvent.setValue(gankList);
                }else {
                    mLoadMoreTaskEvent.setValue(gankList);
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable throwable) {
                LogUtil.i(TAG, "fetchGankList onError");
                if(pageIndex == 1){
                    mRefreshTaskEvent.setValue(null);
                }else {
                    mLoadMoreTaskEvent.setValue(null);
                }
            }

            @Override
            public void onComplete() {
                LogUtil.i(TAG, "fetchGankList onComplete");
            }
        };
        mRepository.getRemoteGankList(observer, type.getDataType(), pageIndex, pageSize);
    }

    private void cacheGankList(GankList gankList) {
        if (gankList != null && gankList.size() > 0) {
            Observer<Boolean> observer = new Observer<Boolean>() {
                @Override
                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                    mCompositeDisposable.add(disposable);
                    LogUtil.i(TAG, "cacheGankList onSubscribe");
                }

                @Override
                public void onNext(@io.reactivex.annotations.NonNull Boolean aBoolean) {
                    LogUtil.i(TAG, "cacheGankList onNext aBoolean:" + aBoolean);
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable throwable) {
                    LogUtil.i(TAG, "cacheGankList onError");
                }

                @Override
                public void onComplete() {
                    LogUtil.i(TAG, "cacheGankList onComplete");
                }
            };
            mRepository.cacheGankList(observer, gankList);
        }
    }

    public void onDestroy(){
        AppUtil.cancelRxDisposables(mCompositeDisposable);
    }

}
