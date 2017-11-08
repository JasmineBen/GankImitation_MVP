package com.conan.gankimitation.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.contract.MainTabContract;
import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.presenter.MainTabPresenter;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.Constants;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.adapter.GankListAdapter;
import com.conan.gankimitation.widget.GankRecyclerView;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description：TabFragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class MainTabFragment extends BaseFragment implements MainTabContract.IMainTabView, SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener {

    private static final String TAG = MainTabFragment.class.getSimpleName();

    private GankApi.GankDataType mDataType;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerview)
    GankRecyclerView mRecyclerView;

    @Inject
    MainTabPresenter mPresenter;

    @Inject
    GankListAdapter mAdapter;

    public static MainTabFragment newInstance(GankApi.GankDataType dataType) {
        MainTabFragment fragment = new MainTabFragment();
        Bundle data = new Bundle();
        data.putString(Constants.GANK_DATA_TYPE, dataType.getDataType());
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        if (data != null) {
            mDataType = AppUtil.parseGankDataType(data.getString(Constants.GANK_DATA_TYPE));
            LogUtil.i(TAG, "" + mDataType);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.fetchGankList(mDataType, AppUtil.getPageIndex(mAdapter.getItemCount(),
                Constants.PAGE_SIZE), Constants.PAGE_SIZE);
    }

    @Override
    public void onLoadMore() {
        if(!mSwipeRefreshLayout.isRefreshing()) {
            mPresenter.fetchGankList(mDataType, AppUtil.getPageIndex(mAdapter.getItemCount(),
                    Constants.PAGE_SIZE), Constants.PAGE_SIZE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.gank_list_layout;
    }

    @Override
    protected void injectDagger() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initViews(View fragmentView) {
        mPresenter.attachView(this);
        initSwipeView();
        intRecyclerView();
        initData();
    }

    private void intRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
    }

    private void initSwipeView() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void initData(){
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }


    @Override
    public void fetchGankListSuccess(GankList gankList, boolean hasMoreData) {
        mAdapter.setData(gankList);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    public void fetchGankListFail(String reason) {
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }
}
