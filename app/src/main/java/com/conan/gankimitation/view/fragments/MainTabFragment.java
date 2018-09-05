package com.conan.gankimitation.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankEntity;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.contract.MainTabContract;
import com.conan.gankimitation.data.network.GankApi;
import com.conan.gankimitation.databinding.GankListLayoutBinding;
import com.conan.gankimitation.presenter.MainTabPresenter;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.Constants;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.activities.MainActivity;
import com.conan.gankimitation.view.adapter.GankListAdapter;
import com.conan.gankimitation.view.listener.OnItemClickListener;
import com.conan.gankimitation.viewmodel.GankListViewModel;
import com.conan.gankimitation.widget.GankRecyclerView;

import javax.inject.Inject;

/**
 * Description：TabFragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class MainTabFragment extends BaseFragment implements MainTabContract.IMainTabView, SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener {

    private static final String TAG = MainTabFragment.class.getSimpleName();

    private GankApi.GankDataType mDataType;

    SwipeRefreshLayout mSwipeRefreshLayout;

    GankRecyclerView mRecyclerView;

    @Inject
    MainTabPresenter mPresenter;
    @Inject
    GankListAdapter mAdapter;

    private GankListLayoutBinding mBinding;
    private GankListViewModel mViewModel;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = GankListLayoutBinding.inflate(inflater,container,false);
        mBinding.setViewmodel(((MainActivity)getActivity()).obtainViewModel());
        initViews();
        return mBinding.getRoot();
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
    protected void injectDagger() {
        getFragmentComponent().inject(this);
    }

    private void initViews() {
        mPresenter.attachView(this);
        initSwipeView();
        intRecyclerView();
        initData();
    }

    private void intRecyclerView() {
        mRecyclerView = mBinding.recyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(GankEntity gank) {
                if(!TextUtils.isEmpty(gank.getUrl())){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(gank.getUrl());
                    intent.setData(uri);
                    startActivity(intent);
                }
            }
        });
    }

    private void initSwipeView() {
        mSwipeRefreshLayout = mBinding.swipeLayout;
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
