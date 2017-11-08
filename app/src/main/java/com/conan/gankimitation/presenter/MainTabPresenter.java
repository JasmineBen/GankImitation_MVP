package com.conan.gankimitation.presenter;

import com.conan.gankimitation.bean.GankContainer;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.contract.MainTabContract;
import com.conan.gankimitation.data.repository.IRepository;
import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.Constants;
import com.conan.gankimitation.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description：主Tab Presenter
 * Created by：JasmineBen
 * Time：2017/11/3
 */
public class MainTabPresenter implements MainTabContract.IMainTabPresenter {

    private static final String TAG = MainTabPresenter.class.getSimpleName();
    private MainTabContract.IMainTabView mView;

    private IRepository mRepository;
    private GankContainer mGankContainer;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public MainTabPresenter(IRepository repository) {
        this.mRepository = repository;
        this.mGankContainer = new GankContainer();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(MainTabContract.IMainTabView view) {
        mView = view;
    }

    @Override
    public void fetchGankList(final GankApi.GankDataType type, final int pageIndex, final int pageSize) {
        LogUtil.i(TAG, "fetchGankList type:" + type+",pageIndex:"+pageIndex+",pageSize:"+pageSize);
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
                    gankList.setType(type.getDataType());
                }
                int added = pageIndex == 1 ? mGankContainer.onRefreshNewData(gankList) : mGankContainer.onLoadMoreData(gankList);
                cacheGankList(gankList);
                mView.fetchGankListSuccess(mGankContainer.getGankList(type), added >= Constants.PAGE_SIZE);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                LogUtil.i(TAG, "fetchGankList onError");
                mView.fetchGankListFail(null);
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
                public void onSubscribe(@NonNull Disposable disposable) {
                    mCompositeDisposable.add(disposable);
                    LogUtil.i(TAG, "cacheGankList onSubscribe");
                }

                @Override
                public void onNext(@NonNull Boolean aBoolean) {
                    LogUtil.i(TAG, "cacheGankList onNext aBoolean:" + aBoolean);
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
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

    @Override
    public void detachView() {
        AppUtil.cancelRxDisposables(mCompositeDisposable);
    }
}
