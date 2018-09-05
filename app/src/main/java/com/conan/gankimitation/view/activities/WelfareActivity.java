package com.conan.gankimitation.view.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.contract.WelfareContract;
import com.conan.gankimitation.databinding.WelfareLayoutBinding;
import com.conan.gankimitation.presenter.WelfarePresenter;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.Constants;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.adapter.WelfareAdapter;
import com.conan.gankimitation.widget.GankRecyclerView;
import com.conan.gankimitation.widget.WelfareItemDecoration;

import javax.inject.Inject;

/**
 * Description：福利Activity
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public class WelfareActivity extends BaseActivity implements WelfareContract.IWelfareView,SwipeRefreshLayout.OnRefreshListener,GankRecyclerView.OnLoadMoreListener{

    private static final String TAG = WelfareActivity.class.getSimpleName();
    @Inject
    WelfarePresenter mPresenter;
    @Inject
    WelfareAdapter mAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    GankRecyclerView mRecyclerView;
    WelfareLayoutBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onNecessaryPermissionGranted() {
        getActivityComponent().inject(this);
        mPresenter.attachView(this);
        initViews();
    }

    private void initViews(){
        mBinding = DataBindingUtil.setContentView(this,R.layout.welfare_layout);
        customToolbar();
        initSwipeView();
        intRecyclerView();
        initData();
    }

    private void customToolbar(){
        Toolbar toolbar = mBinding.welfareToolbar.toolbar;
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setTitle(R.string.welfare);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG,"setNavigationOnClickListener");
                finish();
            }
        });
    }

    private void initSwipeView(){
        mSwipeRefreshLayout = mBinding.swipeLayout;
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void intRecyclerView() {
        mRecyclerView = mBinding.recyclerview;
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnLoadMoreListener(this);
        mRecyclerView.addItemDecoration(new WelfareItemDecoration(16,2));
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
    public void onRefresh() {
        int pageIndex = AppUtil.getPageIndex(mAdapter.getItemCount(), Constants.PAGE_SIZE);
        mPresenter.fetchWelfareList(pageIndex,Constants.PAGE_SIZE);
    }

    @Override
    public void onLoadMore() {
        LogUtil.i(TAG,"onLoadMore:"+mSwipeRefreshLayout.isRefreshing());
        if(!mSwipeRefreshLayout.isRefreshing()){
            int pageIndex = AppUtil.getPageIndex(mAdapter.getItemCount(), Constants.PAGE_SIZE);
            mPresenter.fetchWelfareList(pageIndex,Constants.PAGE_SIZE);
        }
    }

    @Override
    public void fetchWelfareListSuccess(GankList gankList, boolean hasMoreData) {
        LogUtil.i(TAG,"fetchWelfareListSuccess");
        mAdapter.setData(gankList);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    public void fetchWelfareListFail(String reason) {
        LogUtil.i(TAG,"fetchWelfareListFail reason:"+reason);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setLoadMoreComplete();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        LogUtil.i(TAG,"onDestroy");
        super.onDestroy();
    }
}
